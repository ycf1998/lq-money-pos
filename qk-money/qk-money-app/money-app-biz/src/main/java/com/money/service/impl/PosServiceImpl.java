package com.money.service.impl;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.money.common.exception.BaseException;
import com.money.common.util.BeanMapUtil;
import com.money.constant.OrderStatusEnum;
import com.money.dto.OmsOrder.OmsOrderVO;
import com.money.dto.pos.PosGoodsVO;
import com.money.dto.pos.PosMemberVO;
import com.money.dto.pos.SettleAccountsDTO;
import com.money.dto.pos.SettleAccountsDetail;
import com.money.entity.*;
import com.money.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : money
 * @version : 1.0.0
 * @description : pos服务impl
 * @createTime : 2023-05-10 22:25:45
 */
@Service
@RequiredArgsConstructor
public class PosServiceImpl implements PosService {

    private final UmsMemberService umsMemberService;
    private final GmsGoodsService gmsGoodsService;
    private final OmsOrderService omsOrderService;
    private final OmsOrderDetailService omsOrderDetailService;
    private final OmsOrderLogService omsOrderLogService;

    @Override
    public List<PosGoodsVO> listGoods(String barcode) {
        List<GmsGoods> gmsGoodsList = gmsGoodsService.lambdaQuery().like(StrUtil.isNotBlank(barcode), GmsGoods::getBarcode, barcode).list();
        return BeanMapUtil.to(gmsGoodsList, PosGoodsVO::new);
    }

    @Override
    public List<PosMemberVO> listMember(String member) {
        List<UmsMember> memberList = umsMemberService.lambdaQuery().like(StrUtil.isNotBlank(member), UmsMember::getName, member).list();
        return BeanMapUtil.to(memberList, PosMemberVO::new);
    }

    @Override
    public OmsOrderVO settleAccounts(SettleAccountsDTO settleAccountsDTO) {
        OmsOrder order = new OmsOrder();
        String orderNo = this.getOrderNo();
        order.setOrderNo(orderNo);
        order.setStatus(OrderStatusEnum.PAID.name());
        order.setPaymentTime(LocalDateTime.now());

        // 核算订单
        List<SettleAccountsDetail> detailList = settleAccountsDTO.getDetails();
        List<OmsOrderDetail> orderDetails = detailList.stream().map(detail -> {
            GmsGoods goods = gmsGoodsService.getById(detail.getGoodsId());
            OmsOrderDetail orderDetail = new OmsOrderDetail();
            orderDetail.setOrderNo(orderNo);
            orderDetail.setStatus(OrderStatusEnum.PAID.name());
            orderDetail.setGoodsId(goods.getId());
            orderDetail.setGoodsBarcode(goods.getBarcode());
            orderDetail.setGoodsName(goods.getName());
            orderDetail.setSalePrice(goods.getSalePrice());
            orderDetail.setPurchasePrice(goods.getPurchasePrice());
            orderDetail.setSalePoints(goods.getSalePoints());

            orderDetail.setGoodsPrice(detail.getGoodsPrice());
            orderDetail.setQuantity(detail.getQuantity());
            return orderDetail;
        }).collect(Collectors.toList());
        this.aggOrder(order, orderDetails);

        // 会员处理
        Long memberId = settleAccountsDTO.getMember();
        UmsMember member = umsMemberService.getById(memberId);
        if (member != null) {
            order.setMember(member.getName());
            order.setMemberId(memberId);
            // 核算积分
            Integer pointsAmount = order.getPointsAmount();
            int consumeCoupon = member.getPoints() - pointsAmount;
            if (consumeCoupon < 0) {
                throw new BaseException("积分不足");
            }
            // 会员消费
            umsMemberService.consume(member.getId(), order.getPayAmount(), order.getPointsAmount());
        } else if (memberId != null) {
            throw new BaseException("未找到该会员");
        } else {
            // 零售
            if (order.getPointsAmount() > 0) {
                throw new BaseException("非会员不允许购买积分商品");
            }
        }

        // 扣库存
        orderDetails.forEach(omsOrderDetail -> gmsGoodsService.updateStock(omsOrderDetail.getGoodsId(), -omsOrderDetail.getQuantity()));
        // 保存订单
        omsOrderService.save(order);
        omsOrderDetailService.saveBatch(orderDetails);
        // 订单日志
        OmsOrderLog log = new OmsOrderLog();
        log.setOrderId(order.getId());
        log.setDescription("完成订单");
        omsOrderLogService.saveBatch(ListUtil.of(log));
        return BeanMapUtil.to(order, OmsOrderVO::new);
    }

    private void aggOrder(OmsOrder order, List<OmsOrderDetail> orderDetails) {
        // 成本
        BigDecimal costAmount = BigDecimal.ZERO;
        // 总价
        BigDecimal totalAmount = BigDecimal.ZERO;
        // 实付款
        BigDecimal payAmount = BigDecimal.ZERO;
        // 消耗积分
        int pointsAmount = 0;
        for (OmsOrderDetail orderDetail : orderDetails) {
            BigDecimal quantity = new BigDecimal(orderDetail.getQuantity());
            BigDecimal purchasePrice = orderDetail.getPurchasePrice().multiply(quantity);
            BigDecimal salePrice = orderDetail.getSalePrice().multiply(quantity);
            BigDecimal goodsPrice = orderDetail.getGoodsPrice().multiply(quantity);
            int points = orderDetail.getSalePoints() * quantity.intValue();
            costAmount = costAmount.add(purchasePrice);
            totalAmount = totalAmount.add(salePrice);
            payAmount = payAmount.add(goodsPrice);
            pointsAmount += points;
        }
        order.setCostAmount(costAmount);
        order.setTotalAmount(totalAmount);
        order.setPayAmount(payAmount);
        order.setPointsAmount(pointsAmount);
        order.setFinalSalesAmount(payAmount);
    }

    /**
     * 获取订单号
     *
     * @return {@link String}
     */
    private synchronized String getOrderNo() {
        // 15位
        String date = LocalDateTime.now().format(DatePattern.PURE_DATETIME_FORMATTER);
        // 1位
        String random = RandomUtil.randomNumbers(1);
        return date + random;
    }
}

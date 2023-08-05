package com.money.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.money.common.exception.BaseException;
import com.money.common.util.BeanMapUtil;
import com.money.common.vo.PageVO;
import com.money.constant.OrderStatusEnum;
import com.money.dto.OmsOrder.*;
import com.money.dto.OmsOrderDetail.OmsOrderDetailVO;
import com.money.dto.OmsOrderLog.OmsOrderLogVO;
import com.money.dto.UmsMember.UmsMemberVO;
import com.money.entity.OmsOrder;
import com.money.entity.OmsOrderDetail;
import com.money.entity.OmsOrderLog;
import com.money.mapper.OmsOrderMapper;
import com.money.service.*;
import com.money.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author money
 * @since 2023-04-30
 */
@Service
@RequiredArgsConstructor
public class OmsOrderServiceImpl extends ServiceImpl<OmsOrderMapper, OmsOrder> implements OmsOrderService {

    private final UmsMemberService umsMemberService;
    private final GmsGoodsService gmsGoodsService;
    private final OmsOrderDetailService omsOrderDetailService;
    private final OmsOrderLogService omsOrderLogService;

    @Override
    public PageVO<OmsOrderVO> list(OmsOrderQueryDTO queryDTO) {
        Page<OmsOrder> page = this.lambdaQuery().eq(StrUtil.isNotBlank(queryDTO.getStatus()), OmsOrder::getStatus, queryDTO.getStatus())
                .like(StrUtil.isNotBlank(queryDTO.getOrderNo()), OmsOrder::getOrderNo, queryDTO.getOrderNo())
                .like(StrUtil.isNotBlank(queryDTO.getMember()), OmsOrder::getMember, queryDTO.getMember())
                .ge(queryDTO.getStartTime() != null, OmsOrder::getCreateTime, queryDTO.getStartTime())
                .le(queryDTO.getEndTime() != null, OmsOrder::getCreateTime, queryDTO.getEndTime())
                .orderByDesc(StrUtil.isBlank(queryDTO.getSort()), OmsOrder::getCreateTime)
                .last(StrUtil.isNotBlank(queryDTO.getSort()), queryDTO.getOrderBySql())
                .page(PageUtil.toPage(queryDTO));
        return PageUtil.toPageVO(page, OmsOrderVO::new);
    }

    @Override
    public OrderCountVO getOrderCount(LocalDateTime startTime, LocalDateTime endTime) {
        // 1.查询时间段内的所有订单详情
        List<OmsOrderDetail> omsOrderDetails = omsOrderDetailService.lambdaQuery()
                // 仅查询需要用到的字段
                .select(OmsOrderDetail::getOrderNo, OmsOrderDetail::getQuantity, OmsOrderDetail::getReturnQuantity,
                        OmsOrderDetail::getGoodsPrice, OmsOrderDetail::getPurchasePrice)
                // 只统计已支付的订单
                .eq(OmsOrderDetail::getStatus, OrderStatusEnum.PAID)
                .ge(startTime != null, OmsOrderDetail::getCreateTime, startTime)
                .le(endTime != null, OmsOrderDetail::getCreateTime, endTime)
                .list();
        // 2.通过去重订单详情的单号获取到订单数
        long count = omsOrderDetails.stream().map(OmsOrderDetail::getOrderNo).distinct().count();
        // 3.计算销售额和成本
        BigDecimal saleCount = BigDecimal.ZERO;
        BigDecimal costCount = BigDecimal.ZERO;
        for (OmsOrderDetail omsOrderDetail : omsOrderDetails) {
            // 商品数量需要减去退货的数量
            int quantity = omsOrderDetail.getQuantity() - omsOrderDetail.getReturnQuantity();
            saleCount = saleCount.add(omsOrderDetail.getGoodsPrice().multiply(new BigDecimal(quantity)));
            costCount = costCount.add(omsOrderDetail.getPurchasePrice().multiply(new BigDecimal(quantity)));
        }
        // 4.返回 VO
        OrderCountVO vo = new OrderCountVO();
        vo.setOrderCount(count);
        vo.setSaleCount(saleCount);
        vo.setCostCount(costCount);
        // 销售额 - 成本 = 利润
        vo.setProfit(saleCount.subtract(costCount));
        return vo;
    }

    @Override
    public OrderDetailVO getOrderDetail(Long id) {
        OrderDetailVO vo = new OrderDetailVO();
        OmsOrder order = this.getById(id);
        if (order == null) {
            throw new BaseException("订单不存在");
        }
        // 订单信息
        vo.setOrder(BeanMapUtil.to(order, OmsOrderVO::new));
        // 会员信息
        vo.setMember(BeanMapUtil.to(umsMemberService.getById(order.getMemberId()), UmsMemberVO::new));
        // 订单详情
        vo.setOrderDetail(BeanMapUtil.to(omsOrderDetailService.listByOrderNo(order.getOrderNo()), OmsOrderDetailVO::new));
        // 订单日志
        vo.setOrderLog(BeanMapUtil.to(omsOrderLogService.listByOrderId(id), OmsOrderLogVO::new));
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void returnOrder(Set<Long> ids) {
        // 根据 id 转为订单信息
        ids.stream().map(this::getById)
                // 循环退单
                .forEach(order -> {
                    // 查询订单详情
                    List<OmsOrderDetail> orderDetails = omsOrderDetailService.listByOrderNo(order.getOrderNo());
                    // 累计整单需要退的金额和积分
                    AtomicReference<BigDecimal> returnPrice = new AtomicReference<>(BigDecimal.ZERO);
                    AtomicReference<Integer> returnPoints = new AtomicReference<>(0);
                    orderDetails.forEach(orderDetail -> {
                        int returnQty = orderDetail.getQuantity() - orderDetail.getReturnQuantity();
                        returnPrice.set(returnPrice.get().add(orderDetail.getGoodsPrice().multiply(new BigDecimal(returnQty))));
                        returnPoints.set(returnPoints.get() + (orderDetail.getSalePoints() * returnQty));
                        // 更新库存
                        gmsGoodsService.updateStock(orderDetail.getGoodsId(), returnQty);
                        // 修改订单详情
                        orderDetail.setReturnQuantity(orderDetail.getQuantity());
                        orderDetail.setStatus(OrderStatusEnum.RETURN.name());
                    });
                    // 退款和退积分
                    umsMemberService.rebate(order.getMemberId(), returnPrice.get(), returnPoints.get());
                    // 修改订单最终销售额
                    order.setFinalSalesAmount(BigDecimal.ZERO);
                    order.setStatus(OrderStatusEnum.RETURN.name());
                    this.updateById(order);
                    omsOrderDetailService.updateBatchById(orderDetails);
                    // 订单日志
                    OmsOrderLog log = new OmsOrderLog();
                    log.setOrderId(order.getId());
                    log.setDescription("<span style=\"color:red\">退单</span>");
                    omsOrderLogService.save(log);
                });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void returnGoods(ReturnGoodsDTO returnGoodsDTO) {
        // 修改明细
        Integer returnQty = returnGoodsDTO.getQuantity();
        OmsOrderDetail orderDetail = omsOrderDetailService.getById(returnGoodsDTO.getId());
        orderDetail.setReturnQuantity(orderDetail.getReturnQuantity() + returnQty);
        if (orderDetail.getReturnQuantity().compareTo(orderDetail.getQuantity()) == 0) {
            orderDetail.setStatus(OrderStatusEnum.RETURN.name());
        } else if (orderDetail.getReturnQuantity().compareTo(orderDetail.getQuantity()) > 0) {
            throw new BaseException("退货数量不能超过商品数量");
        }
        omsOrderDetailService.updateById(orderDetail);
        // 修改订单
        OmsOrder order = this.lambdaQuery().eq(OmsOrder::getOrderNo, orderDetail.getOrderNo()).one();
        BigDecimal returnPrice = orderDetail.getGoodsPrice().multiply(new BigDecimal(returnQty));
        BigDecimal finalSalesAmount = order.getFinalSalesAmount().subtract(returnPrice);
        order.setFinalSalesAmount(finalSalesAmount);
        this.updateById(order);
        // 退款和抵用券
        Integer returnPoints = orderDetail.getSalePoints() * returnQty;
        umsMemberService.rebate(order.getMemberId(), returnPrice, returnPoints);
        // 更新库存
        gmsGoodsService.updateStock(orderDetail.getGoodsId(), returnQty);
        // 订单日志
        OmsOrderLog log = new OmsOrderLog();
        log.setOrderId(order.getId());
        log.setDescription("<span style=\"color:red\">退货</span>" + orderDetail.getGoodsName() + " X " + returnQty);
        omsOrderLogService.save(log);
    }

}

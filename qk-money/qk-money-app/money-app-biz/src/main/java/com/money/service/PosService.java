package com.money.service;

import com.money.dto.OmsOrder.OmsOrderVO;
import com.money.dto.pos.PosGoodsVO;
import com.money.dto.pos.PosMemberVO;
import com.money.dto.pos.SettleAccountsDTO;

import java.util.List;

/**
 * @author : money
 * @version : 1.0.0
 * @description : pos服务
 * @createTime : 2023-05-10 22:25:42
 */
public interface PosService {

    /**
     * 商品列表
     *
     * @param barcode 条形码
     * @return {@link List}<{@link PosGoodsVO}>
     */
    List<PosGoodsVO> listGoods(String barcode);

    /**
     * 成员列表
     *
     * @param member 成员
     * @return {@link List}<{@link PosMemberVO}>
     */
    List<PosMemberVO> listMember(String member);

    /**
     * 结算
     *
     * @param settleAccountsDTO 结算dto
     * @return {@link OmsOrderVO}
     */
    OmsOrderVO settleAccounts(SettleAccountsDTO settleAccountsDTO);
}

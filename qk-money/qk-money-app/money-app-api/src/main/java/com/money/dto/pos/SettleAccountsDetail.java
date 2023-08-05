package com.money.dto.pos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SettleAccountsDetail {

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 状态
     */
    private String status;

    /**
     * 商品id
     */
    private Long goodsId;

    /**
     * 商品条形码
     */
    private String goodsBarcode;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 实际单价
     */
    private BigDecimal goodsPrice;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 售价
     */
    private BigDecimal salePrice;

    /**
     * 进价
     */
    private BigDecimal purchasePrice;

    /**
     * 会员价
     */
    private BigDecimal vipPrice;

    /**
     * 消耗积分
     */
    private BigDecimal salePoints;

    /**
     * 退货数量
     */
    private Integer returnQuantity;
}

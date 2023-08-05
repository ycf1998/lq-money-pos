package com.money.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.money.mb.base.BaseEntity;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author money
 * @since 2023-04-30
 */
@Getter
@Setter
@TableName("oms_order_detail")
public class OmsOrderDetail extends BaseEntity {

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
     * 商品条码
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
     * 消耗积分
     */
    private Integer salePoints;

    /**
     * 退货数量
     */
    private Integer returnQuantity;

    /**
     * 租户id
     */
    private Long tenantId;

}

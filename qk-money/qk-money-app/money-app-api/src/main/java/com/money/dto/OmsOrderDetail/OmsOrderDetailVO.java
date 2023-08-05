package com.money.dto.OmsOrderDetail;

import java.math.BigDecimal;
import lombok.Data;

/**
* <p>
* 
* </p>
*
* @author money
* @since 2023-04-30
*/
@Data
public class OmsOrderDetailVO {

    private Long id;

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

}

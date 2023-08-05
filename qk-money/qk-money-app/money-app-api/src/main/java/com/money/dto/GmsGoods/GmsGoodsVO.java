package com.money.dto.GmsGoods;

import java.math.BigDecimal;
import lombok.Data;

/**
* <p>
* 
* </p>
*
* @author money
* @since 2023-04-22
*/
@Data
public class GmsGoodsVO {

    private Long id;

    /**
    * 条码
    */
    private String barcode;

    /**
    * 商品名称
    */
    private String name;

    /**
    * 商品图片
    */
    private String pic;

    /**
    * 单位
    */
    private String unit;

    /**
    * 规格
    */
    private String size;

    /**
    * 描述
    */
    private String description;

    /**
    * 进价
    */
    private BigDecimal purchasePrice;

    /**
    * 售价
    */
    private BigDecimal salePrice;

    /**
    * 消耗积分
    */
    private Integer salePoints;

    /**
    * 库存
    */
    private Long stock;

    /**
    * 销量
    */
    private Long sales;

    /**
    * 状态
    */
    private String status;

}

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
 * @since 2023-04-22
 */
@Getter
@Setter
@TableName("gms_goods")
public class GmsGoods extends BaseEntity {

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

    /**
     * 租户id
     */
    private Long tenantId;

}

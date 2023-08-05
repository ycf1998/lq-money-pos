package com.money.dto.GmsGoods;

import com.money.common.dto.ValidGroup;
import com.money.constant.GoodsStatus;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
* <p>
* 
* </p>
*
* @author money
* @since 2023-04-22
*/
@Data
public class GmsGoodsDTO {

    @NotNull(groups = ValidGroup.Update.class)
    private Long id;

    /**
    * 条码
    */
    @NotBlank(groups = {ValidGroup.Save.class, ValidGroup.Update.class})
    private String barcode;

    /**
    * 商品名称
    */
    @NotBlank(groups = {ValidGroup.Save.class, ValidGroup.Update.class})
    private String name;

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
    @NotNull(groups = {ValidGroup.Save.class, ValidGroup.Update.class})
    @DecimalMin(value = "0", groups = {ValidGroup.Save.class, ValidGroup.Update.class})
    private BigDecimal purchasePrice;

    /**
    * 售价
    */
    @NotNull(groups = {ValidGroup.Save.class, ValidGroup.Update.class})
    @DecimalMin(value = "0", groups = {ValidGroup.Save.class, ValidGroup.Update.class})
    private BigDecimal salePrice;

    /**
    * 消耗积分
    */
    @NotNull(groups = {ValidGroup.Save.class, ValidGroup.Update.class})
    @Min(value = 0, groups = {ValidGroup.Save.class, ValidGroup.Update.class})
    private Integer salePoints;

    /**
    * 库存
    */
    @NotNull(groups = {ValidGroup.Save.class, ValidGroup.Update.class})
    @Min(value = 0, groups = {ValidGroup.Save.class, ValidGroup.Update.class})
    private Long stock;


    /**
    * 状态
    */
    private GoodsStatus status = GoodsStatus.SALE;

}

package com.money.dto.GmsGoods;

import com.money.common.dto.QueryRequest;
import com.money.constant.GoodsStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* <p>
* 
* </p>
*
* @author money
* @since 2023-04-22
*/
@Data
@EqualsAndHashCode(callSuper = true)
public class GmsGoodsQueryDTO extends QueryRequest {

    /**
    * 条码
    */
    private String barcode;

    /**
    * 商品名称
    */
    private String name;

    /**
    * 状态
    */
    private GoodsStatus status = GoodsStatus.SALE;
}

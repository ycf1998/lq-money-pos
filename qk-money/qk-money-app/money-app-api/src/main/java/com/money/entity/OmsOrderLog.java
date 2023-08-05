package com.money.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.money.mb.base.BaseEntity;
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
@TableName("oms_order_log")
public class OmsOrderLog extends BaseEntity {

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 描述
     */
    private String description;

    /**
     * 租户id
     */
    private Long tenantId;

}

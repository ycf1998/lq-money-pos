package com.money.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.money.mb.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
@TableName("oms_order")
public class OmsOrder extends BaseEntity {

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 会员名
     */
    private String member;

    /**
     * 会员id
     */
    private Long memberId;

    /**
     * 状态
     */
    private String status;

    /**
     * 总成本
     */
    private BigDecimal costAmount;

    /**
     * 总价
     */
    private BigDecimal totalAmount;

    /**
     * 实付款
     */
    private BigDecimal payAmount;

    /**
     * 消耗积分
     */
    private Integer pointsAmount;

    /**
     * 最终销售金额
     */
    private BigDecimal finalSalesAmount;

    /**
     * 备注
     */
    private String remark;

    /**
     * 支付时间
     */
    private LocalDateTime paymentTime;

    /**
     * 租户id
     */
    private Long tenantId;

}

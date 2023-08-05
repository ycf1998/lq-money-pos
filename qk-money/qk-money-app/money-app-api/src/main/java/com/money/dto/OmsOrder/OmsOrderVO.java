package com.money.dto.OmsOrder;

import lombok.Data;

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
@Data
public class OmsOrderVO {

    private Long id;

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
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
    * 支付时间
    */
    private LocalDateTime paymentTime;

}

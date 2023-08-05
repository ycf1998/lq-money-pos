package com.money.dto.OmsOrder;

import com.money.common.dto.QueryRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@EqualsAndHashCode(callSuper = true)
public class OmsOrderQueryDTO extends QueryRequest {

    /**
    * 订单号
    */
    private String orderNo;

    /**
    * 会员名
    */
    private String member;

    /**
    * 状态
    */
    private String status;

    /**
    * 开始时间
    */
    private LocalDateTime startTime;

    /**
    * 结束时间
    */
    private LocalDateTime endTime;
}

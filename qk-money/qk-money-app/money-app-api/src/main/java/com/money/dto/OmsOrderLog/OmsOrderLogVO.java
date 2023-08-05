package com.money.dto.OmsOrderLog;

import lombok.Data;

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
public class OmsOrderLogVO {

    private Long id;

    /**
    * 订单id
    */
    private Long orderId;

    /**
    * 描述
    */
    private String description;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}

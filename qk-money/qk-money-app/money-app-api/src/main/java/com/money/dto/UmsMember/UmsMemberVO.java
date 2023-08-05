package com.money.dto.UmsMember;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author money
 * @since 2023-04-08
 */
@Data
@Schema(description = "")
public class UmsMemberVO {

    private Long id;

    /**
     * 会员名称
     */
    private String name;

    /**
     * 会员类型
     */
    private String type;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 积分
     */
    private Integer points;

    /**
     * 总消费金额
     */
    private BigDecimal consumeAmount;

    /**
     * 消费次数
     */
    private Integer consumeTimes;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

}
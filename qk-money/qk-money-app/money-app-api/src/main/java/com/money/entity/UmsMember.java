package com.money.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.money.mb.base.BaseEntity;
import java.math.BigDecimal;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author money
 * @since 2023-04-08
 */
@Getter
@Setter
@TableName("ums_member")
@Schema(description = "")
public class UmsMember extends BaseEntity {

    @Schema(description="会员名称")
    private String name;

    @Schema(description="会员类型")
    private String type;

    @Schema(description="联系电话")
    private String phone;

    @Schema(description="积分")
    private Integer points;

    @Schema(description="总消费金额")
    private BigDecimal consumeAmount;

    @Schema(description="消费次数")
    private Integer consumeTimes;

    @Schema(description="备注")
    private String remark;

    @Schema(description="租户id")
    private Long tenantId;

}

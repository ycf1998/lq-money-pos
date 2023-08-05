package com.money.entity;

import com.money.mb.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author money
 * @since 2023-03-25
 */
@Getter
@Setter
@Schema(description = "")
public class Demo extends BaseEntity {

    @Schema(description="名称")
    private String name;

    @Schema(description="状态")
    private String status;

    @Schema(description="租户id")
    private Long tenantId;

}

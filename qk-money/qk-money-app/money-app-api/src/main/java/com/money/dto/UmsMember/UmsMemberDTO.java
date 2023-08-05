package com.money.dto.UmsMember;

import com.money.common.dto.ValidGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * <p>
 *
 * </p>
 *
 * @author money
 * @since 2023-04-08
 */
@Data
public class UmsMemberDTO {

    @NotNull(groups = ValidGroup.Update.class)
    private Long id;

    /**
     * 会员名称
     */
    @NotBlank(groups = {ValidGroup.Save.class, ValidGroup.Update.class})
    private String name;

    /**
     * 会员类型
     */
    @NotBlank(groups = {ValidGroup.Save.class, ValidGroup.Update.class})
    private String type;

    /**
     * 联系电话
     */
    @NotBlank(groups = {ValidGroup.Save.class, ValidGroup.Update.class})
    private String phone;

    /**
     * 备注
     */
    @Size(max = 255, groups = {ValidGroup.Save.class, ValidGroup.Update.class})
    private String remark;

}
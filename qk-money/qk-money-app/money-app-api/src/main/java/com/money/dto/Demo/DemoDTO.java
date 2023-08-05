package com.money.dto.Demo;

import com.money.common.dto.ValidGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
* <p>
* 
* </p>
*
* @author money
* @since 2023-03-25
*/
@Data
@Schema(description = "")
public class DemoDTO {

    @NotNull(groups = ValidGroup.Update.class)
    private Long id;

    @NotBlank(message = "名称不允许为空", groups = {ValidGroup.Save.class, ValidGroup.Update.class})
    @Schema(description="名称")
    private String name;

    @Schema(description="状态")
    @NotBlank(message = "状态不允许为空", groups = {ValidGroup.Save.class, ValidGroup.Update.class})
    private String status;

}

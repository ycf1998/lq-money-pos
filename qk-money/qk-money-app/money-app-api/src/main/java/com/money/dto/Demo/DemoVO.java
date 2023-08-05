package com.money.dto.Demo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

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
public class DemoVO {

    private Long id;

    @Schema(description="名称")
    private String name;

    @Schema(description="状态")
    private String status;

    @Schema(description="创建时间")
    private LocalDateTime createTime;

    @Schema(description="更新时间")
    private LocalDateTime updateTime;

}

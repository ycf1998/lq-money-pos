package com.money.dto.Demo;

import com.money.common.dto.QueryRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* <p>
* 
* </p>
*
* @author money
* @since 2023-03-25
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "")
public class DemoQueryDTO extends QueryRequest {

    @Schema(description="名称")
    private String name;

    @Schema(description="状态")
    private String status;
}

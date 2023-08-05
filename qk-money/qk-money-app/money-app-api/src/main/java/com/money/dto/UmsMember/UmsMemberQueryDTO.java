package com.money.dto.UmsMember;

import com.money.common.dto.QueryRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* <p>
*
* </p>
*
* @author money
* @since 2023-04-08
*/
@Data
@EqualsAndHashCode(callSuper = true)
public class UmsMemberQueryDTO extends QueryRequest {

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

}

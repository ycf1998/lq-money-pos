package com.money.dto.pos;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author : money
 * @version : 1.0.0
 * @description : 结算dto
 * @createTime : 2022-04-19 23:58:51
 */
@Data
public class SettleAccountsDTO {

    /**
     * 会员
     */
    private Long member;

    /**
     * 结算明细
     */
    @NotEmpty
    private List<SettleAccountsDetail> details;
}

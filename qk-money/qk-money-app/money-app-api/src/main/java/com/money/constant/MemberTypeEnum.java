package com.money.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberTypeEnum {
    /**
     * 普通会员
     */
    MEMBER(0),
    /**
     * 黄金会员
     */
    HJ_VIP(1000),
    /**
     * 铂金会员
     */
    BJ_VIP(5000),
    ;
    /**
     * 需达到积分
     */
    private final Integer points;
}
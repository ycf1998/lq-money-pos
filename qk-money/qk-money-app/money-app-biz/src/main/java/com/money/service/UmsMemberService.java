package com.money.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.money.common.vo.PageVO;
import com.money.dto.UmsMember.UmsMemberDTO;
import com.money.dto.UmsMember.UmsMemberQueryDTO;
import com.money.dto.UmsMember.UmsMemberVO;
import com.money.entity.UmsMember;

import java.math.BigDecimal;
import java.util.Set;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author money
 * @since 2023-04-08
 */
public interface UmsMemberService extends IService<UmsMember> {

    PageVO<UmsMemberVO> list(UmsMemberQueryDTO queryDTO);

    void add(UmsMemberDTO addDTO);

    void update(UmsMemberDTO updateDTO);

    void delete(Set<Long> ids);

    /**
     * 消费
     *
     * @param id     会员id
     * @param amount 消费金额
     * @param points 消费积分
     */
    void consume(Long id, BigDecimal amount, Integer points);

    /**
     * 退款
     *
     * @param id           会员id
     * @param returnAmount 退还金额
     * @param returnPoints 退还积分
     */
    void rebate(Long id, BigDecimal returnAmount, Integer returnPoints);
}

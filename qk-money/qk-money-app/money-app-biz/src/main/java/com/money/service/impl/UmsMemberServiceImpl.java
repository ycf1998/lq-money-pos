package com.money.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.money.common.exception.BaseException;
import com.money.common.vo.PageVO;
import com.money.constant.MemberTypeEnum;
import com.money.dto.UmsMember.UmsMemberDTO;
import com.money.dto.UmsMember.UmsMemberQueryDTO;
import com.money.dto.UmsMember.UmsMemberVO;
import com.money.entity.UmsMember;
import com.money.mapper.UmsMemberMapper;
import com.money.service.UmsMemberService;
import com.money.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Set;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author money
 * @since 2023-04-08
 */
@Service
@RequiredArgsConstructor
public class UmsMemberServiceImpl extends ServiceImpl<UmsMemberMapper, UmsMember> implements UmsMemberService {

    @Override
    public PageVO<UmsMemberVO> list(UmsMemberQueryDTO queryDTO) {
        Page<UmsMember> page = this.lambdaQuery()
                // 若查询字段不为空，则需要过滤
                .like(StrUtil.isNotBlank(queryDTO.getName()), UmsMember::getName, queryDTO.getName())
                .like(StrUtil.isNotBlank(queryDTO.getPhone()), UmsMember::getPhone, queryDTO.getPhone())
                .eq(StrUtil.isNotBlank(queryDTO.getType()), UmsMember::getType, queryDTO.getType())
                // 如果没有传排序字段，则默认使用更新时间降序排序
                .orderByDesc(StrUtil.isBlank(queryDTO.getSort()), UmsMember::getUpdateTime)
                .last(StrUtil.isNotBlank(queryDTO.getSort()), queryDTO.getOrderBySql())
                .page(PageUtil.toPage(queryDTO));
        return PageUtil.toPageVO(page, UmsMemberVO::new);
    }

    @Override
    public void add(UmsMemberDTO addDTO) {
        boolean exists = this.lambdaQuery().eq(UmsMember::getPhone, addDTO.getPhone()).exists();
        if (exists) {
            throw new BaseException("手机号码已存在");
        }
        UmsMember umsMember = new UmsMember();
        BeanUtil.copyProperties(addDTO, umsMember);
        this.save(umsMember);
    }

    @Override
    public void update(UmsMemberDTO updateDTO) {
        boolean exists = this.lambdaQuery().ne(UmsMember::getId, updateDTO.getId()).eq(UmsMember::getPhone, updateDTO.getPhone()).exists();
        if (exists) {
            throw new BaseException("手机号码已存在");
        }
        UmsMember umsMember = new UmsMember();
        BeanUtil.copyProperties(updateDTO, umsMember);
        this.updateById(umsMember);
    }

    @Override
    public void delete(Set<Long> ids) {
        this.removeByIds(ids);
    }

    @Override
    public void consume(Long id, BigDecimal amount, Integer points) {
        // 消费金额和积分 1：1，不足1直接舍弃
        Integer getPoints = amount.intValue();
        // 最终获得的积分 = 本次获得积分 - 消费积分
        points = getPoints - points;

        UmsMember umsMember = this.getById(id);
        // 增加总消费金额
        umsMember.setConsumeAmount(umsMember.getConsumeAmount().add(amount));
        // 增加/减少积分
        umsMember.setPoints(umsMember.getPoints() + points);
        // 消费次数加1
        umsMember.setConsumeTimes(umsMember.getConsumeTimes() + 1);
        // 会员类型判定，当前积分大于类型要求积分升级，否则降级
        for (MemberTypeEnum typeEnum : MemberTypeEnum.values()) {
            if (umsMember.getPoints() >= typeEnum.getPoints()) {
                umsMember.setType(typeEnum.name());
            }
        }
        // 更新
        this.updateById(umsMember);
    }

    @Override
    public void rebate(Long id, BigDecimal returnAmount, Integer returnPoints) {
        UmsMember umsMember = this.getById(id);
        if (umsMember == null) return;
        // 减少总消费金额
        umsMember.setConsumeAmount(umsMember.getConsumeAmount().subtract(returnAmount));
        // 增加/减少积分
        umsMember.setPoints(umsMember.getPoints() + returnPoints);
        // 会员等级判定
        for (MemberTypeEnum typeEnum : MemberTypeEnum.values()) {
            if (umsMember.getPoints() >= typeEnum.getPoints()) {
                umsMember.setType(typeEnum.name());
            }
        }
        // 更新
        this.updateById(umsMember);
    }

}

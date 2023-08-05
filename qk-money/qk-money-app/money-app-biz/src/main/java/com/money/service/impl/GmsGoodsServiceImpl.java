package com.money.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.money.common.vo.PageVO;
import com.money.dto.GmsGoods.GmsGoodsDTO;
import com.money.dto.GmsGoods.GmsGoodsQueryDTO;
import com.money.dto.GmsGoods.GmsGoodsVO;
import com.money.entity.GmsGoods;
import com.money.mapper.GmsGoodsMapper;
import com.money.service.GmsGoodsService;
import com.money.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author money
 * @since 2023-04-22
 */
@Service
@RequiredArgsConstructor
public class GmsGoodsServiceImpl extends ServiceImpl<GmsGoodsMapper, GmsGoods> implements GmsGoodsService {

    @Override
    public PageVO<GmsGoodsVO> list(GmsGoodsQueryDTO queryDTO) {
        Page<GmsGoods> page = this.lambdaQuery()
                .like(StrUtil.isNotBlank(queryDTO.getBarcode()), GmsGoods::getBarcode, queryDTO.getBarcode())
                .like(StrUtil.isNotBlank(queryDTO.getName()), GmsGoods::getName, queryDTO.getName())
                .eq(ObjectUtil.isNotNull(queryDTO.getStatus()), GmsGoods::getStatus, queryDTO.getStatus())
                .orderByDesc(StrUtil.isBlank(queryDTO.getSort()), GmsGoods::getUpdateTime)
                .last(StrUtil.isNotBlank(queryDTO.getSort()), queryDTO.getOrderBySql())
                .page(PageUtil.toPage(queryDTO));
        return PageUtil.toPageVO(page, GmsGoodsVO::new);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(GmsGoodsDTO addDTO) {
        GmsGoods gmsGoods = new GmsGoods();
        BeanUtil.copyProperties(addDTO, gmsGoods);
        this.save(gmsGoods);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(GmsGoodsDTO updateDTO) {
        GmsGoods gmsGoods = new GmsGoods();
        BeanUtil.copyProperties(updateDTO, gmsGoods);
        this.updateById(gmsGoods);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        this.removeByIds(ids);
    }

}

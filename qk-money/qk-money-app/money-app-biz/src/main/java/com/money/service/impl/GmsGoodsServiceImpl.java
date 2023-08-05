package com.money.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.money.common.exception.BaseException;
import com.money.common.vo.PageVO;
import com.money.constant.GoodsStatus;
import com.money.dto.GmsGoods.GmsGoodsDTO;
import com.money.dto.GmsGoods.GmsGoodsQueryDTO;
import com.money.dto.GmsGoods.GmsGoodsVO;
import com.money.entity.GmsGoods;
import com.money.mapper.GmsGoodsMapper;
import com.money.oss.OSSDelegate;
import com.money.oss.core.FileNameStrategy;
import com.money.oss.core.FolderPath;
import com.money.oss.local.LocalOSS;
import com.money.service.GmsGoodsService;
import com.money.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author money
 * @since 2023-04-22
 */
@Service
@RequiredArgsConstructor
public class GmsGoodsServiceImpl extends ServiceImpl<GmsGoodsMapper, GmsGoods> implements GmsGoodsService {

    private final OSSDelegate<LocalOSS> localOSS;

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
    public void add(GmsGoodsDTO addDTO, MultipartFile pic) {
        boolean exists = this.lambdaQuery().eq(GmsGoods::getBarcode, addDTO.getBarcode()).exists();
        if (exists) {
            throw new BaseException("条码已存在");
        }
        GmsGoods gmsGoods = new GmsGoods();
        BeanUtil.copyProperties(addDTO, gmsGoods);
        // 上传图片
        if (pic != null) {
            String picUrl = localOSS.upload(pic, FolderPath.builder().cd("goods").build(), FileNameStrategy.TIMESTAMP);
            gmsGoods.setPic(picUrl);
        }
        this.save(gmsGoods);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(GmsGoodsDTO updateDTO, MultipartFile pic) {
        boolean exists = this.lambdaQuery().ne(GmsGoods::getId, updateDTO.getId()).eq(GmsGoods::getBarcode, updateDTO.getBarcode()).exists();
        if (exists) {
            throw new BaseException("条码已存在");
        }
        GmsGoods gmsGoods = this.getById(updateDTO.getId());
        BeanUtil.copyProperties(updateDTO, gmsGoods);
        // 调整商品状态
        if (GoodsStatus.SOLD_OUT.name().equals(gmsGoods.getStatus()) && updateDTO.getStock() > 0) {
            gmsGoods.setStatus(GoodsStatus.SALE.name());
        }
        if (GoodsStatus.SALE.name().equals(gmsGoods.getStatus()) && updateDTO.getStock() <= 0) {
            gmsGoods.setStatus(GoodsStatus.SOLD_OUT.name());
        }
        // 上传图片
        if (pic != null) {
            localOSS.delete(gmsGoods.getPic());
            String picUrl = localOSS.upload(pic, FolderPath.builder().cd("goods").build(), FileNameStrategy.TIMESTAMP);
            gmsGoods.setPic(picUrl);
        }
        this.updateById(gmsGoods);
    }

    @Override
    public void delete(Set<Long> ids) {
        List<GmsGoods> gmsGoodsList = this.listByIds(ids);
        this.removeByIds(ids);
        gmsGoodsList.forEach(gmsGoods -> {
            if (StrUtil.isNotBlank(gmsGoods.getPic())) {
                localOSS.delete(gmsGoods.getPic());
            }
        });
    }

    @Override
    public void updateStock(Long goodsId, Integer qty) {
        if (qty != 0) {
            GmsGoods byId = this.getById(goodsId);
            byId.setStock(byId.getStock() + qty);
            byId.setSales(byId.getSales() + 1);
            // 更新状态
            byId.setStatus(byId.getStock() > 0 ? GoodsStatus.SALE.name() : GoodsStatus.SOLD_OUT.name());
            this.updateById(byId);
        }
    }

}

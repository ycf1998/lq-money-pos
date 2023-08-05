package com.money.service;

import com.money.entity.GmsGoods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.money.common.vo.PageVO;
import com.money.dto.GmsGoods.GmsGoodsDTO;
import com.money.dto.GmsGoods.GmsGoodsQueryDTO;
import com.money.dto.GmsGoods.GmsGoodsVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author money
 * @since 2023-04-22
 */
public interface GmsGoodsService extends IService<GmsGoods> {

    PageVO<GmsGoodsVO> list(GmsGoodsQueryDTO queryDTO);

    void add(GmsGoodsDTO addDTO, MultipartFile pic);

    void update(GmsGoodsDTO updateDTO, MultipartFile pic);

    void delete(Set<Long> ids);

    /**
     * 更新库存
     *
     * @param goodsId 商品id
     * @param qty     数量（正数增加，负数减少）
     */
    void updateStock(Long goodsId, Integer qty);
}

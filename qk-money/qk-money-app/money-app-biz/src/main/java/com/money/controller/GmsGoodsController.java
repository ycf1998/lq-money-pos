package com.money.controller;

import com.money.common.dto.ValidGroup;
import com.money.common.vo.PageVO;
import com.money.dto.GmsGoods.GmsGoodsDTO;
import com.money.dto.GmsGoods.GmsGoodsQueryDTO;
import com.money.dto.GmsGoods.GmsGoodsVO;
import com.money.service.GmsGoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author money
 * @since 2023-04-22
 */
@RestController
@RequestMapping("/gmsGoods")
@RequiredArgsConstructor
public class GmsGoodsController {

    private final GmsGoodsService gmsGoodsService;

    @GetMapping
    @PreAuthorize("@rbac.hasPermission('gmsGoods:list')")
    public PageVO<GmsGoodsVO> list(@Validated GmsGoodsQueryDTO queryDTO) {
        return gmsGoodsService.list(queryDTO);
    }

    @PostMapping
    @PreAuthorize("@rbac.hasPermission('gmsGoods:add')")
    public void add(@Validated(ValidGroup.Save.class) @RequestPart("goods") GmsGoodsDTO addDTO,
                    @RequestPart(required = false) MultipartFile pic) {
        gmsGoodsService.add(addDTO, pic);
    }

    @PutMapping
    @PreAuthorize("@rbac.hasPermission('gmsGoods:edit')")
    public void update(@Validated(ValidGroup.Update.class) @RequestPart("goods") GmsGoodsDTO updateDTO,
                       @RequestPart(required = false) MultipartFile pic) {
        gmsGoodsService.update(updateDTO, pic);
    }

    @DeleteMapping
    @PreAuthorize("@rbac.hasPermission('gmsGoods:del')")
    public void delete(@RequestBody Set<Long> ids) {
        gmsGoodsService.delete(ids);
    }
}

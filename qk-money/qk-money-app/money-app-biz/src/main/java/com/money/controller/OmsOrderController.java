package com.money.controller;

import com.money.common.vo.PageVO;
import com.money.dto.OmsOrder.*;
import com.money.service.OmsOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author money
 * @since 2023-04-30
 */
@RestController
@RequestMapping("/omsOrder")
@RequiredArgsConstructor
public class OmsOrderController {

    private final OmsOrderService omsOrderService;

    @GetMapping
    @PreAuthorize("@rbac.hasPermission('omsOrder:list')")
    public PageVO<OmsOrderVO> list(@Validated OmsOrderQueryDTO queryDTO) {
        return omsOrderService.list(queryDTO);
    }

    @GetMapping("/count")
    @PreAuthorize("@rbac.hasPermission('omsOrder:list')")
    public OrderCountVO orderCount(LocalDateTime startTime, LocalDateTime endTime) {
        return omsOrderService.getOrderCount(startTime, endTime);
    }

    @GetMapping("/detail")
    @PreAuthorize("@rbac.hasPermission('omsOrder:list')")
    public OrderDetailVO orderDetail(@RequestParam Long id) {
        return omsOrderService.getOrderDetail(id);
    }

    @DeleteMapping("/returnOrder")
    @PreAuthorize("@rbac.hasPermission('omsOrder:edit')")
    public void returnOrder(@RequestBody Set<Long> ids) {
        omsOrderService.returnOrder(ids);
    }

    @DeleteMapping("/returnGoods")
    @PreAuthorize("@rbac.hasPermission('omsOrder:edit')")
    public void returnGoods(@Valid @RequestBody ReturnGoodsDTO returnGoodsDTO) {
        omsOrderService.returnGoods(returnGoodsDTO);
    }

}

package com.money.controller;

import com.money.common.vo.PageVO;
import com.money.dto.OmsOrder.OmsOrderQueryDTO;
import com.money.dto.OmsOrder.OmsOrderVO;
import com.money.service.OmsOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
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

}

package com.money.controller;

import com.money.dto.OmsOrder.OmsOrderVO;
import com.money.dto.pos.PosGoodsVO;
import com.money.dto.pos.PosMemberVO;
import com.money.dto.pos.SettleAccountsDTO;
import com.money.service.PosService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pos")
@RequiredArgsConstructor
public class PosController {

    private final PosService posService;

    @GetMapping("/goods")
    @PreAuthorize("@rbac.hasPermission('pos:cashier')")
    public List<PosGoodsVO> listGoods(String barcode) {
        return posService.listGoods(barcode);
    }

    @GetMapping("/members")
    @PreAuthorize("@rbac.hasPermission('pos:cashier')")
    public List<PosMemberVO> listMember(String member) {
        return posService.listMember(member);
    }

    @PostMapping("/settleAccounts")
    @PreAuthorize("@rbac.hasPermission('pos:cashier')")
    public OmsOrderVO settleAccounts(@Validated @RequestBody SettleAccountsDTO settleAccountsDTO) {
        return posService.settleAccounts(settleAccountsDTO);
    }
}

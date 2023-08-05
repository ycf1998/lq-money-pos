package com.money.controller;

import com.money.common.dto.ValidGroup;
import com.money.common.vo.PageVO;
import com.money.dto.Demo.DemoDTO;
import com.money.dto.Demo.DemoQueryDTO;
import com.money.dto.Demo.DemoVO;
import com.money.service.DemoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author money
 * @since 2023-03-25
 */
@Tag(name = "demo", description = "")
@RestController
@RequestMapping("/demo")
@RequiredArgsConstructor
public class DemoController {

    private final DemoService demoService;

    @Operation(summary = "分页查询")
    @GetMapping
    public PageVO<DemoVO> list(@Validated DemoQueryDTO queryDTO) {
        return demoService.list(queryDTO);
    }

    @Operation(summary = "添加")
    @PostMapping
    public void add(@Validated(ValidGroup.Save.class) @RequestBody DemoDTO addDTO) {
        demoService.add(addDTO);
    }

    @Operation(summary = "修改")
    @PutMapping
    public void update(@Validated(ValidGroup.Update.class) @RequestBody DemoDTO updateDTO) {
        demoService.update(updateDTO);
    }

    @Operation(summary = "删除")
    @DeleteMapping
    public void delete(@RequestBody Set<Long> ids) {
        demoService.delete(ids);
    }
}
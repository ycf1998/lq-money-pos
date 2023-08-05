package com.money.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.money.common.vo.PageVO;
import com.money.dto.Demo.DemoDTO;
import com.money.dto.Demo.DemoQueryDTO;
import com.money.dto.Demo.DemoVO;
import com.money.entity.Demo;

import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author money
 * @since 2023-03-25
 */
public interface DemoService extends IService<Demo> {

    PageVO<DemoVO> list(DemoQueryDTO queryDTO);

    void add(DemoDTO addDTO);

    void update(DemoDTO updateDTO);

    void delete(Set<Long> ids);
}

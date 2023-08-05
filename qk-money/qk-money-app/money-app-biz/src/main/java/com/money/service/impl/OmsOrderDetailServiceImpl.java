package com.money.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.money.entity.OmsOrderDetail;
import com.money.mapper.OmsOrderDetailMapper;
import com.money.service.OmsOrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author money
 * @since 2023-04-30
 */
@Service
@RequiredArgsConstructor
public class OmsOrderDetailServiceImpl extends ServiceImpl<OmsOrderDetailMapper, OmsOrderDetail> implements OmsOrderDetailService {

}

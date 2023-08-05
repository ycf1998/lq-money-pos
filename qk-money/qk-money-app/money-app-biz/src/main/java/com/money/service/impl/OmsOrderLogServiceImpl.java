package com.money.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.money.entity.OmsOrderLog;
import com.money.mapper.OmsOrderLogMapper;
import com.money.service.OmsOrderLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
public class OmsOrderLogServiceImpl extends ServiceImpl<OmsOrderLogMapper, OmsOrderLog> implements OmsOrderLogService {

    @Override
    public List<OmsOrderLog> listByOrderId(Long orderId) {
        return this.lambdaQuery().eq(OmsOrderLog::getOrderId, orderId).list();
    }
}

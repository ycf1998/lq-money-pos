package com.money.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.money.entity.OmsOrderDetail;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author money
 * @since 2023-04-30
 */
public interface OmsOrderDetailService extends IService<OmsOrderDetail> {

    /**
     * 根据订单号查询
     *
     * @param orderNo 订单号
     * @return {@link List}<{@link OmsOrderDetail}>
     */
    List<OmsOrderDetail> listByOrderNo(String orderNo);

}

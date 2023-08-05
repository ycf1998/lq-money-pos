package com.money.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.money.common.vo.PageVO;
import com.money.dto.OmsOrder.OmsOrderQueryDTO;
import com.money.dto.OmsOrder.OmsOrderVO;
import com.money.entity.OmsOrder;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author money
 * @since 2023-04-30
 */
public interface OmsOrderService extends IService<OmsOrder> {

    PageVO<OmsOrderVO> list(OmsOrderQueryDTO queryDTO);

}

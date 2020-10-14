package com.syp.order.service.provider.service.impl;

import com.syp.order.service.provider.controller.dto.OrderDTO;
import com.syp.order.service.provider.service.IOrderService;
import org.springframework.stereotype.Service;

/**
 * @Author: SYP
 * @Date: 2020/9/26
 * @Description:
 */
@Service
public class OrderServiceImpl implements IOrderService {
    @Override
    public String createOrder(OrderDTO orderDTO) {
        //锁库存
        //查询商品信息
        //创建订单
        return null;
    }
}

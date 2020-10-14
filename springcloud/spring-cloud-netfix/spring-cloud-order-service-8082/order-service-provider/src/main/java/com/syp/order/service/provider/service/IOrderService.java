package com.syp.order.service.provider.service;

import com.syp.order.service.provider.controller.dto.OrderDTO;

/**
 * @Author: SYP
 * @Date: 2020/9/26
 * @Description:
 */
public interface IOrderService {
    String createOrder(OrderDTO orderDTO);
}

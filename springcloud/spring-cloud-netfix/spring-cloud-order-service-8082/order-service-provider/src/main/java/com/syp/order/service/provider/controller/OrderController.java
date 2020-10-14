package com.syp.order.service.provider.controller;

import com.syp.order.service.provider.controller.dto.OrderDTO;
import com.syp.order.service.provider.service.IOrderService;
import com.syp.springcloud.api.R;
import com.syp.springcloud.exception.ValidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: SYP
 * @Date: 2020/9/26
 * @Description:
 */
@RestController
public class OrderController {

    @Autowired
    IOrderService orderService;

    @PostMapping("/order")
    public R order(@RequestBody @Validated OrderDTO orderDTO, BindingResult bindingResult){
        orderDTO.validData(bindingResult);
        String orderId = orderService.createOrder(orderDTO);
        return new R.Builder<>().setData(orderId).buildOk();
    }
}

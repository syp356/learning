package com.syp.order.service.provider.controller.dto;

import com.syp.springcloud.exception.ValidException;
import lombok.Data;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author: SYP
 * @Date: 2020/9/26
 * @Description:
 */
@Data
public class OrderDTO {
    @NotNull(message = "name不能为空")
    private String name;
    @NotNull(message = "tel不能为空")
    private String tel;
    private String userId;
    @NotEmpty(message = "商品列表为空")
    private List<ItemsDTO> items;

    public void validData(BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            for(ObjectError oe : bindingResult.getAllErrors()){
                sb.append(oe.getDefaultMessage()+"\n");
            }
            throw  new ValidException(sb.toString());
        }
    }
}

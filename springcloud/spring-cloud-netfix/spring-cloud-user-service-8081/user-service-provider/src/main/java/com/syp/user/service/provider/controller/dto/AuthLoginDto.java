package com.syp.user.service.provider.controller.dto;

import com.syp.springcloud.exception.ValidException;
import lombok.Data;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import javax.validation.constraints.NotNull;

/**
 * @Author: SYP
 * @Date: 2020/10/9
 * @Description:
 */
@Data
public class AuthLoginDto {
    private String username;
    private String password;
    private String phone;
    private String code;
    private String openId;

    @NotNull(message = "登录类型不能为空")
    private Integer loginType;

    public void validData(BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            for(ObjectError oe : bindingResult.getAllErrors()){
                sb.append(oe.getDefaultMessage()+"\n");
            }
            throw new ValidException(sb.toString());
        }
    }
}

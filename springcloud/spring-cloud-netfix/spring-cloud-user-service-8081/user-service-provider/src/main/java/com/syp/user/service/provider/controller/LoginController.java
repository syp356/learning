package com.syp.user.service.provider.controller;

import com.syp.springcloud.api.R;
import com.syp.springcloud.exception.BizException;
import com.syp.user.service.provider.biz.AbstractLogin;
import com.syp.user.service.provider.biz.Login;
import com.syp.user.service.provider.controller.dto.AuthLoginDto;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: SYP
 * @Date: 2020/10/9
 * @Description:
 */
@RestController
public class LoginController {

    @PostMapping("/login")
    public R loginAuth(@RequestBody @Validated AuthLoginDto authLoginDto, BindingResult bindingResult){
        authLoginDto.validData(bindingResult);
        Login login = AbstractLogin.loginMap.get(authLoginDto.getLoginType());
        if(login == null){
            throw new BizException("暂不支持该种登录类型");
        }
        return login.doLogin(authLoginDto);
    }
}

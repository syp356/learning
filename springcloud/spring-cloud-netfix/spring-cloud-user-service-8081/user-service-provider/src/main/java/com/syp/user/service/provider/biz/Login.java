package com.syp.user.service.provider.biz;

import com.syp.springcloud.api.R;
import com.syp.user.service.provider.controller.dto.AuthLoginDto;

/**
 * @Author: SYP
 * @Date: 2020/10/9-21-16
 * @Description:
 */
public interface Login {
    R doLogin(AuthLoginDto authLoginDto);
}

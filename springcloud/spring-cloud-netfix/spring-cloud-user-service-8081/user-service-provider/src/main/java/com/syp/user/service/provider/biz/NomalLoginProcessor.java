package com.syp.user.service.provider.biz;

import com.syp.springcloud.exception.ValidException;
import com.syp.user.service.provider.controller.dto.AuthLoginDto;
import com.syp.user.service.provider.controller.enums.LoginTypeEnum;
import com.syp.user.service.provider.mapper.entitys.TbMember;
import org.apache.commons.lang.StringUtils;

/**
 * @Author: SYP
 * @Date: 2020/10/9
 * @Description:
 */
public class NomalLoginProcessor extends AbstractLogin {
    @Override
    public int getLoginType() {
        return LoginTypeEnum.NORMAL.getCode();
    }

    @Override
    public void validate(AuthLoginDto authLoginDto) {
        if(StringUtils.isBlank(authLoginDto.getUsername()) || StringUtils.isBlank(authLoginDto.getPassword())){
            throw new ValidException("账号和密码不能为空");
        }
    }

    @Override
    public TbMember doProcesser(AuthLoginDto authLoginDto) {
        TbMember tbMember = new TbMember();
        tbMember.setId(1L);
        tbMember.setPhone("18261433545");
        tbMember.setPassword("18261433545");
        return tbMember;
    }
}

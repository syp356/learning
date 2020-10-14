package com.syp.user.service.provider.biz;

import com.syp.springcloud.api.R;
import com.syp.user.service.provider.controller.dto.AuthLoginDto;
import com.syp.user.service.provider.mapper.entitys.TbMember;
import com.syp.user.service.provider.utils.JwtGeneratorUtil;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: SYP
 * @Date: 2020/10/9
 * @Description:
 */
@Slf4j
public abstract class AbstractLogin implements  Login{

    public static ConcurrentHashMap<Integer,AbstractLogin> loginMap = new ConcurrentHashMap<>();

    public void init(){
        loginMap.put(getLoginType(),this);
    }

    @Override
    public R doLogin(AuthLoginDto authLoginDto) {
        log.info("begin doLogin: "+authLoginDto);
        validate(authLoginDto);
        TbMember member = doProcesser(authLoginDto);
        Map<String,Object> payload = new HashMap<>();
        payload.put("uid",member.getId());
        payload.put("exp", DateTime.now().plusHours(1).toDate().getTime()/1000);
        String token = JwtGeneratorUtil.generatorToken(payload);
        return new R.Builder().setData(token).buildOk();
    }

    public abstract int getLoginType();

    public abstract void validate(AuthLoginDto authLoginDto);

    public abstract TbMember doProcesser(AuthLoginDto authLoginDto);
}

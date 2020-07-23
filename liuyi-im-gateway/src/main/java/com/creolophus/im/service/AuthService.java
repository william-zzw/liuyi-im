package com.creolophus.im.service;

import com.creolophus.im.common.base.BaseService;
import com.creolophus.liuyi.common.exception.UnauthorizedException;
import com.creolophus.im.common.security.UserSecurity;
import com.creolophus.im.feign.BackendFeign;
import com.creolophus.im.netty.protocol.Auth;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author magicnana
 * @date 2020/1/11 下午5:14
 */
@Service
public class AuthService extends BaseService {

    @Resource
    private BackendFeign backendFeign;

    public Auth verify(String token) {

        UserSecurity us = backendFeign.verifyToken(token);
        if(us == null) {
            throw new UnauthorizedException("无法取得 token 信息");
        }
        Auth auth = new Auth();
        auth.setAppKey(us.getAppKey());
        auth.setUserId(us.getUserId());
        return auth;
    }
}
/*
 * Copyright 2019 T-Doer (tdoer.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.tdoer.gateway.controller;

import com.tdoer.bedrock.Platform;
import com.tdoer.bedrock.tenant.BaseUser;
import com.tdoer.security.oauth2.client.OAuth2LoginHandler;
import com.tdoer.security.oauth2.client.OAuth2LogoutHandler;
import com.tdoer.springboot.rest.GenericResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Web SSO Login/Logout request will be intercepted and processed by
 * {@link com.tdoer.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter}
 * and {@link org.springframework.security.web.authentication.logout.LogoutFilter}
 * which are introduced in by {@link com.tdoer.security.configure.EnableGatewayService}.
 * <br>
 * The class processes "/api/login" and "/api/logout" requests etc.
 *
 * @author Htinker Hu (htinker@163.com)
 * @create 2019-11-16
 */
@Api("用户登录登出API")
@RestController
@RequestMapping("/api/")
@Slf4j
public class UserController {
    /**
     * Introduced by {@link com.tdoer.security.configure.EnableGatewayService}.
     */
    @Autowired
    private OAuth2LoginHandler loginHandler;

    /**
     * Introduced by {@link com.tdoer.security.configure.EnableGatewayService}.
     */
    @Autowired
    private OAuth2LogoutHandler logoutHandler;

    @ApiOperation(value = "用户登录，请求需要带‘account’和‘password’参数。" +
            "如果登录成功，将在头部和Cookie中带auth-token令牌字符串回去，并且" +
            "返回用户对象")
    @PostMapping(path="/login")
    public GenericResponseData<BaseUser> login(HttpServletResponse response, HttpServletRequest request){
        log.info("Login request from: {}", request.getRequestURL());

        String account = request.getParameter("username");
        String password = request.getParameter("password");
        Assert.hasText(account, "'username' parameter cannot be blank");
        Assert.hasText(account, "'password' parameter cannot be blank");

        loginHandler.login(request, response, account, password);

        BaseUser user = Platform.getCurrentUser();
        // don't transport password to outside
        user.setPassword(null);
        log.info("Login user: {}", user);

        return new GenericResponseData<>(user);
    }

    @ApiOperation(value = "用户登出。如果登出成功，在去除Cookie中auto-token令牌，" +
            "并请求认证服务撤销令牌。")
    @PostMapping(path="/logout")
    public GenericResponseData<BaseUser> logout(HttpServletResponse response, HttpServletRequest request, Authentication authentication){
        log.info("Logout request from: {}", request.getRequestURL());

        BaseUser user = Platform.getCurrentUser();
        if(user != null){
            // don't transport password to outside
            user.setPassword(null);
            log.info("User logs out: {}", user);
            logoutHandler.logout(request, response, authentication);
        }else{
            log.info("User not login yet, invalid log out request from: {}", request.getRequestURL());
        }

        return new GenericResponseData<>(200, "登出成功", null);
    }

}

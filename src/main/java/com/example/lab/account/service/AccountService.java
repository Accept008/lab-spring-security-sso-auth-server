package com.example.lab.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {


    @Autowired
    AuthenticationManager authenticationManager;


    public String login(String username, String password) {
//        //用户验证
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//        //存储认证信息
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        //生成token
//        SmSecurityUser userDetail = (SmSecurityUser) authentication.getPrincipal();
//        return JwtUtil.generateToken(userDetail);
        return null;
    }
}

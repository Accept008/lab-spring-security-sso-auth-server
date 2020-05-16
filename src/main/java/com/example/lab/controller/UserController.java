package com.example.lab.controller;

import java.security.Principal;

import com.example.lab.add.OauthContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @RequestMapping("/user/me")
    public Principal user(Principal principal) {
        System.out.println(principal);
        String customerId = OauthContextHolder.getCustomerId();
        String userId = OauthContextHolder.getUserId();
        return principal;
    }
}

package com.example.lab.add;

import org.springframework.stereotype.Component;

@Component
public class UserService {

    /**
     * 根据用户邮箱或者手机号码获取用户信息，取第一个
     * @param value
     * @return
     */
    public User getUserByEmailOrMobile(String value) {
        User user = new User();
        user.setUsername(value);
        user.setId("141304105");
        user.setPassword("123456");
        return user;
    }


}

package com.example.lab.account.provider;

import com.alibaba.fastjson.JSON;
import com.example.lab.account.domain.User;
import com.example.lab.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

/**
 * @Classname SmAuthenticationProvider
 * @Description 声动平台项目登录重写验证
 * @Author Jemo
 * @Date 2020-04-28
 */
@Component
public class LoginAuthenticationProvider extends DaoAuthenticationProvider {

//    @Autowired
//    UserService userService;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    public LoginAuthenticationProvider(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService) {
        super();
        // 这个地方一定要对userDetailsService赋值，不然userDetailsService是null (这个坑有点深)
        setUserDetailsService(userDetailsService);
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                  UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

        String username = authentication.getName();
        String presentedPassword = (String)authentication.getCredentials();

        // 根据用户名获取用户信息
//        User user = this.userService.getUserByEmailOrMobile(username);
//        if (user == null) {
//            throw new BadCredentialsException("用户名不存在");
//        } else {
//            if (authentication.getCredentials() == null) {
//                throw new BadCredentialsException("登录名或密码错误");
//            } else if (!userService.passwordMatch(presentedPassword, user.getPassword(), user.getSalt())) {
//                throw new BadCredentialsException("登录密码错误");
//            }
//        }
        String userStr = "{\"emails\":[\"jemo@soundbus.cn\"],\"id\":\"59ed5323dd7c4a51b7036fbd\",\"initializedUUIDPassword\":false,\"mobiles\":[],\"nickName\":\"user_10000000221\",\"password\":\"dr0+D7uoGyiKna03wjFRMfe8eWe3IyKllZ7LU9+EUnXZZg3cMxW6UrQ+XvOzGsTo6KMLDJ/esu0I2/4Plmjl/w==\",\"salt\":\"N}BSSMMjH,AbM0oQ\",\"systemId\":\"open_platform\",\"thirdAccounts\":{},\"thirdIds\":{},\"username\":\"10000000221\",\"version\":52}";
        User user = JSON.parseObject(userStr, User.class);
        System.out.println("【1】user -> "+JSON.toJSONString(user));
    }

}

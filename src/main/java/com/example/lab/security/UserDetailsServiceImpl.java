package com.example.lab.security;

import com.alibaba.fastjson.JSON;
import com.example.lab.account.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @Classname UserDetailsServiceImpl
 * @Description 用户身份验证
 * @Author Jemo
 * @Date 2020-04-28
 * @Version 1.0
 */
@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

//    @Autowired
//    private UserService userService;

    /**
     * 用户名密码登录
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//        User user = userService.getUserByEmailOrMobile(username);
//        if (ObjectUtil.isNull(user)) {
//            log.info("登录用户：" + username + " 不存在.");
//            throw new UsernameNotFoundException("登录用户：" + username + " 不存在");
//        }
        String userStr = "{\"emails\":[\"jemo@soundbus.cn\"],\"id\":\"59ed5323dd7c4a51b7036fbd\",\"initializedUUIDPassword\":false,\"mobiles\":[],\"nickName\":\"user_10000000221\",\"password\":\"dr0+D7uoGyiKna03wjFRMfe8eWe3IyKllZ7LU9+EUnXZZg3cMxW6UrQ+XvOzGsTo6KMLDJ/esu0I2/4Plmjl/w==\",\"salt\":\"N}BSSMMjH,AbM0oQ\",\"systemId\":\"open_platform\",\"thirdAccounts\":{},\"thirdIds\":{},\"username\":\"10000000221\",\"version\":52}";
        User user = JSON.parseObject(userStr, User.class);
        System.out.println("【1】user -> "+JSON.toJSONString(user));
        return new SmSecurityUser(user.getId(), username, user.getPassword(), LoginType.normal);
    }

}

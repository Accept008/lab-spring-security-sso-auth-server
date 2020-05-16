package com.example.lab.add;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Description 用户身份验证
 */
@Slf4j
@Service
//接管
public class UserDetailsServiceImpl{
//public class UserDetailsServiceImpl implements UserDetailsService {

//    @Autowired
//    private UserService userService;
//
//    /**
//     * 用户名密码登录
//     *
//     * @param username
//     * @return
//     * @throws UsernameNotFoundException
//     */
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        User user = userService.getUserByEmailOrMobile(username);
//        if (ObjectUtil.isNull(user)) {
//            log.info("登录用户：" + username + " 不存在.");
//            throw new UsernameNotFoundException("登录用户：" + username + " 不存在");
//        }
////        user.getRoles();
//        System.out.println("db user -> "+ JSON.toJSONString(user));
//        List<String> roles = new ArrayList<>();
//        roles.add("USER");
//        Collection<? extends GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("role_admin");
//        return new SmSecurityUser(user.getId(), username, user.getPassword(), authorities, LoginType.normal);
//    }

}

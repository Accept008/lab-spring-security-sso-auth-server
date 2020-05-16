package com.example.lab.config;

import com.example.lab.AccessTokenThreadFilter;
import com.example.lab.add.UserDetailsServiceImpl;
import com.example.lab.add.provider.LoginAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@Order(2)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //【接管A-2】
    @Autowired
    LoginAuthenticationProvider loginAuthenticationProvider;

    @Autowired
    private AccessTokenThreadFilter accessTokenThreadFilter;

    // 【接管A-3】
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception { // @formatter:off
        http.addFilterBefore(accessTokenThreadFilter, UsernamePasswordAuthenticationFilter.class);

        http.requestMatchers()
            .antMatchers("/login", "/oauth/authorize")
            .and()
            .authorizeRequests()
            .anyRequest()
            .authenticated()
            .and()
            .formLogin()
            .permitAll()
            .and().csrf().disable();
    } // @formatter:on

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception { // @formatter:off
//        auth.inMemoryAuthentication()
//            .withUser("john")
//            .password(passwordEncoder().encode("123"))
//            .roles("USER");

        // 【接管A-1】登录账号配置读取数据库
        auth.authenticationProvider(loginAuthenticationProvider)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
        ;
    } // @formatter:on
//
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}

package com.example.lab.config;

import com.example.lab.account.provider.LoginAuthenticationProvider;
import com.example.lab.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private LoginAuthenticationProvider loginAuthenticationProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception { // @formatter:off

//        http.requestMatchers()
//            .antMatchers("/login", "/oauth/authorize")
//            .and()
//            .authorizeRequests()
//            .anyRequest()
//            .authenticated()
//            .and()
//            .formLogin()
//            .permitAll()
//            .and().csrf().disable();

        http
                .authorizeRequests()
                .antMatchers( "/").anonymous()
                .antMatchers( "/hello").anonymous()
                .antMatchers( "/login").anonymous()
                .antMatchers( "/login1").anonymous()
                .antMatchers(HttpMethod.GET, "/*.html", "/**/*.html", "/**/*.css", "/**/*.js","/**/*.png")
                .permitAll()
                .antMatchers("/login", "/oauth/authorize")
                .permitAll()
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                //指定登录页的路径
                .loginPage("/")
                //指定自定义form表单请求的路径
                .loginProcessingUrl("/authentication/form")
//                .failureUrl("/login?error")
//                .defaultSuccessUrl("/success")
                //必须允许所有用户访问我们的登录页（例如未验证的用户，否则验证流程就会进入死循环）
                //这个formLogin().permitAll()方法允许所有用户基于表单登录访问/login这个page。
                .permitAll()
                .and().csrf().disable();

    } // @formatter:on

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception { // @formatter:off
//        auth.inMemoryAuthentication()
//            .withUser("john")
//            .password(passwordEncoder().encode("123"))
//            .roles("USER");
        auth.authenticationProvider(loginAuthenticationProvider).userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    } // @formatter:on

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}

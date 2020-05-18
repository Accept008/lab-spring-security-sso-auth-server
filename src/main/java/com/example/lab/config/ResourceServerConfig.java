package com.example.lab.config;

import com.example.lab.AccessTokenThreadFilter;
import com.example.lab.OAuthWebResponseExceptionTranslator;
import com.example.lab.add.SmSecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.HashMap;
import java.util.Map;

/**
 * 资源服务器
 */
@Configuration
@EnableResourceServer
@Order(1)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private AccessTokenThreadFilter accessTokenThreadFilter;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers( "/oauth/**","/debug","/actuator/**","/favicon.ico").anonymous()
            .antMatchers("/api/**").anonymous()
            .antMatchers(HttpMethod.GET, "/*.icon","/*.html", "/**/*.html", "/**/*.css", "/**/*.js","/**/*.png","/**/*.jpg")
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .csrf().disable();

        // 添加JWT filter 用户名登录, 添加JWT验证过滤器
        http.addFilterBefore(accessTokenThreadFilter, UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        // 定义异常转换类生效
        AuthenticationEntryPoint authenticationEntryPoint = new OAuth2AuthenticationEntryPoint();
        ((OAuth2AuthenticationEntryPoint) authenticationEntryPoint).setExceptionTranslator(new OAuthWebResponseExceptionTranslator());
        resources.authenticationEntryPoint(authenticationEntryPoint);
    }




}

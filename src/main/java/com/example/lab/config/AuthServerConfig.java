package com.example.lab.config;

import com.alibaba.fastjson.JSON;
import com.example.lab.OAuthWebResponseExceptionTranslator;
import com.example.lab.add.SmSecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.HashMap;
import java.util.Map;


@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {
    
    @Autowired    
    private BCryptPasswordEncoder passwordEncoder;
    /**
     * 注入AuthenticationManager ，密码模式用到
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * oauth 自定义异常转换器
     */
    @Autowired
    OAuthWebResponseExceptionTranslator oauthWebResponseExceptionTranslator;
    @Bean
    public OAuthWebResponseExceptionTranslator getOAuthWebResponseExceptionTranslator(){
        return new OAuthWebResponseExceptionTranslator();
    }

    /**
     * 对Jwt签名时，增加一个密钥
     * JwtAccessTokenConverter：对Jwt来进行编码以及解码的类
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
//        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//        converter.setSigningKey("test-secret");
//        return converter;

        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter() {
            /**
             * 重写增强token的方法
             * 自定义返回相应的信息
             *
             */

            @Override
            public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

                String userName = authentication.getUserAuthentication().getName();
                // 与登录时候放进去的UserDetail实现类一直查看link{SecurityConfiguration}
                SmSecurityUser user = (SmSecurityUser) authentication.getUserAuthentication().getPrincipal();
                System.out.println("user -> "+ JSON.toJSONString(user));
                /** 自定义一些token属性 ***/
                final Map<String, Object> additionalInformation = new HashMap<>();
                additionalInformation.put("userId", user.getUserId());
                //additionalInformation.put("authorities", user.getAuthorities());

//                List<String> authorities = new ArrayList<>();
//                authorities.add("USER");
//                additionalInformation.put("authorities", authorities);
                ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
                OAuth2AccessToken enhancedToken = super.enhance(accessToken, authentication);
                return enhancedToken;
            }

        };
        // 测试用,资源服务使用相同的字符达到一个对称加密的效果,生产时候使用RSA非对称加密方式
        accessTokenConverter.setSigningKey("test-secret");
        return accessTokenConverter;
    }

    /**
     * 设置token 由Jwt产生，不使用默认的透明令牌
     */
    @Bean
    public JwtTokenStore jwtTokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)
                .tokenStore(jwtTokenStore())
                .accessTokenConverter(accessTokenConverter());

        endpoints.exceptionTranslator(oauthWebResponseExceptionTranslator);
    }

    @Override
    public void configure(final AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.tokenKeyAccess("permitAll()")
            .checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
            .withClient("SampleClientId")
            .secret(passwordEncoder.encode("secret"))
            //.authorizedGrantTypes("authorization_code")
                //设置支持[密码模式、授权码模式、token刷新]
            .authorizedGrantTypes(
                    "password",
                    "authorization_code",
                    "refresh_token")
            .scopes("user_info")
            .autoApprove(true)
            .redirectUris("http://localhost:8082/ui/login","http://localhost:8083/ui2/login","http://localhost:8082/login","http://www.example.com/")
        // .accessTokenValiditySeconds(3600)
        ; // 1 hour
    }


}

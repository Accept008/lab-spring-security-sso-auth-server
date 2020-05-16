/*
 * Project Name: SoundBus-Open-Platform
 * Package Name: cn.soundbus.platform.oauth2.utils
 * Copyright: Copyright(C) 2015-2016 SoundBus Technologies, Co., LTD. All rights reserved.
 */
package com.example.lab;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * A delegate to {@link ResourceServerTokenServices}
 *
 * @author <a href="mailto:jacky.wu@soundnet.com.cn">Jacky Wu</a>
 * @since 16/6/13 上午9:25
 */
@Component
public class TokenUtils {

    @Autowired
    private JwtTokenStore resourceServerTokenServices;

    public final Set<String> getScope(String accessToken) {
        return getAccessToken(accessToken).getScope();
    }

    public Map<String, Object> getAdditionalInformation(String accessToken) {
        return getAccessToken(accessToken).getAdditionalInformation();
    }

    public boolean isExpired(String accessToken) {
        return getAccessToken(accessToken).isExpired();
    }

    public Date getExpiration(String accessToken) {
        return getAccessToken(accessToken).getExpiration();
    }

    public int getExpiresIn(String accessToken) {
        return getAccessToken(accessToken).getExpiresIn();
    }

    public OAuth2AccessToken getAccessToken(String accessToken) {
        return resourceServerTokenServices.readAccessToken(accessToken);
    }

    public Authentication getUserAuthentication(String accessToken) {
        return getOAuth2Authentication(accessToken).getUserAuthentication();
    }

    public <T> T getPrincipal(String accessToken) {
        return (T) getOAuth2Authentication(accessToken).getPrincipal();
    }

    public boolean isClientOnly(String accessToken) {
        return getOAuth2Authentication(accessToken).isClientOnly();
    }

    public boolean isAuthenticated(String accessToken) {
        return getOAuth2Authentication(accessToken).isAuthenticated();
    }

    public OAuth2Authentication getOAuth2Authentication(String accessToken) {
        return resourceServerTokenServices.readAuthentication(accessToken);
    }
}

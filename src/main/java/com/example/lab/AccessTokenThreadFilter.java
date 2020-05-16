package com.example.lab;

import com.example.lab.add.OauthContextHolder;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * access token 处理filter
 * <p>
 * 获取access token 并放入threadLocal中 <br>
 * 解析出userId放入threadLocal中
 *
 * @author Miles.wang
 * @since 2016/05/24 13：37
 */
@Component
public class AccessTokenThreadFilter extends OncePerRequestFilter {
    public final static String AUTHORIZATION = "Authorization";
    public final static String ACCESS_TOKEN = "access_token";
    public final static String BEARER = "Bearer";
    public final static String ADDITIONAL_INFO_SYSTEM_ID_KEY = "system_id";

    @Autowired
    TokenUtils tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        HttpServletResponse responseToUse = response;
        try {
            String token = getAccessTokenFromHttpRequest(request);
            // 设置ip
            String ip = getIpAddr(request);
            OauthContextHolder.setClientIp(ip);
            if (token != null) {
                OauthContextHolder.setAccessToken(token);

                OAuth2Authentication o = tokenService.getOAuth2Authentication(token);
                String clientId = o.getOAuth2Request().getClientId();

                Object principal = o.getPrincipal();
                //String userId = getUserIdFromPrincipal(principal);

                OauthContextHolder.setClientId(clientId);

                Map<String, Object> additionalInformation =
                        tokenService.getAdditionalInformation(token);

                String userId = "";
                if(additionalInformation.containsKey("userId")){
                    userId = (String)additionalInformation.get("userId");
                }

                String customerId = "";
                if(additionalInformation.containsKey("customerId")){
                    customerId = (String)additionalInformation.get("customerId");
                }



                if (MapUtils.isNotEmpty(additionalInformation)) {
                    OauthContextHolder.setAdditionalInfo(additionalInformation);
                    Object systemId = additionalInformation.get(ADDITIONAL_INFO_SYSTEM_ID_KEY);
                    if (null != systemId && String.class.isAssignableFrom(systemId.getClass())) {
                        OauthContextHolder.setSystemId((String) systemId);
                    }
                }

                // client only不将userId放入threadlocal中
                if (!tokenService.isClientOnly(token) && StringUtils.isNotBlank(userId)) {
                    OauthContextHolder.setUserId(userId);
                }
                if (!tokenService.isClientOnly(token) && StringUtils.isNotBlank(customerId)) {
                    OauthContextHolder.setCustomerId(customerId);
                }
            }
            filterChain.doFilter(request, responseToUse);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            OauthContextHolder.remove();
        }
    }

    private String getUserIdFromPrincipal(Object principal)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (String.class.isAssignableFrom(principal.getClass())) {
            return (String) principal;
        } else {
            // in case principal is a CasUser instance
            Method getUserIdMethod = principal.getClass().getDeclaredMethod("getUserId");
            return (String) getUserIdMethod.invoke(principal);
        }
    }


    private String getAccessTokenFromHttpRequest(HttpServletRequest request) {
        String token = request.getParameter(ACCESS_TOKEN);
        if (StringUtils.isNotBlank(token)) {
            return StringUtils.defaultString(token);
        }
        String authorization = request.getHeader(AUTHORIZATION);
        if (StringUtils.isNotBlank(authorization)) {
            if (StringUtils.startsWith(authorization, BEARER)) {
                return StringUtils.replace(authorization, BEARER, "").trim();
            }
        }
        return null;
    }

    /**
     * 获取访问者IP
     *
     * 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。
     *
     * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)， 如果还不存在则调用Request
     * .getRemoteAddr()。
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        } else {
            return request.getRemoteAddr();
        }
    }
}


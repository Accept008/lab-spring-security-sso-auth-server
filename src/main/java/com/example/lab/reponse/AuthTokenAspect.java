package com.example.lab.reponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AuthTokenAspect {

    /// @Around是可以改变controller返回值的
    @Around("execution(* org.springframework.security.oauth2.provider.endpoint.TokenEndpoint.postAccessToken(..))")
    public Object handleControllerMethod(ProceedingJoinPoint pjp) throws Throwable {
        // 放行
        OauthSuccessResponse resp = new OauthSuccessResponse();
        Object proceed = pjp.proceed();
        if (proceed != null) {
            ResponseEntity<OAuth2AccessToken> responseEntity = (ResponseEntity<OAuth2AccessToken>) proceed;
            OAuth2AccessToken body = responseEntity.getBody();
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                resp.setStatus(responseEntity.getStatusCode());
                resp.setPayload(body);
            } else {
                resp.setStatus(responseEntity.getStatusCode());
                resp.setMessage("获取授权码失败");
                System.out.println("responseEntity.getStatusCode() -> " + responseEntity.getStatusCode());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }
}

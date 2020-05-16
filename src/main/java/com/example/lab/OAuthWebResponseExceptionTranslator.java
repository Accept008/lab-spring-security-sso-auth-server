package com.example.lab;

import cn.soundbus.library.core.exception.authorization.UnauthorizedException;
import cn.soundbus.library.spring.web.api.response.RestErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;

/**
 * @Author: zym
 * @Date: 2020/5/13 14:14
 */
public class OAuthWebResponseExceptionTranslator implements WebResponseExceptionTranslator {

    // 自定义的全局异常处理处理优先级不如类级别的
    // 只需要实现WebResponseExceptionTranslator类的translate 方法即可
    // ExceptionTranslator 的配置只需要在AuthorizationServerEndpointsConfigurer endpoints 后添加：
    // endpoints.exceptionTranslator(oAuthWebResponseExceptionTranslator);
    public ResponseEntity translate(Exception e) throws Exception {
        RestErrorResponse resp;
        if (e instanceof UnauthorizedException) {
            UnauthorizedException ee = (UnauthorizedException) e;
            resp = new RestErrorResponse(HttpStatus.valueOf(ee.getHttpStatusCode()),
                    ee.getErrCode(), ee.getDevMsg(), ee.getDevMsg(),
                    "mailto:developer@sonicmoving.cn", null, "");
            return ResponseEntity.status(ee.getHttpStatusCode()).body(resp);
        }
        resp = new RestErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "E5000",
                "Internal Server Error", "Internal Server Error", "mailto:developer@sonicmoving.cn",
                null, "");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
    }
}

package com.example.lab.global.exception.module.account;

import com.example.lab.global.exception.BaseException;
import com.example.lab.global.exception.annotation.ExceptionResponseInfo;
import org.springframework.http.HttpStatus;

/**
 * @since 2020/6/2
 */
//@formatter:off
@ExceptionResponseInfo(
        status = HttpStatus.BAD_REQUEST,
        errCode = ThirdPartyAccountAlreadyBindException.CODE,
        errMsg = "当前第三方账号已经被绑定/使用"
)
//@formatter:on
public class ThirdPartyAccountAlreadyBindException extends BaseException {

    public static final String CODE = "E02005";

    public ThirdPartyAccountAlreadyBindException(String message) {
        super(CODE, message);
    }

    public ThirdPartyAccountAlreadyBindException(String message, Object... params) {
        super(CODE, message, params);
    }
}

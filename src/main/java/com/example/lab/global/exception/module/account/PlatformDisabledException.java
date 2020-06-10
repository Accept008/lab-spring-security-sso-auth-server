package com.example.lab.global.exception.module.account;

import com.example.lab.global.exception.BaseException;
import com.example.lab.global.exception.annotation.ExceptionResponseInfo;
import org.springframework.http.HttpStatus;

/**
 * @since 2020-06-02
 */
@ExceptionResponseInfo(
        status = HttpStatus.BAD_REQUEST,
        errCode = PlatformDisabledException.CODE,
        errMsg = "平台被禁用异常"
)
public class PlatformDisabledException extends BaseException {

    public static final String CODE = "E02003";

    public PlatformDisabledException(String message) {
        super(CODE, message);
    }

    public PlatformDisabledException(String message, Object... params) {
        super(CODE, message, params);
    }

}
package com.example.lab.global.exception.module.account;

import com.example.lab.global.exception.BaseException;
import com.example.lab.global.exception.annotation.ExceptionResponseInfo;
import org.springframework.http.HttpStatus;

/**
 * @since 2020-06-02
 */
@ExceptionResponseInfo(
        status = HttpStatus.BAD_REQUEST,
        errCode = PlatformExpiredException.CODE,
        errMsg = "平台授权过期异常"
)
public class PlatformExpiredException extends BaseException {

    public static final String CODE = "E02004";

    public PlatformExpiredException(String message) {
        super(CODE, message);
    }

    public PlatformExpiredException(String message, Object... params) {
        super(CODE, message, params);
    }

}
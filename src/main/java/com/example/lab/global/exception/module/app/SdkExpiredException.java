package com.example.lab.global.exception.module.app;

import com.example.lab.global.exception.BaseException;
import com.example.lab.global.exception.annotation.ExceptionResponseInfo;
import org.springframework.http.HttpStatus;

/**
 * @created 2020-06-01
 */
@ExceptionResponseInfo(
        status = HttpStatus.BAD_REQUEST,
        errCode = SdkExpiredException.CODE,
        errMsg = "sdk过期异常"
)
public class SdkExpiredException extends BaseException {

    public static final String CODE = "E03004";

    public SdkExpiredException(String message) {
        super(CODE, message);
    }

    public SdkExpiredException(String message, Object... params) {
        super(CODE, message, params);
    }

}


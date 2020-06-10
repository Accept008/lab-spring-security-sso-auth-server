package com.example.lab.global.exception.module.sonic;

import com.example.lab.global.exception.BaseException;
import com.example.lab.global.exception.annotation.ExceptionResponseInfo;

/**
 * @created 2020-06-01
 */
@ExceptionResponseInfo(
        errCode = SonicExpiredException.CODE,
        errMsg = "声连码授权过期异常"
)
public class SonicExpiredException extends BaseException {

    public static final String CODE = "E04017";

    public SonicExpiredException(String message) {
        super(CODE, message);
    }

    public SonicExpiredException(String message, Object... params) {
        super(CODE, message, params);
    }

}

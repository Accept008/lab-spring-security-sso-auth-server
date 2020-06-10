package com.example.lab.global.exception.module.push;

import com.example.lab.global.exception.BaseException;
import com.example.lab.global.exception.annotation.ExceptionResponseInfo;

/**
 * @created 2020-06-01
 */
@ExceptionResponseInfo(
        errCode = PushDisabledException.CODE,
        errMsg = "不可推送异常"
)
public class PushDisabledException extends BaseException {

    public static final String CODE = "E04004";

    public PushDisabledException(String message) {
        super(CODE, message);
    }

    public PushDisabledException(String message, Object... params) {
        super(CODE, message, params);
    }

}
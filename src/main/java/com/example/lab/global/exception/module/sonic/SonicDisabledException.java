package com.example.lab.global.exception.module.sonic;

import com.example.lab.global.exception.BaseException;
import com.example.lab.global.exception.annotation.ExceptionResponseInfo;

/**
 * @created 2020-06-01
 */
@ExceptionResponseInfo(
        errCode = SonicDisabledException.CODE,
        errMsg = "声连码不可用异常"
)
public class SonicDisabledException extends BaseException {

    public static final String CODE = "E04015";

    public SonicDisabledException(String message) {
        super(CODE, message);
    }

    public SonicDisabledException(String message, Object... params) {
        super(CODE, message, params);
    }

}


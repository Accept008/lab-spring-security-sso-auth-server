package com.example.lab.global.exception.module.sonic;

import com.example.lab.global.exception.BaseException;
import com.example.lab.global.exception.annotation.ExceptionResponseInfo;

@ExceptionResponseInfo(
        errCode = SonicCodeNameMatcherException.CODE,
        errMsg = "声连码名字长度异常"
)
public class SonicCodeNameMatcherException extends BaseException {

    public static final String CODE = "E04014";

    public SonicCodeNameMatcherException(String message) {
        super(CODE, message);
    }

    public SonicCodeNameMatcherException(String message, Object... params) {
        super(CODE, message, params);
    }

}

package com.example.lab.global.exception.module.sonic;

import com.example.lab.global.exception.BaseException;
import com.example.lab.global.exception.annotation.ExceptionResponseInfo;

/**
 * @created 2020-06-01
 */
@ExceptionResponseInfo(
        errCode = SonicAmountLimitException.CODE,
        errMsg = "声连码超出限制异常"
)
public class SonicAmountLimitException extends BaseException {

    public static final String CODE = "E04013";

    public SonicAmountLimitException(String message) {
        super(CODE, message);
    }

    public SonicAmountLimitException(String message, Object... params) {
        super(CODE, message, params);
    }

}

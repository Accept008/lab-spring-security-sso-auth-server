package com.example.lab.global.exception.module.sys;

/**
 * Project:sop-core-exception
 */

import com.example.lab.global.exception.BaseException;
import com.example.lab.global.exception.annotation.ExceptionResponseInfo;
import org.springframework.http.HttpStatus;

/**
 * @created 2020-06-01
 */
@ExceptionResponseInfo(
        status = HttpStatus.BAD_REQUEST,
        errCode = ValueRequiredException.CODE,
        errMsg = "参数值为空异常"
)
public class ValueRequiredException extends BaseException {

    public static final String CODE = "E0004";

    public ValueRequiredException(String message) {
        super(CODE, message);
    }

    public ValueRequiredException(String message, Object... params) {
        super(CODE, message, params);
    }

}


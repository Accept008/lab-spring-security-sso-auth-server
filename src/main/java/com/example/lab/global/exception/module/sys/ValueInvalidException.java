package com.example.lab.global.exception.module.sys;

import com.example.lab.global.exception.BaseException;
import com.example.lab.global.exception.annotation.ExceptionResponseInfo;
import org.springframework.http.HttpStatus;

/**
 * @created 2020-06-01
 */
@ExceptionResponseInfo(
        status = HttpStatus.BAD_REQUEST,
        errCode = ValueInvalidException.CODE,
        errMsg = "值无效异常"
)
public class ValueInvalidException extends BaseException {

    public static final String CODE = "E0003";

    public ValueInvalidException(String message) {
        super(CODE, message);
    }

    public ValueInvalidException(String message, Object... params) {
        super(CODE, message, params);
    }

}


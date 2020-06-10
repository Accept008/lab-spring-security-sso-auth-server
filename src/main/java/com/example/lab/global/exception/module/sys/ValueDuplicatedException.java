package com.example.lab.global.exception.module.sys;

import com.example.lab.global.exception.BaseException;
import com.example.lab.global.exception.annotation.ExceptionResponseInfo;
import org.springframework.http.HttpStatus;

/**
 * @created 2020-06-01
 */
@ExceptionResponseInfo(
        status = HttpStatus.BAD_REQUEST,
        errCode = ValueDuplicatedException.CODE,
        errMsg = "出现重复异常"
)
public class ValueDuplicatedException extends BaseException {

    public static final String CODE = "E0002";

    public ValueDuplicatedException(String message) {
        super(CODE, message);
    }

    public ValueDuplicatedException(String message, Object... params) {
        super(CODE, message, params);
    }

}
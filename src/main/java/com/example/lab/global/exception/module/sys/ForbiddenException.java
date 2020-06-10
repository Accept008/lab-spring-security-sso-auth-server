package com.example.lab.global.exception.module.sys;

import com.example.lab.global.exception.BaseException;
import com.example.lab.global.exception.annotation.ExceptionResponseInfo;
import org.springframework.http.HttpStatus;

/**
 * @since 2020/06/01
 */
// formatter:off
@ExceptionResponseInfo(
        status = HttpStatus.FORBIDDEN,
        errCode = ForbiddenException.CODE,
        errMsg = "不可访问异常"
)
// formatter:on
public class ForbiddenException extends BaseException {

    public static final String CODE = "E0005";

    public ForbiddenException(String message) {
        super(CODE, message);
    }

    public ForbiddenException(String message, Object... params) {
        super(CODE, message, params);
    }
}

/**
 * Project:sop-core-exception
 */
package com.example.lab.global.exception.module.app;

import com.example.lab.global.exception.BaseException;
import com.example.lab.global.exception.annotation.ExceptionResponseInfo;
import org.springframework.http.HttpStatus;

/**
 * @created 2020-06-01
 */
@ExceptionResponseInfo(
        status = HttpStatus.BAD_REQUEST,
        errCode = ApplicationLimitException.CODE,
        errMsg = "应用限制异常"
)
public class ApplicationLimitException extends BaseException {

    public static final String CODE = "E03001";

    public ApplicationLimitException(String message) {
        super(CODE, message);
    }

    public ApplicationLimitException(String message, Object... params) {
        super(CODE, message, params);
    }

}

/**
 * Project:sop-core-exception
 */
package com.example.lab.global.exception.module.sys;

import com.example.lab.global.exception.BaseException;
import com.example.lab.global.exception.annotation.ExceptionResponseInfo;
import org.springframework.http.HttpStatus;

/**
 * @created 2020-06-01
 */
//@formatter:off
//@formatter:on
@ExceptionResponseInfo(
        status = HttpStatus.BAD_REQUEST,
        errCode = NotFoundException.CODE,
        errMsg = "信息未找到异常"
)
public class NotFoundException extends BaseException {

    public static final String CODE = "E0001";

    public NotFoundException(String message) {
        super(CODE, message);
    }

    public NotFoundException(String message, Object... params) {
        super(CODE, message, params);
    }

}

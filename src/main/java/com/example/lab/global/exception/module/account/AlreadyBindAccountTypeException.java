package com.example.lab.global.exception.module.account;

import com.example.lab.global.exception.BaseException;
import com.example.lab.global.exception.annotation.ExceptionResponseInfo;
import org.springframework.http.HttpStatus;

/**
 * 已绑定指定类型的账号
 *
 * @since 2020/06/01
 */
//@formatter:off
@ExceptionResponseInfo(
        status = HttpStatus.BAD_REQUEST,
        errCode = AlreadyBindAccountTypeException.CODE,
        errMsg = "已经绑定账户类型异常"
)
//@formatter:on
public class AlreadyBindAccountTypeException extends BaseException {

    public static final String CODE = "E02002";

    public AlreadyBindAccountTypeException(String message) {
        super(CODE, message);
    }

    public AlreadyBindAccountTypeException(String message, Object... params) {
        super(CODE, message, params);
    }
}

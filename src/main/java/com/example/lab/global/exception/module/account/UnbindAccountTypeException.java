package com.example.lab.global.exception.module.account;

import com.example.lab.global.exception.BaseException;
import com.example.lab.global.exception.annotation.ExceptionResponseInfo;
import org.springframework.http.HttpStatus;

/**
 * 未绑定指定类型的账号
 *
 * @since 2020/6/2
 */
//@formatter:off
@ExceptionResponseInfo(
        status = HttpStatus.BAD_REQUEST,
        errCode = UnbindAccountTypeException.CODE,
        errMsg = "未绑定指定类型的账号"
)
//@formatter:on
public class UnbindAccountTypeException extends BaseException {

    public static final String CODE = "E02006";

    public UnbindAccountTypeException(String message) {
        super(CODE, message);
    }

    public UnbindAccountTypeException(String message, Object... params) {
        super(CODE, message, params);
    }
}
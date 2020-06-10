package com.example.lab.global.exception.module.account;

import com.example.lab.global.exception.BaseException;
import com.example.lab.global.exception.annotation.ExceptionResponseInfo;
import org.springframework.http.HttpStatus;

@ExceptionResponseInfo(
        status = HttpStatus.BAD_REQUEST,
        errCode = AccountBussinessException.CODE,
        errMsg = "账户业务异常"
)
//@formatter:on
public class AccountBussinessException extends BaseException {

    public static final String CODE = "E02001";

    public AccountBussinessException(String code, String msg) {
        super(CODE, msg);
    }

    public AccountBussinessException(String message, Object... params) {
        super(CODE, message, params);
    }
}

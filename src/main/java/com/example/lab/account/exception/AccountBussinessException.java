package com.example.lab.account.exception;

import cn.soundbus.library.core.exception.annotation.ExceptionResponseInfo;
import cn.soundbus.library.core.exception.general.GeneralException;
import org.springframework.http.HttpStatus;

//@formatter:off
@ExceptionResponseInfo(
    status = HttpStatus.BAD_REQUEST
)
//@formatter:on
public class AccountBussinessException extends GeneralException {

    public AccountBussinessException(String code, String msg) {
        super(code, msg);
    }

    public AccountBussinessException(String msg) {
        super(msg);
    }
}

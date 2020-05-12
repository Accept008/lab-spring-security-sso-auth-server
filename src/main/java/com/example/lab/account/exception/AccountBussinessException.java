package com.example.lab.account.exception;

import com.example.lab.base.exception.GeneralException;
import com.example.lab.base.exception.annotation.ExceptionResponseInfo;
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

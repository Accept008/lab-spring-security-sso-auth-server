package com.example.lab.global.exception.module.app;

import com.example.lab.global.exception.BaseException;
import com.example.lab.global.exception.annotation.ExceptionResponseInfo;
import org.springframework.http.HttpStatus;

@ExceptionResponseInfo(
        status = HttpStatus.INTERNAL_SERVER_ERROR,
        errCode = SdkVersionAlreadyExistsException.CODE,
        errMsg = "sdk版本已经存在异常"
)
public class SdkVersionAlreadyExistsException extends BaseException {
    public static final String CODE = "E03003";

    public SdkVersionAlreadyExistsException(String errorMessage) {
        super(CODE, errorMessage);
    }

    public SdkVersionAlreadyExistsException(String message, Object... params) {
        super(CODE, message, params);
    }
}

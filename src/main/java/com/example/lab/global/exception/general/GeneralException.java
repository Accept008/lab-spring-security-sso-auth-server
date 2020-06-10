package com.example.lab.global.exception.general;

import com.example.lab.global.exception.BaseException;
import com.example.lab.global.exception.annotation.ExceptionResponseInfo;
import org.springframework.http.HttpStatus;

/**
 * @since 2020/06/01
 */
@ExceptionResponseInfo(
        status = HttpStatus.INTERNAL_SERVER_ERROR,
        errCode = GeneralException.CODE,
        errMsg = "通用异常"
)
public class GeneralException extends BaseException {

    public static final String CODE = "E0000";

    public GeneralException(String message) {
        super(CODE, message);
    }

    public GeneralException(String message, Object... params) {
        super(CODE, message, params);
    }

    protected GeneralException(String message, String moreInfoUrl) {
        super(CODE, message, moreInfoUrl);
    }

    protected GeneralException(String errCode, String message, String devMsg, String moreInfoUrl) {
        super(errCode, message, devMsg, moreInfoUrl);
    }

    protected GeneralException(String errCode, String message, Throwable cause, String devMsg,
                               String moreInfoUrl) {
        super(errCode, message, cause, devMsg, moreInfoUrl);
    }

    protected GeneralException(String errCode, Throwable cause, String devMsg, String moreInfoUrl) {
        super(errCode, cause, devMsg, moreInfoUrl);
    }
}


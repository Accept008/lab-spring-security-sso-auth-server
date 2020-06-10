package com.example.lab.global.exception.module.push;

import com.example.lab.global.exception.BaseException;
import com.example.lab.global.exception.annotation.ExceptionResponseInfo;

/**
 * @created 2020-06-01
 */
@ExceptionResponseInfo(
        errCode = NoPushContentException.CODE,
        errMsg = "无推送内容异常"
)
public class NoPushContentException extends BaseException {

    public static final String CODE = "E04003";

    public NoPushContentException(String message) {
        super(CODE, message);
    }

    public NoPushContentException(String message, Object... params) {
        super(CODE, message, params);
    }

}
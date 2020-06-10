/**
 * Project:sop-core-exception
 */
package com.example.lab.global.exception.module.push;

import com.example.lab.global.exception.BaseException;
import com.example.lab.global.exception.annotation.ExceptionResponseInfo;

@ExceptionResponseInfo(
        errCode = PushScheduleDateInvalidException.CODE,
        errMsg = "排播日期无效异常"
)
public class PushScheduleDateInvalidException extends BaseException {

    public static final String CODE = "E04006";

    public PushScheduleDateInvalidException(String message) {
        super(CODE, message);
    }

    public PushScheduleDateInvalidException(String message, Object... params) {
        super(CODE, message, params);
    }
}

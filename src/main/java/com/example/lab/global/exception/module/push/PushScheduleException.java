/**
 * Project:sop-core-exception
 */
package com.example.lab.global.exception.module.push;

import com.example.lab.global.exception.BaseException;
import com.example.lab.global.exception.annotation.ExceptionResponseInfo;

@ExceptionResponseInfo(
        errCode = PushScheduleException.CODE,
        errMsg = "排播异常"
)
public class PushScheduleException extends BaseException {

    public static final String CODE = "E04007";

    public PushScheduleException(String message) {
        super(CODE, message);
    }

    public PushScheduleException(String message, Object... params) {
        super(CODE, message, params);
    }

}

/**
 * Project:sop-core-exception
 */
package com.example.lab.global.exception.module.push;

import com.example.lab.global.exception.BaseException;
import com.example.lab.global.exception.annotation.ExceptionResponseInfo;

@ExceptionResponseInfo(
        errCode = PushSonicNumLimitException.CODE,
        errMsg = "推送声码超出限制异常"
)
public class PushSonicNumLimitException extends BaseException {

    public static final String CODE = "E04010";

    public PushSonicNumLimitException(String message) {
        super(CODE, message);
    }

    public PushSonicNumLimitException(String message, Object... params) {
        super(CODE, message, params);
    }

}

/**
 * Project:sop-core-exception
 */
package com.example.lab.global.exception.module.push;

import com.example.lab.global.exception.BaseException;
import com.example.lab.global.exception.annotation.ExceptionResponseInfo;

@ExceptionResponseInfo(
        errCode = PushSonicInvalidException.CODE,
        errMsg = "推送声码无效异常"
)
public class PushSonicInvalidException extends BaseException {

    public static final String CODE = "E04009";

    public PushSonicInvalidException(String message) {
        super(CODE, message);
    }

    public PushSonicInvalidException(String message, Object... params) {
        super(CODE, message, params);
    }

}

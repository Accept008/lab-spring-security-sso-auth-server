/**
 * Project:sop-core-exception
 */
package com.example.lab.global.exception.module.push;

import com.example.lab.global.exception.BaseException;
import com.example.lab.global.exception.annotation.ExceptionResponseInfo;

@ExceptionResponseInfo(
        errCode = PushEditLockException.CODE,
        errMsg = "推送更新被锁定异常"
)
public class PushEditLockException extends BaseException {

    public static final String CODE = "E04005";

    public PushEditLockException(String message) {
        super(CODE, message);
    }

    public PushEditLockException(String message, Object... params) {
        super(CODE, message, params);
    }

}

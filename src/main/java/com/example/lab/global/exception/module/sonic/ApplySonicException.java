/**
 * Project:sop-core-exception
 */
package com.example.lab.global.exception.module.sonic;

import com.example.lab.global.exception.BaseException;
import com.example.lab.global.exception.annotation.ExceptionResponseInfo;

@ExceptionResponseInfo(
        errCode = ApplySonicException.CODE,
        errMsg = "申请声连码异常"
)
public class ApplySonicException extends BaseException {

    public static final String CODE = "E04011";

    public ApplySonicException(String message) {
        super(CODE, message);
    }

    public ApplySonicException(String message, Object... params) {
        super(CODE, message, params);
    }

}

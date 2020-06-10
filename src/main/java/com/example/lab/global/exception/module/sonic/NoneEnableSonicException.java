/**
 * Project:sop-core-exception
 */
package com.example.lab.global.exception.module.sonic;

import com.example.lab.global.exception.BaseException;
import com.example.lab.global.exception.annotation.ExceptionResponseInfo;

@ExceptionResponseInfo(
        errCode = NoneEnableSonicException.CODE,
        errMsg = "没有可用声连码异常"
)
public class NoneEnableSonicException extends BaseException {

    public static final String CODE = "E04012";

    public NoneEnableSonicException(String message) {
        super(CODE, message);
    }

    public NoneEnableSonicException(String message, Object... params) {
        super(CODE, message, params);
    }

}

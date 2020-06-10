/**
 * Project:sop-core-exception
 */
package com.example.lab.global.exception.module.sonic;

import com.example.lab.global.exception.BaseException;
import com.example.lab.global.exception.annotation.ExceptionResponseInfo;

@ExceptionResponseInfo(
        errCode = SonicInfoException.CODE,
        errMsg = "声连码信息异常"
)
public class SonicInfoException extends BaseException {

    public static final String CODE = "E04018";

    public SonicInfoException(String message) {
        super(CODE, message);
    }

    public SonicInfoException(String message, Object... params) {
        super(CODE, message, params);
    }

}

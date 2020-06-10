/**
 * Project:sop-core-exception
 */
package com.example.lab.global.exception.module.app;

import com.example.lab.global.exception.BaseException;
import com.example.lab.global.exception.annotation.ExceptionResponseInfo;
import org.springframework.http.HttpStatus;

/**
 * @created 2020-06-01
 */
@ExceptionResponseInfo(
        status = HttpStatus.BAD_REQUEST,
        errCode = DnsUpdateException.CODE,
        errMsg = "dns更新异常"
)
public class DnsUpdateException extends BaseException {

    public static final String CODE = "E03002";

    public DnsUpdateException(String message) {
        super(CODE, message);
    }

    public DnsUpdateException(String message, Object... params) {
        super(CODE, message, params);
    }

}

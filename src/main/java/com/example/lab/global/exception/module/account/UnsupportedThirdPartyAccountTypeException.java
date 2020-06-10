package com.example.lab.global.exception.module.account;

import com.example.lab.global.exception.BaseException;
import com.example.lab.global.exception.annotation.ExceptionResponseInfo;
import org.springframework.http.HttpStatus;

/**
 * 不支持的第三方账号类型
 *
 * @since 2020/06/02
 */
//@formatter:off
@ExceptionResponseInfo(
        status = HttpStatus.BAD_REQUEST,
        errCode = UnsupportedThirdPartyAccountTypeException.CODE,
        errMsg = "不支持的第三方账号类型"
)
//@formatter:on
public class UnsupportedThirdPartyAccountTypeException extends BaseException {

    public static final String CODE = "E02007";

    public UnsupportedThirdPartyAccountTypeException(String message) {
        super(CODE, message);
    }

    public UnsupportedThirdPartyAccountTypeException(String message, Object... params) {
        super(CODE, message, params);
    }
}

package com.example.lab.global.exception.module.sonic;

import com.example.lab.global.exception.BaseException;
import com.example.lab.global.exception.annotation.ExceptionResponseInfo;

@ExceptionResponseInfo(
        errCode = UnsupportEncodingException.CODE,
        errMsg = "不支持的编码异常"
)
public class UnsupportEncodingException extends BaseException {

    public static final String CODE = "E04019";

    public UnsupportEncodingException(String message) {
        super(CODE, message);
    }

    public UnsupportEncodingException(String message, Object... params) {
        super(CODE, message, params);
    }

}

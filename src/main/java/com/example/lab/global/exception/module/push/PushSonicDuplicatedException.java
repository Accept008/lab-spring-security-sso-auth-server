package com.example.lab.global.exception.module.push;

import com.example.lab.global.exception.BaseException;
import com.example.lab.global.exception.annotation.ExceptionResponseInfo;

/**
 * @created 2020-06-01
 */
@ExceptionResponseInfo(
        errCode = PushSonicDuplicatedException.CODE,
        errMsg = "推送声码重复异常")
public class PushSonicDuplicatedException extends BaseException {

    public static final String CODE = "E04008";

    public PushSonicDuplicatedException(String message) {
        super(CODE, message);
    }

    public PushSonicDuplicatedException(String message, Object... params) {
        super(CODE, message, params);
    }

}
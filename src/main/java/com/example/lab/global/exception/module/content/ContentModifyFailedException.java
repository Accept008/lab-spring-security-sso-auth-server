/**
 * Project:sop-core-exception
 */
package com.example.lab.global.exception.module.content;

import com.example.lab.global.exception.BaseException;
import com.example.lab.global.exception.annotation.ExceptionResponseInfo;

/**
 * @created 2020-06-01
 */
@ExceptionResponseInfo(
        errCode = ContentModifyFailedException.CODE,
        errMsg = "内容修改异常")
public class ContentModifyFailedException extends BaseException {

    public static final String CODE = "E04001";

    public ContentModifyFailedException(String message) {
        super(CODE, message);
    }

    public ContentModifyFailedException(String message, Object... params) {
        super(CODE, message, params);
    }

}

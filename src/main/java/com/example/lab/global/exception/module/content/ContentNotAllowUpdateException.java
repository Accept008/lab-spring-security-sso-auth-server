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
        errCode = ContentNotAllowUpdateException.CODE,
        errMsg = "内容不允许更新异常")
public class ContentNotAllowUpdateException extends BaseException {

    public static final String CODE = "E04002";

    public ContentNotAllowUpdateException(String message) {
        super(CODE, message);
    }

    public ContentNotAllowUpdateException(String message, Object... params) {
        super(CODE, message, params);
    }

}

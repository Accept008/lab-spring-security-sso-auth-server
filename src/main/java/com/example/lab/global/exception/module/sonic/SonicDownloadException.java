/**
 * Project:sop-core-exception
 */
package com.example.lab.global.exception.module.sonic;

import com.example.lab.global.exception.BaseException;
import com.example.lab.global.exception.annotation.ExceptionResponseInfo;

@ExceptionResponseInfo(
        errCode = SonicDownloadException.CODE,
        errMsg = "声连码下载异常"
)
public class SonicDownloadException extends BaseException {

    public static final String CODE = "E04016";

    public SonicDownloadException(String message) {
        super(CODE, message);
    }

    public SonicDownloadException(String message, Object... params) {
        super(CODE, message, params);
    }

}

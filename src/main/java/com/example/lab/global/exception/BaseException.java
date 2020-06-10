/*
 * Project Name: SoundBus Open Platform
 * Package Name: cn.soundbus.platform.core.exception
 * Copyright: Copyright(C) 2015-2017 SoundBus Technologies, Co., LTD. All rights reserved.
 */

package com.example.lab.global.exception;

import com.example.lab.global.exception.annotation.ExceptionResponseInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.core.NestedRuntimeException;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.text.MessageFormat;

/**
 * Base exception.
 *
 * @since 2020/06/02
 */
public abstract class BaseException extends NestedRuntimeException implements ExceptionDetail {

    private static final Logger log = LoggerFactory.getLogger(BaseException.class);

    private static final int DEFAULT_EXCEPTION_HTTP_STATUS_CODE = 500;

    /**
     * 响应的HTTP状态码； 为了兼容客户端某些工具无法获取HTTP状态码；
     */
    private int httpStatusCode = DEFAULT_EXCEPTION_HTTP_STATUS_CODE;

    /**
     * 错误码，可以用于获取此错误的相关信息；
     */
    private String errCode;

    /**
     * 一种清晰和原始的技术细节消息，用于开发者调试API；
     */
    private String devMsg;

    /**
     * 用于获取该错误更多信息的URL地址；
     */
    private String moreInfoUrl = null;

    /**
     * 是否显示获取该错误更多信息的URL地址；
     */
    private boolean disableMoreInfoUrl = false;

    protected static String formatMessage(String message, Object... params) {
        if (params == null) {
            return message;
        }
        String formatMessage;
        if (message == null) {
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < params.length; i++) {
                if (i > 0) {
                    buffer.append(",");
                }
                buffer.append("{}");
            }
            formatMessage = buffer.toString();
        } else {
            formatMessage = message.replaceAll("'", "''");
        }

        try {
            if (formatMessage.contains("{}")) {
                return MessageFormatter.arrayFormat(formatMessage, params).getMessage();
            } else {
                return MessageFormat.format(formatMessage, params);
            }
        } catch (Exception e) {
            log.error("failed to format exception message," + e.getMessage());
            StringBuffer buffer = new StringBuffer();
            buffer.append(message);
            for (int i = 0; i < params.length; i++) {
                buffer.append(",");
                buffer.append(params[i]);
            }
            return buffer.toString();
        }
    }

    protected BaseException(String errCode, String message) {
        this(errCode, message, null, message, "");
    }

    protected BaseException(String errCode, String message, Object... params) {
        this(errCode, formatMessage(message, params));
    }

    protected BaseException(String errCode, String message, String moreInfoUrl) {
        this(errCode, message, null, message, moreInfoUrl);
    }

    protected BaseException(String errCode, String message, String devMsg, String moreInfoUrl) {
        this(errCode, message, null, devMsg, moreInfoUrl);
    }

    protected BaseException(String errCode, String message, Throwable cause, String devMsg,
                            String moreInfoUrl) {
        super(message, cause);

        this.errCode = errCode;

        if (null == devMsg) {
            this.devMsg = message;
        } else {
            this.devMsg = devMsg;
        }

        if (null != moreInfoUrl) {
            this.moreInfoUrl = moreInfoUrl;
        }

        if (getHttpStatusCode() == DEFAULT_EXCEPTION_HTTP_STATUS_CODE) {
            ExceptionResponseInfo expInfo = AnnotatedElementUtils
                    .findMergedAnnotation(this.getClass(), ExceptionResponseInfo.class);
            if (expInfo != null && expInfo.status() != null) {
                setHttpStatusCode(expInfo.status().value());
            }
        }
    }

    protected BaseException(String errCode, Throwable cause, String devMsg, String moreInfoUrl) {
        this(errCode, cause.getMessage(), cause, devMsg, moreInfoUrl);
    }

    public String getDevMsg() {
        return devMsg;
    }

    /**
     * Note: Not allow set <code>null</code> to devMsg.
     *
     * @param devMsg Message for developers
     */
    public void setDevMsg(String devMsg) {
        if (null == devMsg) {
            return;
        }

        this.devMsg = devMsg;
    }

    public void setDevMsg(String message, String... params) {
        if (null != message) {
            setDevMsg(MessageFormat.format(message.replaceAll("'", "''"), params));
        } else {
            setDevMsg(message);
        }
    }

    public String getMoreInfoUrl() {
        return moreInfoUrl;
    }

    public void setMoreInfoUrl(String moreInfoUrl) {
        this.moreInfoUrl = moreInfoUrl;
    }

    public String getErrCode() {
        return errCode;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public boolean isDisableMoreInfoUrl() {
        return disableMoreInfoUrl;
    }

    public void setDisableMoreInfoUrl(boolean disableMoreInfoUrl) {
        this.disableMoreInfoUrl = disableMoreInfoUrl;
    }
}


/*
 * Project Name: SoundBus Open Platform
 * Package Name: cn.soundbus.platform.core.exception.annotation
 * Copyright: Copyright(C) 2015-2017 SoundBus Technologies, Co., LTD. All rights reserved.
 */
package com.example.lab.global.exception.annotation;

import com.example.lab.global.exception.general.GeneralException;
import org.springframework.core.annotation.AliasFor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.lang.annotation.*;

/**
 * @see ResponseStatus
 * @since 2020/5/29
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExceptionResponseInfo {

    /**
     * Alias for {@link #status}.
     */
    @AliasFor("status") HttpStatus value() default HttpStatus.INTERNAL_SERVER_ERROR;

    /**
     * The <em>status</em> code to use for the response.
     * <p>Default is {@link HttpStatus#INTERNAL_SERVER_ERROR}, which should
     * typically be changed to something more appropriate.
     * @since 4.2
     */
    @AliasFor("value")
    HttpStatus status() default HttpStatus.INTERNAL_SERVER_ERROR;

    /**
     * 错误码，可以用于获取此错误的相关信息；
     */
    String errCode() default GeneralException.CODE;

    /**
     * The <em>errMsg</em> to be used for the error message.
     */
    String errMsg() default "";

    /**
     * 一种清晰和原始的技术细节消息，用于开发者调试API；
     */
    String devMsg() default "";

    /**
     * 用于获取该错误更多信息的URL地址；
     */
    String moreInfoUrl() default "mailto:support@soundbus.cn";

}

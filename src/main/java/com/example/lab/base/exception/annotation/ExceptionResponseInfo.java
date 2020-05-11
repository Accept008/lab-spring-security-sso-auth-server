package com.example.lab.base.exception.annotation;

import com.example.lab.base.exception.GeneralException;
import org.springframework.core.annotation.AliasFor;
import org.springframework.http.HttpStatus;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExceptionResponseInfo {

    /**
     * Alias for {@link #status}.
     *
     * @return http status
     */
    @AliasFor("status") HttpStatus value() default HttpStatus.BAD_REQUEST;

    /**
     * The <em>status</em> code to use for the response.
     * <p>Default is {@link HttpStatus#BAD_REQUEST}, which should
     * typically be changed to something more appropriate.
     *
     * @return http status
     * @since 4.2
     */
    @AliasFor("value")
    HttpStatus status() default HttpStatus.BAD_REQUEST;

    /**
     * 错误码，可以用于获取此错误的相关信息；
     *
     * @return error code
     */
    String errCode() default GeneralException.CODE;

    /**
     * The <em>errMsg</em> to be used for the error message.
     *
     * @return error message
     */
    String errMsg() default "";

    /**
     * 一种清晰和原始的技术细节消息，用于开发者调试API；
     *
     * @return message for developer
     */
    String devMsg() default "";

    /**
     * 用于获取该错误更多信息的URL地址；
     *
     * @return more information url
     */
    String moreInfoUrl() default "mailto:support@soundbus.cn";
}

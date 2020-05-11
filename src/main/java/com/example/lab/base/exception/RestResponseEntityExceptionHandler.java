package com.example.lab.base.exception;

import com.example.lab.base.response.RestErrorResponse;
import com.example.lab.base.servlet.handler.MapRestErrorResponseConverter;
import com.example.lab.base.servlet.handler.RestErrorResponseConverter;
import com.example.lab.base.servlet.handler.RestExceptionResolver;
import com.nimbusds.oauth2.sdk.GeneralException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    public RestResponseEntityExceptionHandler() {
        super();
    }

    @Autowired
    private RestExceptionResolver restExceptionResolver;

    private RestErrorResponseConverter<?> restErrorResponseConverter = new MapRestErrorResponseConverter();

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleBadRequest(final Exception exception, final WebRequest webRequest) {
        RestErrorResponse responseEntity = restExceptionResolver.resolveError((ServletWebRequest) webRequest, null, exception);
        if (responseEntity != null) {
            if (restErrorResponseConverter != null) {
                HttpStatus status = HttpStatus.valueOf(responseEntity.getStatus());
                Object info = restErrorResponseConverter.convert(responseEntity);
                return ResponseEntity.status(status).body(info);
            }
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GeneralException(exception.getMessage()));
    }

    /**
     * 接管父类具体异常的处理
     *
     * @param ex 发生的异常
     * @param body 响应body内容
     * @param headers 响应header
     * @param status 响应http status
     * @param request 请求instance
     * @return 响应消息
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }
        return handleBadRequest(ex, request);
    }
}

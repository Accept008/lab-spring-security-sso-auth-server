package com.example.lab.base.servlet.handler;

import com.example.lab.base.response.RestErrorResponse;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * A {@code RestExceptionResolver} resolves an exception and produces a {@link RestErrorResponse} instance that can be used
 * to render a Rest error representation to the response body.
 *
 */
public interface RestExceptionResolver {

    /**
     * Returns a {@code RestError} instance to render as the response body based on the given exception.
     *
     * @param request current {@link ServletWebRequest} that can be used to obtain the source request/response pair.
     * @param handler the executed handler, or <code>null</code> if none chosen at the time of the exception
     *                (for example, if multipart resolution failed)
     * @param ex      the exception that was thrown during handler execution
     * @return a resolved {@code RestError} instance to render as the response body or <code>null</code> for default
     * processing
     */
    RestErrorResponse resolveError(ServletWebRequest request, Object handler, Exception ex);
}


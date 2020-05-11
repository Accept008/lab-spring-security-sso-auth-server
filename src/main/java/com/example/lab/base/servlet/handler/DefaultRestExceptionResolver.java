package com.example.lab.base.servlet.handler;

import com.example.lab.base.exception.BaseException;
import com.example.lab.base.exception.ExceptionDetail;
import com.example.lab.base.exception.annotation.ExceptionResponseInfo;
import com.example.lab.base.response.RestErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.*;

/**
 * Default {@code RestExceptionResolver} implementation that converts discovered Exceptions to
 * {@link RestErrorResponse} instances.
 *
 * @author <a href="mailto:jacky.wu@soundnet.com.cn">Jacky Wu</a>
 */
public class DefaultRestExceptionResolver
        implements RestExceptionResolver, MessageSourceAware, InitializingBean, Ordered {

    private static final String DEFAULT_EXCEPTION_MESSAGE_VALUE = "_exmsg";
    private static final String DEFAULT_MESSAGE_VALUE = "_msg";

    private static final Logger log = LoggerFactory.getLogger(DefaultRestExceptionResolver.class);

    private Map<String, RestErrorResponse> exceptionMappings = new LinkedHashMap<>();

    private Map<String, String> exceptionMappingDefinitions = new LinkedHashMap<>();

    private MessageSource messageSource;
    private LocaleResolver localeResolver;

    private String defaultMoreInfoUrl;
    private boolean defaultEmptyCodeToStatus;
    private String defaultDeveloperMessage;

    public DefaultRestExceptionResolver() {
        this.defaultEmptyCodeToStatus = true;
        this.defaultDeveloperMessage = DEFAULT_EXCEPTION_MESSAGE_VALUE;
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public void setLocaleResolver(LocaleResolver resolver) {
        this.localeResolver = resolver;
    }

    public void setExceptionMappingDefinitions(Map<String, String> exceptionMappingDefinitions) {
        this.exceptionMappingDefinitions = exceptionMappingDefinitions;
    }

    public void setDefaultMoreInfoUrl(String defaultMoreInfoUrl) {
        this.defaultMoreInfoUrl = defaultMoreInfoUrl;
    }

    public void setDefaultEmptyCodeToStatus(boolean defaultEmptyCodeToStatus) {
        this.defaultEmptyCodeToStatus = defaultEmptyCodeToStatus;
    }

    public void setDefaultDeveloperMessage(String defaultDeveloperMessage) {
        this.defaultDeveloperMessage = defaultDeveloperMessage;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        //populate with some defaults:
        Map<String, String> definitions = createDefaultExceptionMappingDefinitions();

        //add in user-specified mappings (will override defaults as necessary):
        if (this.exceptionMappingDefinitions != null && !this.exceptionMappingDefinitions
                .isEmpty()) {
            definitions.putAll(this.exceptionMappingDefinitions);
        }

        this.exceptionMappings = toRestErrors(definitions);
    }

    protected final Map<String, String> createDefaultExceptionMappingDefinitions() {

        Map<String, String> m = new LinkedHashMap<String, String>();

        // 400
        applyDef(m, HttpMessageNotReadableException.class, HttpStatus.BAD_REQUEST);
        applyDef(m, MissingServletRequestParameterException.class, HttpStatus.BAD_REQUEST);
        applyDef(m, TypeMismatchException.class, HttpStatus.BAD_REQUEST);
        applyDef(m, "javax.validation.ValidationException", HttpStatus.BAD_REQUEST);

        // 404
        applyDef(m, NoHandlerFoundException.class, HttpStatus.NOT_FOUND);
        applyDef(m, "org.hibernate.ObjectNotFoundException", HttpStatus.NOT_FOUND);

        // 405
        applyDef(m, HttpRequestMethodNotSupportedException.class, HttpStatus.METHOD_NOT_ALLOWED);

        // 406
        applyDef(m, HttpMediaTypeNotAcceptableException.class, HttpStatus.NOT_ACCEPTABLE);

        // 409
        //can't use the class directly here as it may not be an available dependency:
        applyDef(m, "org.springframework.dao.DataIntegrityViolationException", HttpStatus.CONFLICT);

        // 415
        applyDef(m, HttpMediaTypeNotSupportedException.class, HttpStatus.UNSUPPORTED_MEDIA_TYPE);

        return m;
    }

    private void applyDef(Map<String, String> m, Class clazz, HttpStatus status) {
        applyDef(m, clazz.getName(), status);
    }

    private void applyDef(Map<String, String> m, String key, HttpStatus status) {
        m.put(key, definitionFor(status));
    }

    private String definitionFor(HttpStatus status) {
        return status.value() + ", " + DEFAULT_EXCEPTION_MESSAGE_VALUE;
    }

    @Override
    public RestErrorResponse resolveError(ServletWebRequest request, Object handler, Exception ex) {
        RestErrorResponse template = getRestErrorTemplate(ex);
        if (template == null) {
            return null;
        }

        RestErrorResponse.Builder builder = new RestErrorResponse.Builder();
        builder.setStatus(getStatusValue(template, request, ex));
        builder.setErrCode(getErrCode(template, request, ex));
        builder.setMoreInfoUrl(getMoreInfoUrl(template, request, ex));
        builder.setThrowable(ex);

        String msg = getMessage(template, request, ex);
        if (msg != null) {
            builder.setErrMsg(msg);
        }
        msg = getDeveloperMessage(template, request, ex);
        if (msg != null) {
            builder.setDevMsg(msg);
        }

        return builder.build();
    }

    protected int getStatusValue(RestErrorResponse template, ServletWebRequest request, Exception ex) {
        return template.getStatus();
    }

    protected String getErrCode(RestErrorResponse template, ServletWebRequest request, Exception ex) {
        String code = template.getErrCode();
        if (StringUtils.isEmpty(code) && defaultEmptyCodeToStatus) {
            code = String.valueOf(getStatusValue(template, request, ex));
        }
        return code;
    }

    protected String getMoreInfoUrl(RestErrorResponse template, ServletWebRequest request, Exception ex) {
        if (this.defaultMoreInfoUrl != null ) {
            return this.defaultMoreInfoUrl;
        } else {
            return template.getMoreInfoUrl();
        }
    }

    protected String getMessage(RestErrorResponse template, ServletWebRequest request, Exception ex) {
        return getMessage(template.getErrMsg(), template.getErrMsg(), request, ex, false);
    }

    protected String getDeveloperMessage(RestErrorResponse template, ServletWebRequest request,
                                         Exception ex) {
        String devMsg = template.getDevMsg();
        if (devMsg == null && defaultDeveloperMessage != null) {
            devMsg = defaultDeveloperMessage;
        }
        if (DEFAULT_MESSAGE_VALUE.equals(devMsg)) {
            devMsg = template.getErrMsg();
        }
        return getMessage(devMsg, devMsg, request, ex, true);
    }

    /**
     * Returns the response status message to return to the client, or {@code null} if no
     * status message should be returned.
     *
     * @return the response status message to return to the client, or {@code null} if no
     * status message should be returned.
     */
    protected String getMessage(String msg, String defaultMsg, ServletWebRequest webRequest, Exception ex, boolean isDevMsg) {
        if (msg != null) {
            if (msg.equalsIgnoreCase("null") || msg.equalsIgnoreCase("off")) {
                return null;
            }
            if (msg.equalsIgnoreCase(DEFAULT_EXCEPTION_MESSAGE_VALUE)) {
                msg = ex.getMessage();
            }
        } else {
            msg = ex.getClass().getName();
            msg = resolveI18nMessage(msg, defaultMsg, webRequest, ex, isDevMsg);
        }
        return msg;
    }

    protected String resolveI18nMessage(String code, String defaultMessage, ServletWebRequest webRequest, Exception ex, boolean isDevMsg) {
        String msg = getValidMessageFrom(defaultMessage, ex);
        if (messageSource != null && localeResolver != null) {
            Locale locale = localeResolver.resolveLocale(webRequest.getRequest());
            if (ex instanceof BaseException && !isDevMsg) {
                BaseException bex = (BaseException) ex;
                msg = messageSource.getMessage(code, bex.getParams(), msg, locale);
            } else {
                msg = messageSource.getMessage(code, null, msg, locale);
            }
        }
        return msg;
    }

    /**
     * Get valid message from defaultMessage or exception's message
     *
     * @param defaultMessage default message
     * @param ex the exception where to get message from
     * @return the valid message, <code>null</code> when there are no valid message
     */
    private String getValidMessageFrom(String defaultMessage, Exception ex) {
        if (defaultMessage != null) {
            return defaultMessage;
        }
        if (null != ex) {
            return ex.getMessage();
        }
        return null;
    }

    /**
     * Returns the config-time 'template' RestErrorResponse instance configured for the specified Exception, or
     * {@code null} if a match was not found.
     * <p/>
     * The config-time template is used as the basis for the RestErrorResponse constructed at runtime.
     *
     * @param ex
     * @return the template to use for the RestErrorResponse instance to be constructed.
     */
    private RestErrorResponse getRestErrorTemplate(Exception ex) {
        RestErrorResponse template = null;

        ExceptionResponseInfo expInfo = AnnotatedElementUtils
                .findMergedAnnotation(ex.getClass(), ExceptionResponseInfo.class);

        if (null != expInfo) {
            template = getTemplateFromAnnotation(expInfo, ex);
        }

        if (null == template) {
            template = getTemplateFromMapping(ex);
        }

        if (log.isDebugEnabled()) {
            log.debug("Resolving to RestErrorResponse template '" + template + "' for exception of type ["
                    + ex.getClass().getName() + "]");
        }

        return template;
    }

    private RestErrorResponse getTemplateFromAnnotation(ExceptionResponseInfo expInfo, Exception ex) {
        RestErrorResponse template = toRestErrorResponse(convertAnnotationToConfigString(expInfo, ex));
        return template;
    }

    private String convertAnnotationToConfigString(ExceptionResponseInfo expInfo, Exception ex) {
        if (null == expInfo) {
            return "";
        }

        Map<String, Object> info = new HashMap();
        info.put("status", getStatus(ex, expInfo));
        info.put("errCode", getErrCode(ex, expInfo));
        info.put("errMsg", getErrMsg(ex, expInfo));
        info.put("devMsg", getDevMsg(ex, expInfo));
        info.put("moreInfoUrl", getMoreInfoUrl(ex, expInfo));

        String result = info.toString().replace("{", "").replace("}", "");
        return result;
    }

    /**
     * Get http status value from exception first, if the message is blank, get it from
     * annotation.
     *
     * @param ex   the exception
     * @param info the annotation instance
     * @return http status value
     */
    private int getStatus(Exception ex, ExceptionResponseInfo info) {
        if (ex instanceof ExceptionDetail) {
            Integer httpStatusCode = ((ExceptionDetail) ex).getHttpStatusCode();
            if (null != httpStatusCode) {
                return httpStatusCode;
            }
        }
        return info.status().value();
    }

    /**
     * Get error message from exception first, if the message is blank, get it from
     * annotation.
     *
     * @param ex   the exception
     * @param info the annotation instance
     * @return error message
     */
    private String getErrMsg(Exception ex, ExceptionResponseInfo info) {
        String message = ex.getMessage();
        if (!StringUtils.isEmpty(message)) {
            return message;
        }
        return info.errMsg();
    }

    /**
     * Get developer message from exception first, if the message is blank, get it from
     * annotation.
     *
     * @param ex   the exception
     * @param info the annotation instance
     * @return developer message
     */
    private String getDevMsg(Exception ex, ExceptionResponseInfo info) {
        if (ex instanceof ExceptionDetail) {
            String message = ((ExceptionDetail) ex).getDevMsg();
            if (!StringUtils.isEmpty(message)) {
                return message;
            }
        }
        return info.devMsg();
    }

    /**
     * Get error code from exception first, if the code is blank, get it from
     * annotation.
     *
     * @param ex   the exception
     * @param info the annotation instance
     * @return error code
     */
    private String getErrCode(Exception ex, ExceptionResponseInfo info) {
        if (ex instanceof ExceptionDetail) {
            String code = ((ExceptionDetail) ex).getErrCode();
            if (!StringUtils.isEmpty(code)) {
                return code;
            }
        }
        return info.errCode();
    }

    /**
     * Get more info url from exception first, if the more info is disabled, then return
     * <code>null</code>, otherwise if more info is blank, get it from
     * annotation.
     *
     * @param ex   the exception
     * @param info the annotation instance
     * @return more info url string or <code>null</code>
     */
    private String getMoreInfoUrl(Exception ex, ExceptionResponseInfo info) {
        if (ex instanceof ExceptionDetail) {
            ExceptionDetail e = (ExceptionDetail) ex;
            if (e.isDisableMoreInfoUrl()) {
                return null;
            }

            String moreInfo = e.getMoreInfoUrl();
            if (!StringUtils.isEmpty(moreInfo)) {
                return moreInfo;
            }
        }
        return info.moreInfoUrl();
    }

    private RestErrorResponse getTemplateFromMapping(Exception ex) {
        Map<String, RestErrorResponse> mappings = this.exceptionMappings;
        if (CollectionUtils.isEmpty(mappings)) {
            return null;
        }
        RestErrorResponse template = null;
        String dominantMapping = null;
        int deepest = Integer.MAX_VALUE;
        for (Map.Entry<String, RestErrorResponse> entry : mappings.entrySet()) {
            String key = entry.getKey();
            int depth = getDepth(key, ex);
            if (depth >= 0 && depth < deepest) {
                deepest = depth;
                dominantMapping = key;
                template = entry.getValue();
            }
        }
        if (template != null && log.isDebugEnabled()) {
            log.debug("Resolving to RestErrorResponse template '" + template + "' for exception of type ["
                    + ex.getClass().getName() + "], based on exception mapping [" + dominantMapping
                    + "]");
        }
        return template;
    }

    /**
     * Return the depth to the superclass matching.
     * <p>0 means ex matches exactly. Returns -1 if there's no match.
     * Otherwise, returns depth. Lowest depth wins.
     */
    protected int getDepth(String exceptionMapping, Exception ex) {
        return getDepth(exceptionMapping, ex.getClass(), 0);
    }

    private int getDepth(String exceptionMapping, Class exceptionClass, int depth) {
        if (exceptionClass.getName().contains(exceptionMapping)) {
            // Found it!
            return depth;
        }
        // If we've gone as far as we can go and haven't found it...
        if (exceptionClass.equals(Throwable.class)) {
            return -1;
        }
        return getDepth(exceptionMapping, exceptionClass.getSuperclass(), depth + 1);
    }


    protected Map<String, RestErrorResponse> toRestErrors(Map<String, String> smap) {
        if (CollectionUtils.isEmpty(smap)) {
            return Collections.emptyMap();
        }

        Map<String, RestErrorResponse> map = new LinkedHashMap<String, RestErrorResponse>(smap.size());

        for (Map.Entry<String, String> entry : smap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            RestErrorResponse template = toRestErrorResponse(value);
            map.put(key, template);
        }

        return map;
    }

    protected RestErrorResponse toRestErrorResponse(String exceptionConfig) {
        String[] values = StringUtils.commaDelimitedListToStringArray(exceptionConfig);
        if (values.length == 0) {
            throw new IllegalStateException(
                    "Invalid config mapping.  Exception names must map to a string configuration.");
        }

        RestErrorResponse.Builder builder = new RestErrorResponse.Builder();

        boolean statusSet = false;
        boolean errCodeSet = false;
        boolean errMsgSet = false;
        boolean devMsgSet = false;
        boolean moreInfoUrlSet = false;

        for (String value : values) {

            String trimmedVal = StringUtils.trimWhitespace(value);

            //check to see if the value is an explicitly named key/value pair:
            String[] pair = StringUtils.split(trimmedVal, "=");
            if (pair != null) {
                //explicit attribute set:
                String pairKey = StringUtils.trimWhitespace(pair[0]);
                if (!StringUtils.hasText(pairKey)) {
                    pairKey = null;
                }
                String pairValue = StringUtils.trimWhitespace(pair[1]);
                if (!StringUtils.hasText(pairValue)) {
                    pairValue = null;
                }
                if ("status".equalsIgnoreCase(pairKey)) {
                    int statusCode = getRequiredInt(pairKey, pairValue);
                    builder.setStatus(statusCode);
                    statusSet = true;
                } else if ("errCode".equalsIgnoreCase(pairKey)) {
                    builder.setErrCode(pairValue);
                    errCodeSet = true;
                } else if ("errMsg".equalsIgnoreCase(pairKey)) {
                    builder.setErrMsg(pairValue);
                    errMsgSet = true;
                } else if ("devMsg".equalsIgnoreCase(pairKey)) {
                    builder.setDevMsg(pairValue);
                    devMsgSet = true;
                } else if ("moreInfoUrl".equalsIgnoreCase(pairKey)) {
                    builder.setMoreInfoUrl(pairValue);
                    moreInfoUrlSet = true;
                }
            } else {
                //not a key/value pair - use heuristics to determine what value is being set:
                int val;
                if (!statusSet) {
                    val = getInt("status", trimmedVal);
                    if (val > 0) {
                        builder.setStatus(val);
                        statusSet = true;
                        continue;
                    }
                }
                if (!errCodeSet && trimmedVal.startsWith("E")) {
                    builder.setErrCode(trimmedVal);
                    errCodeSet = true;
                    continue;
                }
                if (!moreInfoUrlSet && trimmedVal.toLowerCase().startsWith("http")) {
                    builder.setMoreInfoUrl(trimmedVal);
                    moreInfoUrlSet = true;
                    continue;
                }
                if (!errMsgSet) {
                    builder.setErrMsg(trimmedVal);
                    errMsgSet = true;
                    continue;
                }
                if (!devMsgSet) {
                    builder.setDevMsg(trimmedVal);
                    devMsgSet = true;
                    continue;
                }
                if (!moreInfoUrlSet) {
                    builder.setMoreInfoUrl(trimmedVal);
                    moreInfoUrlSet = true;
                    //noinspection UnnecessaryContinue
                    continue;
                }
            }
        }

        return builder.build();
    }

    private static int getRequiredInt(String key, String value) {
        try {
            Integer anInt = Integer.valueOf(value);
            return Math.max(-1, anInt);
        } catch (NumberFormatException e) {
            String msg =
                    "Configuration element '" + key + "' requires an integer value.  The value "
                            + "specified: " + value;
            throw new IllegalArgumentException(msg, e);
        }
    }

    private static int getInt(String key, String value) {
        try {
            return getRequiredInt(key, value);
        } catch (IllegalArgumentException iae) {
            return 0;
        }
    }

    @Override
    public int getOrder() {
        return 100;
    }
}

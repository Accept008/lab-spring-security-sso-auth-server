package com.example.lab.base.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;

/**
 * 错误响应消息模型
 */
//@ApiModel(value = "RestErrorResponse", description = "错误响应消息")
public class RestErrorResponse extends AbstractResponse implements StatusSupportResponse {

    private static final long serialVersionUID = 367720537127219293L;

//    @ApiModelProperty(value = "errCode", example = "E2003", notes = "错误代码")
    private String errCode;
//    @ApiModelProperty(value = "errMsg", example = "参数A必填", notes = "错误消息")
    private String errMsg;
//    @ApiModelProperty(value = "devMsg", example = "AppId无效", notes = "面向开发者的信息")
    private String devMsg;
//    @ApiModelProperty(value = "moreInfoUrl", example = "https://developer.sonicmoving.cn/help.html", notes = "参考资料URL地址")
    private String moreInfoUrl;
    @JsonIgnore
    private Throwable throwable;

    /**
     * It's not recommend use this construct, use {@link #RestErrorResponse(HttpStatus, String, String, String, String, Throwable, String)}
     * instead.
     *
     * @param requestId the requestId should from http request header
     */
    public RestErrorResponse(String requestId) {
        super(requestId);
    }

    public RestErrorResponse(HttpStatus status, String errCode, String errMsg, String devMsg,
                             String moreInfoUrl, Throwable throwable, String requestId) {
        super(requestId);
        if (status == null) {
            throw new NullPointerException("HttpStatus argument cannot be null.");
        }
        this.setStatus(status);
        this.errCode = errCode;
        this.errMsg = errMsg;
        this.devMsg = devMsg;
        this.moreInfoUrl = moreInfoUrl;
        this.throwable = throwable;
    }

    public String getErrCode() {
        return errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public String getDevMsg() {
        return devMsg;
    }

    public String getMoreInfoUrl() {
        return moreInfoUrl;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof RestErrorResponse) {
            RestErrorResponse re = (RestErrorResponse) o;
            // @formatter:off
            return
                    ObjectUtils.nullSafeEquals(getStatus(), re.getStatus())
                            && ObjectUtils.nullSafeEquals(getErrCode(), re.getErrCode())
                            && ObjectUtils.nullSafeEquals(getErrMsg(), re.getErrMsg())
                            && ObjectUtils.nullSafeEquals(getDevMsg(), re.getDevMsg())
                            && ObjectUtils.nullSafeEquals(getMoreInfoUrl(), re.getMoreInfoUrl())
                            && ObjectUtils.nullSafeEquals(getThrowable(), re.getThrowable())
                            && ObjectUtils.nullSafeEquals(getRequestId(), re.getRequestId());
            // @formatter:on
        }

        return false;
    }

    @Override
    public int hashCode() {
        //noinspection ThrowableResultOfMethodCallIgnored
        return ObjectUtils.nullSafeHashCode(
                new Object[]{getStatus(), getErrCode(), getErrMsg(), getDevMsg(), getMoreInfoUrl(),
                        getThrowable(), getRequestId()});
    }

    @Override
    public String toString() {
        int status = getStatus();
        //noinspection StringBufferReplaceableByString
        return new StringBuilder().append(status).append(" (")
                .append(HttpStatus.valueOf(status).getReasonPhrase()).append(" )").toString();
    }

    public static class Builder {

        private HttpStatus status;
        private String errCode;
        private String errMsg;
        private String devMsg;
        private String moreInfoUrl;
        private String requestId;
        private Throwable throwable;

        public Builder() {
        }

        public Builder setStatus(int statusCode) {
            this.status = HttpStatus.valueOf(statusCode);
            return this;
        }

        public Builder setStatus(HttpStatus status) {
            this.status = status;
            return this;
        }

        public Builder setErrCode(String errCode) {
            this.errCode = errCode;
            return this;
        }

        public Builder setErrMsg(String errMsg) {
            this.errMsg = errMsg;
            return this;
        }

        public Builder setDevMsg(String devMsg) {
            this.devMsg = devMsg;
            return this;
        }

        public Builder setMoreInfoUrl(String moreInfoUrl) {
            this.moreInfoUrl = moreInfoUrl;
            return this;
        }

        public Builder setThrowable(Throwable throwable) {
            this.throwable = throwable;
            return this;
        }

        public Builder setRequestId(String requestId) {
            this.requestId = requestId;
            return this;
        }

        public RestErrorResponse build() {
            if (this.status == null) {
                this.status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
            return new RestErrorResponse(this.status, this.errCode, this.errMsg, this.devMsg,
                    this.moreInfoUrl, this.throwable, this.requestId);
        }
    }
}

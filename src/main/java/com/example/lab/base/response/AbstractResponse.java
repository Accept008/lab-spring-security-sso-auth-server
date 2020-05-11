package com.example.lab.base.response;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import java.util.UUID;

/**
 * 封装重复代码
 */
public abstract class AbstractResponse implements StatusSupportResponse {
    /**
     * 响应的HTTP状态码；为了兼容客户端某些工具无法获取HTTP状态码；
     */
//    @ApiModelProperty(value = "status", example = "200")
    protected int status;

    /**
     * 请求ID；如果客户端发起的http请求在头部设置了"X-REQUEST-ID"，则此属性的值将会是客户端提交的头部,
     * 为"X-REQUEST-ID"的值；否则会在服务器端生成一个全局唯一的长度为36位字符串的UUID（大写带"-"）
     */
//    @ApiModelProperty(value = "requestId", example = "CF910C95-3B97-4EEA-B30A-794E5156FD55")
    protected String requestId;

    public AbstractResponse() {
        this(null);
    }

    public AbstractResponse(String requestId) {
        if (StringUtils.isEmpty(requestId)) {
            requestId = genUuid();
        } else {
            requestId = requestId.toUpperCase();
        }
        this.requestId = requestId;
    }

    protected String genUuid() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().toUpperCase();
    }

    @Override
    public int getStatus() {
        return this.status;
    }

    @Override
    public void setStatus(int status) throws IllegalArgumentException {
        // 利用此方法检查状态值的有消息，如无效则抛出异常
        //noinspection ResultOfMethodCallIgnored
        HttpStatus.valueOf(status);
        this.status = status;
    }

    @Override
    public void setStatus(HttpStatus status) {
        if (null != status) {
            this.status = status.value();
        }
    }

    public String getRequestId() {
        return this.requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}

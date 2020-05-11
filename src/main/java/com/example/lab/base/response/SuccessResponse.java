package com.example.lab.base.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
public class SuccessResponse<T> extends AbstractResponse implements StatusSupportResponse {

    private static final long serialVersionUID = -8926494529609252631L;

    /**
     * 此操作相应的提示信息；
     */
//    @ApiModelProperty(value = "message", example = "操作成功")
    private String message;

    /**
     * 负载属性，由API实现者自己定义需要放在此属性的内容
     * <p>
     * 如果是分页查询，则分页的PageImpl也应该设置到此属性上；
     */
    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
//    @ApiModelProperty(value = "payload", example = "{\"myProp\":\"myValue\"}")
    private T payload;

    public SuccessResponse(String requestId) {
        super(requestId);
    }

    public SuccessResponse() {
        this(null);
    }

    public static <T extends Serializable> SuccessResponse<T> buildOkResponse() {
        return buildOkResponseWithRequestId(null);
    }

    public static <T extends Serializable> SuccessResponse<T> buildOkResponseWithRequestId(String requestId) {
        SuccessResponse<T> rsp = new SuccessResponse<>(requestId);
        rsp.setStatus(HttpStatus.OK.value());
        return rsp;
    }

    public static <T extends Serializable> SuccessResponse<T> buildOkResponse(String message) {
        return buildOkResponse(message, null);
    }

    public static <T extends Serializable> SuccessResponse<T> buildOkResponse(String message, T payload) {
        SuccessResponse<T> rsp = buildOkResponse();
        rsp.setMessage(message);
        rsp.setPayload(payload);
        return rsp;
    }

    public static <T extends Serializable> SuccessResponse<T> buildOkResponseWithPayload(T payload) {
        return buildOkResponse("", payload);
    }
}

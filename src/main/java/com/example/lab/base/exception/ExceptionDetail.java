package com.example.lab.base.exception;

public interface ExceptionDetail {

    int getHttpStatusCode();

    String getMessage();

    String getErrCode();

    String getDevMsg();

    String getMoreInfoUrl();

    boolean isDisableMoreInfoUrl();

}

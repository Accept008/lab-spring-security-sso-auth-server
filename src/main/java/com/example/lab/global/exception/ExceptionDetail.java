package com.example.lab.global.exception;

public interface ExceptionDetail {

    int getHttpStatusCode();

    String getMessage();

    String getErrCode();

    String getDevMsg();

    String getMoreInfoUrl();

    boolean isDisableMoreInfoUrl();

}


package com.example.lab.account.domain;

import java.io.Serializable;

public class ThirdPartyAccountInfo implements Serializable {

    private static final long serialVersionUID = -7155758167584325422L;

    private String type;

    private String accessToken;

    private String userId;

    private String nickName;

    private String iconUrl;

    public ThirdPartyAccountInfo() {
    }

    public ThirdPartyAccountInfo(String type, String accessToken, String userId, String nickName, String iconUrl) {
        super();
        this.type = type;
        this.accessToken = accessToken;
        this.userId = userId;
        this.nickName = nickName;
        this.iconUrl = iconUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

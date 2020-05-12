package com.example.lab.reponse;

import cn.soundbus.library.spring.web.api.response.SuccessResponse;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

@Data
@JsonSerialize(using = OauthSuccessResponseSerializer.class)
public class OauthSuccessResponse extends SuccessResponse<OAuth2AccessToken> {

}

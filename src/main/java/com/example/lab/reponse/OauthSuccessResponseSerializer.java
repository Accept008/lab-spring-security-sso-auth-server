package com.example.lab.reponse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import java.io.IOException;

public class OauthSuccessResponseSerializer  extends StdSerializer<OauthSuccessResponse> {

    public OauthSuccessResponseSerializer() {
        super(OauthSuccessResponse.class);
    }

    @Override
    public void serialize(OauthSuccessResponse value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        OAuth2AccessToken oAuth2AccessToken = (OAuth2AccessToken) value.getPayload();
        gen.writeStartObject();
        gen.writeNumberField("status", value.getStatus());
        gen.writeStringField("requestId", value.getRequestId());
        gen.writeStringField("message", value.getMessage());

        gen.writeObjectFieldStart("payload");
        gen.writeStringField("access_token", oAuth2AccessToken.getValue());
        gen.writeStringField("token_type", oAuth2AccessToken.getTokenType());
        gen.writeStringField("refresh_token", oAuth2AccessToken.getRefreshToken().getValue());
        gen.writeNumberField("expires_in",oAuth2AccessToken.getExpiresIn());
        gen.writeStringField("scope",oAuth2AccessToken.getScope().toString());
//        gen.writeStringField("company",oAuth2AccessToken.getAdditionalInformation().get("company").toString());
        gen.writeStringField("jti",oAuth2AccessToken.getAdditionalInformation().get("jti").toString());
        gen.writeEndObject();

        gen.writeEndObject();
    }
}

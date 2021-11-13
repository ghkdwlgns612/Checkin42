package com.checkin.CheckIn.utils.resource;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

public enum CustomOAuth2Provider {
    FOURTYTWO {
        @Override
        public ClientRegistration.Builder getBuilder(String registrationId) {
            ClientRegistration.Builder builder = ClientRegistration.withRegistrationId(registrationId);

            builder.clientAuthenticationMethod(ClientAuthenticationMethod.PRIVATE_KEY_JWT);
            builder.redirectUri(DEFAULT_LOGIN_REDIRECT_URL + registrationId);
            builder.authorizationUri("https://api.intra.42.fr/oauth/authorize");
            builder.tokenUri("https://api.intra.42.fr/oauth/token");
            builder.userInfoUri("https://api.intra.42.fr/v2/me");
            builder.clientName("42");
            return builder;
        }
    };


    private static final String DEFAULT_LOGIN_REDIRECT_URL = "{baseUrl}/login/callback/";

    protected final ClientRegistration.Builder getBuilder(String registrationId,
                                          ClientAuthenticationMethod clientAuthenticationMethod,
                                          String defaultLoginRedirectUrl) {
        ClientRegistration.Builder builder = ClientRegistration.withRegistrationId(registrationId);
        builder.clientAuthenticationMethod(clientAuthenticationMethod);
        return builder;
    }

    public abstract ClientRegistration.Builder getBuilder(String registrationId);

}

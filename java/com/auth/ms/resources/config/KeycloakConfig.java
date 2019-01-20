package com.auth.ms.resources.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "keycloak")
public class KeycloakConfig {

	public final static String TOKEN_URL = "token_url";
	public final static String USER_INFO_URL = "user_info_url";
	public final static String LOGOUT_URL = "logout_url";
	public final static String AUTH_SERVER_URL = "keycloak_auth_url";
	public final static String OAUTH_IDP_URL = "oauth_idp_url";
	public final static String REDIRECT_URL = "redirect_uri";
	public static final Object HOME_URL = "home_url";
	public static final Object INDEX = "index";

	public final static String UM_CLIENT_SECRET = "user-management";
	public final static String SM_CLIENT_SECRET = "sessiom-management";
	public final static String IDPM_CLIENT_SECRET = "idp-management";

	private final Map<String, String> endpoints = new HashMap<>();
	private final Map<String, String> config = new HashMap<>();

	public Map<String, String> getEndpoints() {
		return endpoints;
	}

	public Map<String, String> getConfig() {
		return config;
	}

}

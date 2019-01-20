package com.auth.ms.services.dto;

import com.auth.ms.entity.AccessTokenResponse;
import com.auth.ms.entity.User;
import com.auth.ms.resources.dto.ResponseDto;

public class SessionServicesDto {

	private String idp;
	private String brokerAuthURL;
	private String realm;
	private String clientId;
	private String redirect_uri;
	private String sessionState;
	private String code;

	private AccessTokenResponse accessTokenResponse = new AccessTokenResponse();
	private User user = new User();

	private ResponseDto responseDto;

	public String getIdp() {
		return idp;
	}

	public void setIdp(String idp) {
		this.idp = idp;
	}

	public String getBrokerAuthURL() {
		return brokerAuthURL;
	}

	public void setBrokerAuthURL(String brokerAuthURL) {
		this.brokerAuthURL = brokerAuthURL;
	}

	public String getRealm() {
		return realm;
	}

	public void setRealm(String realm) {
		this.realm = realm;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getRedirect_uri() {
		return redirect_uri;
	}

	public void setRedirect_uri(String redirect_uri) {
		this.redirect_uri = redirect_uri;
	}

	public String getSessionState() {
		return sessionState;
	}

	public void setSessionState(String sessionState) {
		this.sessionState = sessionState;
	}

	public AccessTokenResponse getAccessTokenResponse() {
		return accessTokenResponse;
	}

	public void setAccessTokenResponse(AccessTokenResponse accessTokenResponse) {
		this.accessTokenResponse = accessTokenResponse;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public ResponseDto getResponseDto() {
		return responseDto;
	}

	public void setResponseDto(ResponseDto responseDto) {
		this.responseDto = responseDto;
	}

}

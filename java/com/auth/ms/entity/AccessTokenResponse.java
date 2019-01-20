package com.auth.ms.entity;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

@Component
public class AccessTokenResponse {
	@JsonProperty("access_token")
	protected String access_token;

	@JsonProperty("expires_in")
	protected long expires_in;

	@JsonProperty("refresh_expires_in")
	protected long refresh_expires_in;

	@JsonProperty("refresh_token")
	protected String refresh_token;

	@JsonProperty("token_type")
	protected String token_type;

	@JsonProperty("id_token")
	protected String id_token;

	@JsonProperty("not-before-policy")
	protected int notBeforePolicy;

	@JsonProperty("session_state")
	protected String session_state;

	protected Map<String, Object> otherClaims = new HashMap<String, Object>();

	@JsonProperty("scope")
	protected String scope;

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public long getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(long expires_in) {
		this.expires_in = expires_in;
	}

	public long getRefresh_expires_in() {
		return refresh_expires_in;
	}

	public void setRefresh_expires_in(long refresh_expires_in) {
		this.refresh_expires_in = refresh_expires_in;
	}

	public String getRefresh_token() {
		return refresh_token;
	}

	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}

	public String getToken_type() {
		return token_type;
	}

	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}

	public String getId_token() {
		return id_token;
	}

	public void setId_token(String id_token) {
		this.id_token = id_token;
	}

	public int getNotBeforePolicy() {
		return notBeforePolicy;
	}

	public void setNotBeforePolicy(int notBeforePolicy) {
		this.notBeforePolicy = notBeforePolicy;
	}

	public String getSession_state() {
		return session_state;
	}

	public void setSession_state(String session_state) {
		this.session_state = session_state;
	}

	public Map<String, Object> getOtherClaims() {
		return otherClaims;
	}

	public void setOtherClaims(Map<String, Object> otherClaims) {
		this.otherClaims = otherClaims;
	}

}

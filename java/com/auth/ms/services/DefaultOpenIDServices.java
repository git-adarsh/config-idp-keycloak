package com.auth.ms.services;

import java.net.URI;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.auth.ms.entity.AccessTokenResponse;
import com.auth.ms.entity.User;
import com.auth.ms.http.HttpClient;
import com.auth.ms.resources.config.KeycloakConfig;
import com.auth.ms.services.dto.OpenIDServicesDto;

@Service("OpenIDServices")
public class DefaultOpenIDServices implements OpenIDServices {

	@Autowired
	HttpClient httpClient;

	@Autowired
	KeycloakConfig kcConfig;

	@Override
	public OpenIDServicesDto getUserInfo(OpenIDServicesDto dto) {
		OpenIDServicesDto retDto = new OpenIDServicesDto();

		BeanUtils.copyProperties(dto, retDto);

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + dto.getAccessTokenResponse().getAccess_token());

		RequestEntity<MultiValueMap<String, String>> requestEntity = new RequestEntity<MultiValueMap<String, String>>(
				null, headers, HttpMethod.GET, URI.create(
						kcConfig.getEndpoints().get(KeycloakConfig.USER_INFO_URL).replace("{realm}", dto.getRealm())));

		ResponseEntity<User> responseEntity = httpClient.getRestTemplate().exchange(requestEntity, User.class);
		System.out.println("Received user info: " + responseEntity.getBody().toString());

		retDto.setUser(responseEntity.getBody());

		return retDto;
	}

	@Override
	public OpenIDServicesDto getAccessToken(OpenIDServicesDto oidDto) {

		OpenIDServicesDto retDto = new OpenIDServicesDto();

		BeanUtils.copyProperties(oidDto, retDto);

		MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();

		requestParams.add("grant_type", "authorization_code");
		requestParams.add("code", oidDto.getCode());
		requestParams.add("client_id", oidDto.getClientId());
		requestParams.add("client_secret", kcConfig.getConfig().get(oidDto.getClientId()));

		if (oidDto.getRedirect_uri() == null) {
			requestParams.add("redirect_uri",
					kcConfig.getEndpoints().get(KeycloakConfig.HOME_URL).replace("{realm}", oidDto.getRealm()));
			retDto.setRedirect_uri(
					kcConfig.getEndpoints().get(KeycloakConfig.HOME_URL).replace("{realm}", oidDto.getRealm()));
		} else {
			requestParams.add("redirect_uri", kcConfig.getEndpoints().get(KeycloakConfig.REDIRECT_URL)
					.replace("{realm}", oidDto.getRealm()).replace("{client_id}", oidDto.getClientId()));
			retDto.setRedirect_uri(oidDto.getRedirect_uri());
		}

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);

		RequestEntity<MultiValueMap<String, String>> requestEntity = new RequestEntity<>(requestParams, headers,
				HttpMethod.POST, URI.create(
						kcConfig.getEndpoints().get(KeycloakConfig.TOKEN_URL).replace("{realm}", oidDto.getRealm())));

		ResponseEntity<AccessTokenResponse> responseEntity = httpClient.getRestTemplate().exchange(requestEntity,
				AccessTokenResponse.class);

		System.out.println("Method:: DefaultSessionServices.loginRedirect : AccessToken["
				+ responseEntity.getBody().toString() + "]");

		retDto.setAccessTokenResponse(responseEntity.getBody());

		return retDto;
	}

	@Override
	public OpenIDServicesDto renewAccessToken(OpenIDServicesDto dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void logout(OpenIDServicesDto dto) {

		MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();

		requestParams.add("refresh_token", dto.getAccessTokenResponse().getRefresh_token());
		requestParams.add("client_id", dto.getClientId());
		requestParams.add("client_secret", kcConfig.getConfig().get(dto.getClientId()));

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
		RequestEntity<MultiValueMap<String, String>> requestEntity = new RequestEntity<>(requestParams, headers,
				HttpMethod.POST,
				URI.create(kcConfig.getEndpoints().get(KeycloakConfig.LOGOUT_URL).replace("{realm}", dto.getRealm())));
		ResponseEntity<Object> responseEntity = httpClient.getRestTemplate().exchange(requestEntity, Object.class);

		System.out.println(responseEntity.getStatusCode());
	}
}

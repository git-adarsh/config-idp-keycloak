package com.auth.ms.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth.ms.http.HttpClient;
import com.auth.ms.resources.config.KeycloakConfig;
import com.auth.ms.services.dto.OpenIDServicesDto;
import com.auth.ms.services.dto.SessionServicesDto;

@Service("SessionServices")
public class DefaultSessionServices implements SessionServices {

	@Autowired
	KeycloakConfig kcConfig;

	@Autowired
	HttpClient httpClient;

	@Autowired
	OpenIDServices oidServices;

	@Override
	public SessionServicesDto login(SessionServicesDto svcDto) {
		SessionServicesDto retDto = new SessionServicesDto();
		StringBuilder brokerURL = new StringBuilder();

		brokerURL.append(
				kcConfig.getEndpoints().get(KeycloakConfig.OAUTH_IDP_URL).replace("{realm}", svcDto.getRealm()));

		brokerURL.append("?response_type=code");
		brokerURL.append("&client_id=");
		brokerURL.append(svcDto.getClientId());
		brokerURL.append("&redirect_uri=");
		brokerURL.append(kcConfig.getEndpoints().get(KeycloakConfig.REDIRECT_URL).replace("{realm}", svcDto.getRealm())
				.replace("{client_id}", svcDto.getClientId()));

		brokerURL.append("&state=");
		brokerURL.append("fd99");
		brokerURL.append("&kc_idp_hint=");
		brokerURL.append(svcDto.getIdp());

		retDto.setBrokerAuthURL(brokerURL.toString());
		System.out.println("Method:: DefaultSessionServices.login : brokerUrl[" + retDto.getBrokerAuthURL() + "]");
		return retDto;
	}

	@Override
	public SessionServicesDto logout(SessionServicesDto svcDto) {
		SessionServicesDto retDto = new SessionServicesDto();

		OpenIDServicesDto oidDto = new OpenIDServicesDto();

		BeanUtils.copyProperties(svcDto, oidDto);

		oidServices.logout(oidDto);

		return retDto;
	}

	@Override
	public SessionServicesDto loginRedirect(SessionServicesDto svcDto) {

		System.out.println("AUTH CODE[" + svcDto.getCode() + "]");
		SessionServicesDto retDto = new SessionServicesDto();

		OpenIDServicesDto oidDto = new OpenIDServicesDto();

		BeanUtils.copyProperties(svcDto, oidDto);

		OpenIDServicesDto dto = oidServices.getUserInfo(oidServices.getAccessToken(oidDto));

		BeanUtils.copyProperties(dto, retDto);

		return retDto;
	}
}

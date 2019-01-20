package com.auth.ms.services;

import com.auth.ms.services.dto.OpenIDServicesDto;

public interface OpenIDServices {
	
	public OpenIDServicesDto getUserInfo(OpenIDServicesDto dto);
	public OpenIDServicesDto renewAccessToken(OpenIDServicesDto dto);
	OpenIDServicesDto getAccessToken(OpenIDServicesDto dto);
	void logout(OpenIDServicesDto dto);
	
}
	

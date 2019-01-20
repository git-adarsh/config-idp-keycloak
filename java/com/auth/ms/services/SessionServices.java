package com.auth.ms.services;

import com.auth.ms.services.dto.SessionServicesDto;

public interface SessionServices {

	public SessionServicesDto login(SessionServicesDto dto);

	public SessionServicesDto loginRedirect(SessionServicesDto dto);
	
	public SessionServicesDto logout(SessionServicesDto dto);
}

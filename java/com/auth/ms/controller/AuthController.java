package com.auth.ms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.auth.ms.entity.AccessTokenResponse;
import com.auth.ms.entity.User;
import com.auth.ms.resources.config.KeycloakConfig;
import com.auth.ms.resources.dto.ResponseDesc;
import com.auth.ms.resources.dto.ResponseDto;
import com.auth.ms.services.SessionServices;
import com.auth.ms.services.dto.SessionServicesDto;

@Controller
@Validated
@RequestMapping("v1/auth/realms/{realm}")
public class AuthController {

	@Autowired
	SessionServices sessionServices;

	@Autowired
	KeycloakConfig kconfig;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(@PathVariable("realm") String realm, Model model) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("realm", realm);
		return "index";
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(@PathVariable("realm") String realm, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession(false);

		if (session == null) {
			// login first. set home page in redirect_uri
		}

		User user = (User) session.getAttribute("user");

		ModelAndView mv = new ModelAndView();
		mv.addObject("username", user.getName());
		System.out.println(user.getName());

		return "home";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ResponseEntity<ResponseDto> login(@PathVariable("realm") String realm, @RequestParam("idp") String idp,
			@RequestParam("redirect_uri") String redirect_uri, @RequestParam("client_id") String clientId,
			HttpServletRequest request, HttpServletResponse response) {

		System.out.println("Method:: AuthController.login : realm [" + realm + "] redirect_uri[" + redirect_uri
				+ "] client_id [" + clientId + "]");

		ResponseDto resDto = new ResponseDto();
		ResponseDesc resDesc = new ResponseDesc();
		SessionServicesDto svcDto = new SessionServicesDto();

		svcDto.setRedirect_uri(redirect_uri);
		svcDto.setIdp(idp);
		svcDto.setClientId(clientId);
		svcDto.setRealm(realm);

		svcDto = sessionServices.login(svcDto);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", svcDto.getBrokerAuthURL());

		HttpSession session = request.getSession();
		session.setAttribute("redirect_uri", redirect_uri);

		resDesc.setCode("302");
		resDesc.setMessage("Redirected to oauth broker url");

		resDto.setDesc(resDesc);
		resDto.setStatus("success");

		return new ResponseEntity<>(resDto, headers, HttpStatus.FOUND);
	}

	@RequestMapping(value = "/{client_id}/login-redirect", method = RequestMethod.GET)
	public ResponseEntity<ResponseDto> loginRedirect(@PathVariable("realm") String realm,
			@RequestParam("session_state") String sessionState, @RequestParam("code") String code,
			@RequestParam("state") String state, @PathVariable("client_id") String clientId, HttpServletRequest request,
			HttpServletResponse response) {

		System.out.println("Method:: AuthController.loginRedirect : realm [" + realm + "] sessionState[" + sessionState
				+ "] client_id [" + clientId + "]");

		HttpSession session = request.getSession();

		System.out.println("Method:: AuthController.loginRedirect : session [" + session + "]");

		ResponseDto resDto = new ResponseDto();
		ResponseDesc resDesc = new ResponseDesc();

		SessionServicesDto svcDto = new SessionServicesDto();

		svcDto.setClientId(clientId);
		svcDto.setRealm(realm);
		svcDto.setCode(code);
		svcDto.setSessionState(sessionState);
		svcDto.setRedirect_uri((String) session.getAttribute("redirect_uri"));

		svcDto = sessionServices.loginRedirect(svcDto);

		System.out.println("Method:: AuthController.loginRedirect : Location [" + svcDto.getRedirect_uri() + "]");

		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", svcDto.getRedirect_uri());

		session.setAttribute("user", svcDto.getUser());
		session.setAttribute("token", svcDto.getAccessTokenResponse());

		resDesc.setCode("302");
		resDesc.setMessage("Redirected");

		resDto.setDesc(resDesc);
		resDto.setStatus("success");

		return new ResponseEntity<>(resDto, headers, HttpStatus.FOUND);
	}

	@RequestMapping(value = "/{client_id}/logout", method = RequestMethod.GET)
	public ResponseEntity<ResponseDto> logout(@PathVariable("realm") String realm,
			@PathVariable("client_id") String clientId, HttpServletRequest request) {
		HttpSession session = request.getSession(false);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", kconfig.getEndpoints().get(KeycloakConfig.INDEX).replace("{realm}", realm));

		if (session == null) {
			return new ResponseEntity<>(null, headers, HttpStatus.SEE_OTHER);
		}

		SessionServicesDto svcDto = new SessionServicesDto();

		svcDto.setClientId(clientId);
		svcDto.setAccessTokenResponse((AccessTokenResponse) session.getAttribute("token"));
		svcDto.setRealm(realm);

		svcDto = sessionServices.logout(svcDto);

		ResponseDto retDto = new ResponseDto();
		ResponseDesc retDesc = new ResponseDesc();

		retDesc.setCode("302");
		retDesc.setMessage("Log out successful.");

		retDto.setDesc(retDesc);
		retDto.setStatus("success");

		return new ResponseEntity<>(retDto, headers, HttpStatus.SEE_OTHER);

	}
}

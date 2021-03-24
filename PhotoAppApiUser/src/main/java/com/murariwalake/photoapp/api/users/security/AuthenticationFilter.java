package com.murariwalake.photoapp.api.users.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.murariwalake.photoapp.api.users.dto.UserDTO;
import com.murariwalake.photoapp.api.users.service.UserService;
import com.murariwalake.photoapp.api.users.ui.model.LoginRequestModel;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	private UserService usersService;
	private Environment environment;

	
	public AuthenticationFilter(UserService aUserService, 
			Environment aEnvironment,
			AuthenticationManager aAuthenticationManager) {
		this.usersService = aUserService;
		this.environment = aEnvironment;
		super.setAuthenticationManager(aAuthenticationManager);
	}


	@Override
	public Authentication attemptAuthentication(HttpServletRequest aRequest,
			HttpServletResponse aResponse) throws AuthenticationException {
		try {
			LoginRequestModel myLoginRequestModel = new ObjectMapper()
					.readValue(aRequest.getInputStream(), LoginRequestModel.class);

			return getAuthenticationManager().authenticate(
					new UsernamePasswordAuthenticationToken(
							myLoginRequestModel.getEmail(),
							myLoginRequestModel.getPassword(),
							new ArrayList<>())
					);

		} catch (IOException myIOException) {
			throw new RuntimeException(myIOException);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest aRequest,
			HttpServletResponse aResponse,
			FilterChain aFilterChain,
			Authentication aAuth) throws IOException, ServletException {
		String myUserName = ((User) aAuth.getPrincipal()).getUsername();
		UserDTO myUserDetails = usersService.getUserDetailsByEmail(myUserName);
		
		String mySecretToken = environment.getProperty("token.secret");
		String myJWTToken = Jwts.builder()
				.setSubject(myUserDetails.getUserId())
				.setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(environment.getProperty("token.expiration_time"))))
				.signWith(SignatureAlgorithm.HS512, mySecretToken )
				.compact();

		aResponse.addHeader("token", myJWTToken);
		aResponse.addHeader("userId", myUserDetails.getUserId());
	} 
}

package com.murariwalake.photoapp.api.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.murariwalake.photoapp.api.gateway.commons.CommonConstants;

import io.jsonwebtoken.Jwts;
import reactor.core.publisher.Mono;

@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config>{

	private static final String NO_AUTHORIZATION_HEADER_MSG = "No Authorization header";
	private static final String INVALID_JWT_TOKEN_MSG = "JWT token is invalid";

	@Autowired
	private Environment environment;

	public AuthorizationHeaderFilter() {
		super(Config.class);
	}

	public static class Config{
		//put some config
	}

	@Override
	public GatewayFilter apply(Config aConfig) {
		return (aExchange, aChain)->{
			ServerHttpRequest myServerHttpRequest = aExchange.getRequest();

			if(!myServerHttpRequest.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
				return onError(aExchange, NO_AUTHORIZATION_HEADER_MSG, HttpStatus.UNAUTHORIZED);
			}

			String myAuthorizationHeader = myServerHttpRequest.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
			String myJwtToken = myAuthorizationHeader.replace(CommonConstants.AUTHORIZATION_PREFIX, "").trim();

			if(!isJwtValid(myJwtToken)) {
				return onError(aExchange, INVALID_JWT_TOKEN_MSG, HttpStatus.UNAUTHORIZED);
			}
			return aChain.filter(aExchange);
		};
	}

	/**
	 * 
	 * @param aExchange
	 * @param aNoAuthorizationHeaderMsg
	 * @param aHttpStatus
	 * @return
	 */
	private Mono<Void> onError(ServerWebExchange aExchange, String aNoAuthorizationHeaderMsg, HttpStatus aHttpStatus) {
		ServerHttpResponse myServerHttpResponse = aExchange.getResponse();
		myServerHttpResponse.setStatusCode(aHttpStatus);
		return myServerHttpResponse.setComplete();
	}

	/**
	 * Validates if JWT token
	 * @param aJwtToken
	 * @return
	 */
	private boolean isJwtValid(String aJwtToken) {
		boolean myReturnValue = true;
		String mySubject = null;
		try{
			mySubject = Jwts.parser()
			.setSigningKey(environment.getProperty(CommonConstants.TOKEN_SECRET))
			.parseClaimsJws(aJwtToken)
			.getBody()
			.getSubject();
		}catch(Exception myException ) {
			myReturnValue = false;
		}
		if(mySubject == null || mySubject.isEmpty())
			myReturnValue = false;

		return myReturnValue;
	}
}

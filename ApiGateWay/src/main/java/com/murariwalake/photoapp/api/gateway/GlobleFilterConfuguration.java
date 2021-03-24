package com.murariwalake.photoapp.api.gateway;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Configuration
public class GlobleFilterConfuguration {

	private static final Logger logger = LoggerFactory.getLogger(GlobleFilterConfuguration.class);
	
	@Bean
	public GlobalFilter globlePreFilter() {
		return (exchange, chain)->{
			String myUrlPath = exchange.getRequest().getPath().toString();
			logger.info("Entering into URL path: "+myUrlPath);
			logger.info("Request type: "+exchange.getRequest().getMethodValue());
			
			HttpHeaders myHttpHeaders  = exchange.getRequest().getHeaders();
			Set<String> myHeadNames = myHttpHeaders.keySet();
			
			logger.info("Header details starts....");
			myHeadNames.forEach((myHeadName)->{
				String myHeaderValue = myHttpHeaders.getFirst(myHeadName);
				logger.info(myHeadName +": "+myHeaderValue);
			});
			logger.info("Header details ends...");
			
			return chain.filter(exchange).then(Mono.fromRunnable(()->{
				logger.info("End of "+myUrlPath);
			}));
		};
	}
	
	
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		String myUrlPath = exchange.getRequest().getPath().toString();
		logger.info("URL path: "+myUrlPath);
		
		HttpHeaders myHttpHeaders  = exchange.getRequest().getHeaders();
		Set<String> myHeadNames = myHttpHeaders.keySet();
		
		logger.info("\nHeader details starts....");
		myHeadNames.forEach((myHeadName)->{
			String myHeaderValue = myHttpHeaders.getFirst(myHeadName);
			logger.info(myHeadName +": "+myHeaderValue);
		});
		logger.info("Header details ends....\n");
		
		return chain.filter(exchange);
	}

}

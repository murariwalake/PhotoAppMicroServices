package com.murariwalake.photoapp.api.users;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableDiscoveryClient
public class PhotoAppApiUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhotoAppApiUserApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper myModelMapper = new ModelMapper();
		myModelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return myModelMapper;
	}
}

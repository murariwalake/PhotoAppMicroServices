package com.murariwalake.photoapp.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class PhotoAppDiscoverySeverApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhotoAppDiscoverySeverApplication.class, args);
	}

}

package com.murariwalake.photoapp.api;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigServer
public class PhotoAppApiConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhotoAppApiConfigServerApplication.class, args);
	}
	
	

}

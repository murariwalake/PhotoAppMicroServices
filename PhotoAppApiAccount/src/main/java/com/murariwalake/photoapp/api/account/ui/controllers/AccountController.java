package com.murariwalake.photoapp.api.account.ui.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountController {
	
	@Autowired
	private Environment environment;
	
	@GetMapping("/status/check")
	public String status() {
		return environment.getProperty("local.spring.application.name")+" is running on port: "+ environment.getProperty("local.server.port");
	}
}

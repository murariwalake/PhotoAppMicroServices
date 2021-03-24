package com.murariwalake.photoapp.api.gateway.ui.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gateway")
public class GateWayController {
	@GetMapping("/status/check")
	public String status() {
		return "Gate way Working";
	}
}

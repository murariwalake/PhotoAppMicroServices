package com.murariwalake.photoapp.api.users.ui.controllers;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.murariwalake.photoapp.api.users.dto.UserDTO;
import com.murariwalake.photoapp.api.users.service.UserService;
import com.murariwalake.photoapp.api.users.ui.model.CreateUserRequestModel;
import com.murariwalake.photoapp.api.users.ui.model.CreateUserResponseModel;
@RefreshScope
@RestController
@RequestMapping("/users")
public class UsersControl {

	@Autowired
	private Environment environment;

	@Autowired 
	private UserService userService;

	@Autowired
	ModelMapper modelMapper;

	@GetMapping("/status/check")
	public String status() {
		return " is running on port: "+ environment.getProperty("local.server.port");
	}

	@PostMapping 
	public ResponseEntity<CreateUserResponseModel> createUser(
			@Valid @RequestBody CreateUserRequestModel aCreateUserRequestModel)
					throws Exception{

		UserDTO myUserDTO = modelMapper.map(aCreateUserRequestModel, UserDTO.class);
		UserDTO myReturnedUserDTO = userService.createUser(myUserDTO);
		CreateUserResponseModel myCreateUserResponseModel = modelMapper.map(myReturnedUserDTO, CreateUserResponseModel.class);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(myCreateUserResponseModel);
	}
}

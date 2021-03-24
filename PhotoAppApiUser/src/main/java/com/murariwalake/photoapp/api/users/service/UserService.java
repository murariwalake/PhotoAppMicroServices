package com.murariwalake.photoapp.api.users.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.murariwalake.photoapp.api.users.dto.UserDTO;

public interface UserService extends UserDetailsService{
	public UserDTO createUser(UserDTO aUserDTO);

	public UserDTO getUserDetailsByEmail(String userName);
	
}

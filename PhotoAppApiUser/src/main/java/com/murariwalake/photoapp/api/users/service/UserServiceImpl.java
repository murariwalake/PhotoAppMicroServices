package com.murariwalake.photoapp.api.users.service;

import java.util.ArrayList;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.murariwalake.photoapp.api.users.data.UserEntity;
import com.murariwalake.photoapp.api.users.dto.UserDTO;
import com.murariwalake.photoapp.api.users.repo.UserRepo;

@Service
public class UserServiceImpl implements UserService{

	UserRepo userRepo;
	Environment environment;
	BCryptPasswordEncoder bCryptPasswordEncoder;
	ModelMapper modelMapper;
	
	@Autowired
	public UserServiceImpl(UserRepo aUserRepo,
			Environment aEnvironment,
			BCryptPasswordEncoder aBCryptPasswordEncoder,
			ModelMapper aModelMapper) {
		this.userRepo = aUserRepo;
		this.environment = aEnvironment;
		this.bCryptPasswordEncoder = aBCryptPasswordEncoder;
		this.modelMapper = aModelMapper;
	}
	
	@Override
	public UserDTO createUser(UserDTO aUserDTO) {
		aUserDTO.setUserId(UUID.randomUUID().toString());
		aUserDTO.setEncryptedPassword(bCryptPasswordEncoder.encode(aUserDTO.getPassword()));
		
		UserEntity myUserEntity = modelMapper.map(aUserDTO, UserEntity.class);
		
		UserEntity myUserEntityFromRepo = userRepo.save(myUserEntity);
		UserDTO myUserDTOFromRepo = modelMapper.map(myUserEntityFromRepo, UserDTO.class);
		return myUserDTOFromRepo;
	}

	@Override
	public UserDetails loadUserByUsername(String aUsername) throws UsernameNotFoundException {
		UserEntity userEntity = userRepo.findByEmail(aUsername);
		
		if(userEntity == null) throw new UsernameNotFoundException(aUsername);	
		
		return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), true, true, true, true, new ArrayList<>());
	}

	@Override
	public UserDTO getUserDetailsByEmail(String aEmail) { 
		UserEntity userEntity = userRepo.findByEmail(aEmail);
		
		if(userEntity == null) 
			throw new UsernameNotFoundException(aEmail);
				
		return modelMapper.map(userEntity, UserDTO.class);
	}
	
	

}

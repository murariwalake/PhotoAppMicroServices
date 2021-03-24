package com.murariwalake.photoapp.api.users.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.murariwalake.photoapp.api.users.data.UserEntity;

@Repository
public interface UserRepo extends CrudRepository<UserEntity, Long> {

	public UserEntity findByEmail(String email);
	public UserEntity findByUserId(String userId);

}

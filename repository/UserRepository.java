package com.obs.repository;

import org.springframework.data.repository.CrudRepository;

import com.obs.domain.User;

public interface UserRepository  extends CrudRepository<User, String>{

}

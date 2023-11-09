package com.obs.repository;

import org.springframework.data.repository.CrudRepository;

import com.obs.domain.UserSecurity;

public interface UserSecurityRepository extends CrudRepository<UserSecurity, String> {

}

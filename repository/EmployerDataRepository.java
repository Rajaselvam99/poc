package com.obs.repository;

import org.springframework.data.repository.CrudRepository;

import com.obs.domain.EmployerData;

public interface EmployerDataRepository extends CrudRepository<EmployerData, Long> {

}

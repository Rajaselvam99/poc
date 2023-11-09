package com.obs.repository;

import org.springframework.data.repository.CrudRepository;

import com.obs.domain.Jobseeker;

public interface JobseekerRepository extends CrudRepository<Jobseeker, String> {

}

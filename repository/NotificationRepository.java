package com.obs.repository;

import org.springframework.data.repository.CrudRepository;

import com.obs.domain.Notification;

public interface NotificationRepository extends CrudRepository<Notification, String> {

	

}

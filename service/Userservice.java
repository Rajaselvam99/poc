package com.obs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.obs.dao.IUserDAO;
import com.obs.domain.AccountStatus;
import com.obs.domain.Country;
import com.obs.domain.Subscriptionuser;
import com.obs.domain.User;
import com.obs.domain.UserSecurity;

@Service
public class Userservice implements IUserservice {

	@Autowired
	IUserDAO userDAO;

	@Override
	public User saveOrUpdateUser(User user) {
		return userDAO.saveOrUpdateUser(user);
	}

	@Override
	public UserSecurity saveOrUpdateUserSecurity(UserSecurity userSecurity) {
		return userDAO.saveOrUpdateUserSecurity(userSecurity);
	}

	@Override
	public User getUserByUserNameOrEmailorMobile(String username) {
		return userDAO.getUserByUserNameOrEmailorMobile(username);
	}

	@Override
	public UserSecurity getUserSecurityByUserName(String userName) {
		return userDAO.getUserSecurityByUserName(userName);
	}

	@Override
	public User getUserById(String userId) {
		return userDAO.getUserById(userId);
	}

	@Override
	public void deleteUser(User user) {
		userDAO.deleteUser(user);
	}

	@Override
	public Subscriptionuser saveOrUpdateSubscriptionuser(Subscriptionuser subscriptionuser) {
		return userDAO.saveOrUpdateSubscriptionuser(subscriptionuser);
	}

	@Override
	public Subscriptionuser getSubscriptionuserByEmailAndUserType(String email, long type) {
		return userDAO.getSubscriptionuserByEmailAndUserType(email, type);
	}

	@Override
	public User getUserByUserNameOrEmailorMobileAndUserType(String username, long type) {
		return userDAO.getUserByUserNameOrEmailorMobileAndUserType(username, type);
	}

	@Override
	public User getUserByVerifyLink(String verifyLink) {
		return userDAO.getUserByVerifyLink(verifyLink);
	}

	@Override
	public List<Country> getAllCountry() {
		return userDAO.getAllCountry();
	}

	@Override
	public void deleteAccessToken(String userId) {
		userDAO.deleteAccessToken(userId);		
	}

	@Override
	public User getUserByType(long l) {
		return userDAO.getUserByType(l);	
	}

	@Override
	public AccountStatus getAccountStatusById(long i) {
		return userDAO.getAccountStatusById(i);	
	}

	@Override
	public List<User> getUsersByType(long l) {
		return userDAO.getUsersByType(l);
	}

}

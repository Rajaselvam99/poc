package com.obs.service;

import java.util.List;

import com.obs.domain.AccountStatus;
import com.obs.domain.Country;
import com.obs.domain.Subscriptionuser;
import com.obs.domain.User;
import com.obs.domain.UserSecurity;

public interface IUserservice {

	User saveOrUpdateUser(User user);

	UserSecurity saveOrUpdateUserSecurity(UserSecurity userSecurity);

	User getUserByUserNameOrEmailorMobile(String username);

	UserSecurity getUserSecurityByUserName(String userName);

	User getUserById(String userId);
	
	void deleteUser(User user);
	
	Subscriptionuser saveOrUpdateSubscriptionuser(Subscriptionuser subscriptionuser);
	
	Subscriptionuser getSubscriptionuserByEmailAndUserType(String email, long type);

	User getUserByUserNameOrEmailorMobileAndUserType(String username, long type);

	User getUserByVerifyLink(String verifyLink);
	
	List<Country> getAllCountry();
	
	void deleteAccessToken(String userId);

	User getUserByType(long l);

	AccountStatus getAccountStatusById(long i);

	List<User> getUsersByType(long l);


}

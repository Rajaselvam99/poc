package com.obs.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.obs.domain.AccountStatus;
import com.obs.domain.Country;
import com.obs.domain.Subscriptionuser;
import com.obs.domain.User;
import com.obs.domain.UserSecurity;
import com.obs.query.UserQuery;
import com.obs.repository.SubscriptionuserRepository;
import com.obs.repository.UserRepository;
import com.obs.repository.UserSecurityRepository;


@Repository
@Transactional
public class UserDAO implements IUserDAO {
	
	private static final Logger logger = LogManager.getLogger(UserDAO.class); 
	
	@Autowired
	private EntityManager em;
	
	@Autowired
	UserRepository 	userRepository;
	
	@Autowired
	UserSecurityRepository userSecurityRepository;
	
	@Autowired
	SubscriptionuserRepository subscriptionuserRepository;
	
	
    @Override
	public User saveOrUpdateUser(User user) {
	User usr = null;
	try
	{
		usr = userRepository.save(user);
	}
	catch(Exception e)
	{
		logger.error(e);
	}
		return usr;
	}
	
    @Override
	public UserSecurity saveOrUpdateUserSecurity(UserSecurity userSecurity) {
		UserSecurity usc = null;
		try
		{
			usc = userSecurityRepository.save(userSecurity);
		}
		catch(Exception e)
		{
			logger.error(e);
		}
			return usc;
		}

    public void deleteUser(User user)
    {
    	try
    	{
    		userRepository.delete(user);
    	}
    	catch(Exception e)
    	{
    		logger.error(e);
    	}
    	
    }
	@Override
	public User getUserByUserNameOrEmailorMobile(String username) {
	List<User> list = null;
	try
	{
		TypedQuery<User> query = em.createQuery(UserQuery.USERBYUSERNAMEOREMAILORMOBILEORSOCIAL, User.class).setParameter("nm", username);
		list = query.getResultList();
	}
	catch(Exception e)
	{
		logger.error(e);
	}
		return list != null && !list.isEmpty() ? list.get(0):null;
	}

	@Override
	public UserSecurity getUserSecurityByUserName(String userName) {
		List<UserSecurity> list = null;
		try
		{
			TypedQuery<UserSecurity> query = em.createQuery(UserQuery.USERSECURITYBYUSERNAME, UserSecurity.class).setParameter("nm", userName);
			list = query.getResultList();
		}
		catch(Exception e)
		{
			logger.error(e);
		}
			return list != null && !list.isEmpty() ? list.get(0):null;
	}

	@Override
	public User getUserById(String userId) {
		List<User> list = null;
		try
		{
			TypedQuery<User> query = em.createQuery(UserQuery.USERBYID, User.class).setParameter("uid", userId);
			list = query.getResultList();
		}
		catch(Exception e)
		{
			logger.error(e);
		}
			return list != null && !list.isEmpty() ? list.get(0):null;
	}

    @Override
	public Subscriptionuser saveOrUpdateSubscriptionuser(Subscriptionuser subscriptionuser) {
    	Subscriptionuser usr = null;
	try
	{
		usr = subscriptionuserRepository.save(subscriptionuser);
	}
	catch(Exception e)
	{
		logger.error(e);
	}
		return usr;
	}
    
    @Override
	public Subscriptionuser getSubscriptionuserByEmailAndUserType(String email,long type) {
    	List<Subscriptionuser> list = null;
	try
	{
		TypedQuery<Subscriptionuser> query = em.createQuery(UserQuery.SUBSCRIPTIONUSERBYEMAILANDUSERTYPE, Subscriptionuser.class).setParameter("em", email).setParameter("t", type);
        list = query.getResultList();
	}
	catch(Exception e)
	{
		logger.error(e);
	}
	return list != null && !list.isEmpty() ? list.get(0):null;
	}
    
	@Override
	public User getUserByUserNameOrEmailorMobileAndUserType(String username,long type) {
	List<User> list = null;
	try
	{
		TypedQuery<User> query = em.createQuery(UserQuery.USERBYUSERNAMEOREMAILORMOBILEANDUSERTYPE, User.class).setParameter("nm", username).setParameter("t", type);
		list = query.getResultList();
	}
	catch(Exception e)
	{
		logger.error(e);
	}
		return list != null && !list.isEmpty() ? list.get(0):null;
	}

	@Override
	public User getUserByVerifyLink(String verifyLink) {
		List<User> list = null;
		try
		{
			TypedQuery<User> query = em.createQuery(UserQuery.USERBYVERIFYLINK, User.class).setParameter("v", verifyLink);
			list = query.getResultList();
		}
		catch(Exception e)
		{
			logger.error(e);
		}
			return list != null && !list.isEmpty() ? list.get(0):null;
	}
	
	@Override
	public List<Country> getAllCountry()
	{
		List<Country> list = null;
		try
		{
			TypedQuery<Country> query = em.createQuery(UserQuery.ALLCOUNTRY, Country.class);
			list = query.getResultList();
			
		}
		catch(Exception e)
		{
			logger.error(e);
		}
		return list != null && !list.isEmpty() ? list:null;
	}
	
	@Override
    public void deleteAccessToken(String userId) {
        try {   
            Query query = em.createQuery("delete from OauthAccessToken  where userName = :id").setParameter("id", userId);
            query.executeUpdate();
            Query query1 = em.createQuery("delete from OauthRefreshToken where tokenId not in(select tokenId from OauthAccessToken)");
            query1.executeUpdate();
        } catch (Exception e) {
            logger.error("deleteAccessToken DAO",e);
        }
   
    }
	
	@Override
	public User getUserByType(long userType) {
		List<User> list = null;
		try {			
			TypedQuery<User> query = em.createQuery("select u from User u where u.userType.userTypeId = :userType",User.class).setParameter("userType", userType);
	        list = query.getResultList();
		} catch (Exception e) {
			logger.error(e);
		}
		return (list!=null && !list.isEmpty())?list.get(0):null;
	}

	@Override
	public AccountStatus getAccountStatusById(long i) {
		List<AccountStatus> list = null;
		try {			
			TypedQuery<AccountStatus> query = em.createQuery("from AccountStatus where id =:i ",AccountStatus.class).setParameter("i", i);
	        list = query.getResultList();
		} catch (Exception e) {
			logger.error(e);
		}
		return (list!=null && !list.isEmpty())?list.get(0):null;
	}

	@Override
	public List<User> getUsersByType(long userType) {
		List<User> list = null;
		try
		{
			TypedQuery<User> query = em.createQuery("select u from User u where u.userType.userTypeId = :userType",User.class).setParameter("userType", userType);
			list = query.getResultList();
		}
		catch(Exception e)
		{
			logger.error(e);
		}
		return list != null && !list.isEmpty() ? list:null;
	}
	
	
}

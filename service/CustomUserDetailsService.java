package com.obs.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.obs.dao.IUserDAO;
import com.obs.domain.UserSecurity;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);


	@Autowired
	private IUserDAO iuserDAO;

	@Autowired
	public CustomUserDetailsService(IUserDAO iuserDAO) {
		this.iuserDAO = iuserDAO;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public UserDetails loadUserByUsername(String username)   {	
		UserSecurity usrsecure = null;
		com.obs.domain.User usr = iuserDAO.getUserById(username);
		
		if(usr != null) {
			usrsecure = iuserDAO.getUserSecurityByUserName(usr.getUserId());
		}
		if (usrsecure == null ) {
			throw new UsernameNotFoundException(String.format("User %s does not exist!", username));
		}
		
		String usrname = (usrsecure.getUser() != null)?usrsecure.getUser().getUserId():usrsecure.getUserName();
		String upassword= usrsecure.isSocial()?usrsecure.getSocialPassword():usrsecure.getPassword();
		String role = usr.getUserType().getUserTypeName();
		List authList = getAuthorities(role);  
		return new User(usrname, upassword, authList);  
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List getAuthorities(String role) {  
		List authList = new ArrayList();  
		try
		{
		authList.add(new SimpleGrantedAuthority("ROLE_USER"));       
		if (role != null && role.trim().length() > 0) {  
			if (role.equals("admin")) {
				authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));  
			}  
			if (role.equals("user")) {  
				authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));  
			} 
		}    
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
		}
		return authList;  
	}

	public com.obs.domain.User viewProfile(String username){
		com.obs.domain.User user=null;
		try {
			user = iuserDAO.getUserById(username);
		}catch(Exception e){
			logger.error("view profile", e);
		}
		return user;
	}
}

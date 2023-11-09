package com.obs.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.obs.customdomain.UserInput;
import com.obs.domain.UserSecurity;
import com.obs.messages.MessageConstants;
import com.obs.messages.PropertyConstants;


@PropertySource("classpath:ValidationMessage/userValidation-msg.properties")
@Service
public class UserValidation {

	@Autowired
	private Environment env;

	@Autowired
	private BasicValidation basicvalidation;

	@Autowired
	PasswordEncoder passwordencoder;
	
	
	public String registrationValidation(UserInput userObj) {
		if (userObj == null) {
			return env.getProperty(PropertyConstants.DATAREQUIRED);
		}	
	   System.out.println("Inside the registraction validation---->");
		if (!basicvalidation.emailValidation(userObj.getEmail())) {
			System.err.println("inside email validation condition--->"+ userObj.getEmail());
			return env.getProperty("emailValidation");
		}
		
		if ((userObj.getFacebookId() == null && userObj.getGoogleId() == null) && !basicvalidation.passwordValidation(userObj.getPassword())) {
			return env.getProperty("passwordValidation");
		}
		
		if (userObj.getUserType() <= 0) {
			return "registerType is required";
		}
		if (userObj.getEmployerDataId() <= 0) {
			
			return "employer data is required";
		}
		return MessageConstants.SUCCESS;
	}

	
	public String registrationJobseekerValidation(UserInput userObj) {
		if (userObj == null) {
			return env.getProperty(PropertyConstants.DATAREQUIRED);
		}	
	
		if (!basicvalidation.emailValidation(userObj.getEmail())) {
			return env.getProperty("emailValidation");
		}
		if ((userObj.getFacebookId() == null && userObj.getGoogleId() == null) && !basicvalidation.passwordValidation(userObj.getPassword())) {
			return env.getProperty("passwordValidation");
		}
		
		if (userObj.getUserType() <= 0) {
			return "registerType is required";
		}
		if(!basicvalidation.checkStringnullandempty(userObj.getFirstName()))
		{
			return "firstName is required";
		}
		if(!basicvalidation.checkStringnullandempty(userObj.getLastName()))
		{
			return "lastName is required";
		}
		if(!basicvalidation.checkStringnullandempty(userObj.getOccupation()))
		{
			return "occupation is required";
		}
		if (userObj.getNationalityId() <= 0) {
			return "nationalityId is required";
		}
		
		return MessageConstants.SUCCESS;
	}
	
	public String changePassword(UserInput userObj,UserSecurity usc) {
		if (userObj == null) {
			return env.getProperty(PropertyConstants.DATAREQUIRED);
		}	
	
		if (!basicvalidation.checkStringnullandempty(userObj.getPassword())) {
			return env.getProperty("passwordMandatory");
		}
		if (!basicvalidation.checkStringnullandempty(userObj.getConfirmPassword())) {
			return env.getProperty("confirmPasswordMandatory");
		}
		if(!userObj.getPassword().equalsIgnoreCase(userObj.getConfirmPassword()))
		{
			return env.getProperty("pwdconfirmpwdmismatch");
		}
		
		if (!basicvalidation.checkStringnullandempty(userObj.getOldPassword())) {
			return env.getProperty("oldpasswordMandatory");
		}
		if(!(passwordencoder.matches(userObj.getOldPassword(), usc.getPassword())))
		{
			return env.getProperty("wrongPassword");
		}
		if(passwordencoder.matches(userObj.getPassword(), usc.getPassword()))
		{
			return env.getProperty("samepassword");
		}
		return MessageConstants.SUCCESS;
	}


}

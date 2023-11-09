package com.obs.email;


import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.StringUtils;

import com.obs.domain.Jobseeker;
import com.obs.domain.PostJob;
import com.obs.domain.Subscriptionuser;
import com.obs.domain.User;
import com.obs.messages.PropertyConstants;
import com.obs.util.CommonProperties;
import com.obs.util.IoUtils;
import com.obs.view.JobseekerPathwayView;



public class EmailConfiguration {

	private static ResourceBundle config = getResourceBundle();

	private static final Logger logger = LogManager.getLogger(EmailConfiguration.class);	
	
	private static final String BUNDLENAME = "emailConfig";
	
	private static ResourceBundle getResourceBundle() {
		ResourceBundle bundle = null;
		bundle = ResourceBundle.getBundle(BUNDLENAME);
		return bundle;
	}

	
	private static String getString(String key) {
		return config.getString(key);
	}
	
	public boolean isAuth() {
		String isAuthStr = getString("isAuth");
		return ("YES".equalsIgnoreCase(isAuthStr));
	}

	public String smtpHost() {
		return getString("smtpHost");
	}

	public int smtpPort() {
		String strPort = getString("smtpPort");
		return Integer.parseInt(strPort);
	}

	public String smtpUser() {
		return getString("smtpUser");
	}

	public String smtpPassword() {
		return getString("smtpPassword");
	}
	
	public String getUserServiceEmailAddress() {
		return getString("userServiceEmailAddress");
	}
	
	//------------------------------------------------------------------

	public String getForgotPasswordSubject() {
		return getString("forgotPassword.subject");
	}

	public String getVerifyEmailSubject() {
		return getString("verifyEmail.subject");
	}

	public String getPasswordChangeSubject() {
		return getString("changepassword.subject");
	}
	
	public String getForgotUsernameSubject() {
		return getString("forgotUsername.subject");
	}

	public String getNewUserSubject() {
		return getString("newUser.subject");
	}
	
	public String getActivateUserSubject() {
		return getString("activateUser.subject");
	}
	
	public String getUserActivationAdminSubject() {
		return getString("adminUserActivation.subject");
	}
	
	public String getSubscribeUserSubject() {
		return getString("subscribeUser.subject");
	}
	
	public String getJobseekerRegistrationSubject() {
		return getString("jobseekerRegistrationEmail.subject");
	}
	
	public String getEmployerRegistrationSubject() {
		return getString("employerRegistrationEmail.subject");
	}
	
	public String getPostJobEmailSubject() {
		return getString("postJobEmail.subject");
	}
	
	public String getJobInviteEmailSubject() {
		return getString("jobInviteEmail.subject");
	}
	
	@SuppressWarnings("rawtypes")
	private String readFile(String messageFile, Map tokens) throws IOException {
		String filePath = CommonProperties.getBasePath()+CommonProperties.getContextPath()+messageFile;
		try {
			String message = new IoUtils().read(filePath);
			return replaceTokens(message, tokens);
		} catch (IOException e) {
			logger.info( "error message ", e);
		return e.getMessage();
		}
	}

	@SuppressWarnings("rawtypes")
	private String replaceTokens(String message, Map tokens) {
		for (Iterator iterator = tokens.keySet().iterator(); iterator.hasNext();) {
			String token = (String) iterator.next();
			message = StringUtils.replace(message, token, (String)tokens.get(token));
		}
		return message;
	}
	
	
	private HashMap<String,String> setBasicmapInfo(EmailObject emailObj)
	{
		HashMap<String,String> mapBasic = new HashMap<>();
		mapBasic.put(PropertyConstants.EMAIL, emailObj.getRecieveEmail());
		mapBasic.put(PropertyConstants.USERNAME, emailObj.getRecieveUserName());
		mapBasic.put(PropertyConstants.FIRSTNAME, emailObj.getRecieveFirstName());
		mapBasic.put("${password}", emailObj.getPasscode());
		mapBasic.put(PropertyConstants.URL, emailObj.getLogoUrl());
		mapBasic.put(PropertyConstants.LOGOURL, emailObj.getLogoUrl());
		return mapBasic;
	}
	
	public String userVerificationMail(String emailAddress,String msg,String username,String passcode,String link,String url,String logoUrl) throws IOException{
		HashMap<String,String> map = new HashMap<>();
		map.put("${emailAddress}", emailAddress);
		map.put("${userName}", username);
		map.put("${firstname}", username);		
		map.put("${link}", link);
		map.put("${url}", url);
		map.put("${msg}", msg);
		map.put("${logoUrl}", logoUrl);
		map.put("${password}", passcode);

		String messageFile = config.getString("accountVerifyEmail.fileName");
		return readFile(messageFile, map);
	}
	
	public String forgotPasswordMail(String emailAddress,String msg,String username,String passcode,String link,String url,String logoUrl) throws IOException{
		HashMap<String,String> map = new HashMap<>();
		map.put("${emailAddress}", emailAddress);
		map.put("${userName}", username);
		map.put("${firstname}", username);		
		map.put("${link}", link);
		map.put("${url}", url);
		map.put("${msg}", msg);
		map.put("${logoUrl}", logoUrl);
		map.put("${password}", passcode);

		String messageFile = config.getString("forgotpassword.fileName");
		return readFile(messageFile, map);
	}
	
	public String getForgotPasswordEmailMessage(EmailObject emailObj) throws IOException {
		HashMap<String,String> map = setBasicmapInfo(emailObj);
		String messageFile = config.getString("forgotPassword.fileName");
		return readFile(messageFile, map);
	}


	public String genertesubscribeUserEmailMessage(Subscriptionuser subscriptionuser, String url, String logoURL) throws IOException {
		HashMap<String,String> map = new HashMap<>();
		map.put("${emailAddress}", subscriptionuser.getEmail());
		map.put("${location}", getLocation(subscriptionuser.getLocation()));
		map.put("${rollName}", subscriptionuser.getRollName());
		map.put("${url}", url);		
		map.put("${logoUrl}", logoURL);			
		String messageFile = config.getString("subscribeUser.fileName");
		return readFile(messageFile, map);
	}


	private String getLocation(String location) {
		if(!location.equalsIgnoreCase("uk")) {
			return "Outside of the UK";
		}
		return location;
	}


	public String generteJobseekerRegistrationMail(User user, String pathway, String link1, String link2, String link3,
			String link4, String url, String logoURL) throws IOException {
		HashMap<String,String> map = new HashMap<>();
		
		map.put("${userName}", user.getFullName());
		map.put("${pathway}", pathway);
		map.put("${link1}", link1);
		map.put("${link2}", link2);
		map.put("${link3}", link3);
		map.put("${link4}", link4);
		map.put("${url}", url);	
		map.put("${logoUrl}", logoURL);
		

		String messageFile = config.getString("jobseekerRegistrationEmail.fileName");
		return readFile(messageFile, map);
	}


	public String generteEmployerRegistrationMail(User user, String link1, String link2, String url, String logoURL) throws IOException {
		HashMap<String,String> map = new HashMap<>();
		
		map.put("${userName}", user.getFullName());			
		map.put("${link1}", link1);
		map.put("${link2}", link2);		
		map.put("${url}", url);	
		map.put("${logoUrl}", logoURL);
		
		String messageFile = config.getString("employerRegistrationEmail.fileName");
		return readFile(messageFile, map);
	}


	public String genertePostJobEmail(JobseekerPathwayView jobseeker, String link, String url, String logoURL) throws IOException {
		HashMap<String,String> map = new HashMap<>();
		
		map.put("${name}", jobseeker.getJobseekerName());			
		map.put("${link}", link);			
		map.put("${url}", url);	
		map.put("${logoUrl}", logoURL);
		
		String messageFile = config.getString("postJobEmail.fileName");
		return readFile(messageFile, map);
	}


	public String generteJobInviteEmail(PostJob postJob, Jobseeker jobseeker, String link, String url, String logoURL) throws IOException {
		HashMap<String,String> map = new HashMap<>();
		
		map.put("${jobseekerName}", jobseeker.getUserInfo().getFullName());
		map.put("${name}", postJob.getEmployer().getEmployerName());	
		map.put("${jobName}", postJob.getName());	
		map.put("${employerName}", postJob.getEmployer().getEmployerName());
		map.put("${link}", link);			
		map.put("${url}", url);	
		map.put("${logoUrl}", logoURL);
		
		String messageFile = config.getString("jobInviteEmail.fileName");
		return readFile(messageFile, map);
	}
}
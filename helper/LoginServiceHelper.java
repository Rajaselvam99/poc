package com.obs.helper;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.obs.customdomain.PostJobInput;
import com.obs.customdomain.UserInput;
import com.obs.domain.AccountStatus;
import com.obs.domain.Country;
import com.obs.domain.Employer;
import com.obs.domain.EmployerData;
import com.obs.domain.JobRefer;
import com.obs.domain.Jobseeker;
import com.obs.domain.Notification;
import com.obs.domain.PostJob;
import com.obs.domain.PostJobQuestion;
import com.obs.domain.DefaultPostJobQuestion;
import com.obs.domain.ReferTrewlink;
import com.obs.domain.SkillsData;
import com.obs.domain.Subscriptionuser;
import com.obs.domain.TokenEarned;
import com.obs.domain.User;
import com.obs.domain.UserSecurity;
import com.obs.domain.UserType;
import com.obs.email.EmailManager;
import com.obs.messages.MessageConstants;
import com.obs.messages.PropertyConstants;
import com.obs.messages.ResponseListMessage;
import com.obs.messages.ResponseListPagination;
import com.obs.messages.ResponseMessage;
import com.obs.messages.ResponseMessageWithToken;
import com.obs.messages.ResponseStatus;
import com.obs.messages.ResponseStatusCode;
import com.obs.repository.JobReferRepository;
import com.obs.repository.NotificationRepository;
import com.obs.repository.DefaultPostJobQuestionRepository;
import com.obs.repository.ReferTrewlinkRepository;
import com.obs.repository.SkillsDataRepository;
import com.obs.repository.TokenEarnedRepository;
import com.obs.service.Employerservice;
import com.obs.service.IJobseekerservice;
import com.obs.service.IUserservice;
import com.obs.util.CommonProperties;
import com.obs.util.CommonUtils;
import com.obs.validation.BasicValidation;
import com.obs.validation.UserValidation;
import com.obs.view.JobseekerPathwayView;

@PropertySources(value = { @PropertySource("classpath:application-msg.properties" ),@PropertySource("classpath:ValidationMessage/userValidation-msg.properties")})
@Service
public class LoginServiceHelper {

	@Autowired
	private Environment env;

	@Autowired
	UserValidation userValidation;

	@Autowired
	BasicValidation basicValidation;

	@Autowired
	IUserservice userservice;

	@Autowired
	PasswordEncoder passwordencoder;

	@Autowired
	Employerservice employerService;

	@Autowired
	CommonUtils commonUtils;

	@Autowired
	IJobseekerservice jobseekerservice;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	ReferTrewlinkRepository referTrewlinkRepository;
	
	@Autowired
	TokenEarnedRepository tokenEarnedRepository;
	
	@Autowired
	NotificationRepository notificationRepository;
	
	@Autowired
	JobReferRepository jobReferRepository;
	
	@Autowired
	SkillsDataRepository skillsDataRepository;
	
	@Autowired
	DefaultPostJobQuestionRepository postJobQuestionRepository;
	

	private static final Logger logger = LogManager.getLogger(LoginServiceHelper.class);

	private static final String PASS ="password";

	private static final String ERROR ="error";
	private static final String ERRORDESCRIPTION ="error_description";
	private static final String ERRORCODE = "error_code";
	private static final String INVALID = "Invalid";
	private static final String INTERNALERROR = "InternalError";
	private static final String NORECORD ="norecord";
	private static final String USEREXISTS = "userexists";
	private static final String CHECKYOURMAIL = "checkYourMail";

	public ResponseMessage<User> registerUser(UserInput userObj, HttpServletResponse res) {
		ResponseStatus status = null;
		User user = null;
		String validation = null;
		try
		{
			System.out.println("inside register user---->");
			validation = userValidation.registrationValidation(userObj);
			if(basicValidation.checkStringnullandempty(validation) && validation.equalsIgnoreCase(MessageConstants.SUCCESS))
			{
				user = checkUserExistence(userObj) ? saveUser(userObj):null;
				if(user != null)
				{
					status = new ResponseStatus(ResponseStatusCode.STATUS_OK, MessageConstants.SUCCESS);
				}
				else
				{
					status = new ResponseStatus(ResponseStatusCode.STATUS_ALREADY_EXISTS,userObj.getEmail()+" "+env.getProperty(USEREXISTS));
					return new ResponseMessage<>(status, null);
				}
			}
			else
			{
				status = new ResponseStatus(ResponseStatusCode.STATUS_REQUIRED, validation);
				res.setStatus(ResponseStatusCode.STATUS_REQUIRED);
			}
		}
		catch(Exception e)
		{
			logger.error(e);
		}
		return new ResponseMessage<>(status, user);
	}

	public ResponseMessageWithToken<Employer> registerEmployer(UserInput userObj, HttpServletResponse res) {
		ResponseStatus status = null;
		Employer employer = null;
		String validation = null;	
		User user = null;
		JSONObject login = null;
		try
		{
			validation = userValidation.registrationValidation(userObj);
			if(basicValidation.checkStringnullandempty(validation) && validation.equalsIgnoreCase(MessageConstants.SUCCESS))
			{
				user = checkUserExistence(userObj) ? saveUser(userObj):null;
				if(user != null)
				{
					Employer emp = new Employer(userObj.getFirstName(), userObj.getLastName(), userObj.getEmployerName(),userObj.isMarketing());
					emp.setUserInfo(user);
					emp.setLocation(userObj.getLocation());					
					
					EmployerData employerData = new EmployerData();
					employerData.setId(userObj.getEmployerDataId());
					emp.setEmployerData(employerData);
					
					employer = employerService.saveOrUpdateEmployer(emp);
					 
					login = accessToken(user.getUserId(), userObj.getPassword());
					status = new ResponseStatus(ResponseStatusCode.STATUS_OK, MessageConstants.SUCCESS);
				}
				else
				{
					status = new ResponseStatus(ResponseStatusCode.STATUS_ALREADY_EXISTS,userObj.getEmail()+" "+env.getProperty(USEREXISTS));
					return new ResponseMessageWithToken<>(status, null);
				}

			}
			else
			{
				status = new ResponseStatus(ResponseStatusCode.STATUS_REQUIRED, validation);
				res.setStatus(ResponseStatusCode.STATUS_REQUIRED);
			}
		}
		catch(Exception e)
		{
			logger.error(e);
		}
		return new ResponseMessageWithToken<>(status, employer,login);
	}

	private boolean checkUserExistence(UserInput userObj)
	{
		User user = userservice.getUserByUserNameOrEmailorMobile(userObj.getEmail());
		
		if(user != null && user.getAccountstatus().getId() == 1) {
			sendResendVerificationEmail(user);
		}
		
		return user == null;
	}

	private User saveUser(UserInput userObj) {
		User user = null;
		try
		{

			user = new User(userObj.getEmail(), userObj.getCountryCode(), userObj.getPhone(),commonUtils.generateUserName(userObj.getEmail()));
			user.setCreatedOn(new Date());
			user.setFacebookId(userObj.getFacebookId());
			user.setGoogleId(userObj.getGoogleId());
			if(userObj.getUserType() == 2 || userObj.getUserType() == 3)
			{
				String verifyLink = commonUtils.randomReferenceString(6)+"_"+user.getUserName().substring(0, 3);
				int channel=1;
				String verificationPage =  CommonProperties.getUiPath()+CommonProperties.getUiDomain()+"?verfyLink="+verifyLink+"&channel="+channel+"&userType="+userObj.getUserType();
				user.setVerifyLink(verifyLink);
				user.setLinkcreatedon(new Date());
				String msg =userObj.getUserType() == 2 ?"Thank you for registering with us.Soon you will be able to post jobs and receive quality applications from talented doctors.":env.getProperty("jobseekerVerficationMsg");
				EmailManager.userVerificationMail(user.getEmail(),msg, userObj.getFirstName() != null && !userObj.getFirstName().isEmpty()?commonUtils.toTitleCase(userObj.getFirstName()):user.getUserName(),"", verificationPage);
				userObj.setPassword(userObj.getPassword());
			}

			UserType uty = new UserType();
			uty.setUserTypeId(userObj.getUserType());
			AccountStatus acs = new AccountStatus();
			acs.setId((long)1);
			user.setUserType(uty);
			user.setAccountstatus(acs);
			user.setActive(true);
			user.setNewUser(true);
			String firstname = userObj.getFirstName() != null && !userObj.getFirstName().isEmpty() ? userObj.getFirstName() :  " "; 
			String lastname = userObj.getLastName() != null && !userObj.getLastName().isEmpty() ? userObj.getLastName() :  " "; 
			user.setFullName(firstname+" "+lastname);
			user = userservice.saveOrUpdateUser(user);
			user = saveUserSecurity(user, userObj) != null? user:null;
			user = user != null? userservice.getUserById(user.getUserId()):null;
			
			setAdminInAppNotificationRegister(user);
		}
		catch(Exception e)
		{
			logger.error(e);
		}
		return user;
	}

	private void setAdminInAppNotificationRegister(User user) {		
		List<User> lstAdminUser = userservice.getUsersByType(1L);
		
		for (User adminUser : lstAdminUser) {		
				
		String senderId = user.getUserId();
		String senderName = user.getFullName();
		String receiverId = (adminUser != null) ? adminUser.getUserId() : null;
		String receiverName = (adminUser != null && adminUser.getFullName() != null) ? adminUser.getFullName() : null;
		String usetType ="Jobseeker";
		if(user.getUserType().getUserTypeId() == 2) {
		 usetType = "Employer";
		}
		String description = senderName+ " has registered as a "+usetType ;
		String hintDescription = "Register(admin)";
				
		setInAppNotification(senderId, senderName, receiverId, receiverName, description, hintDescription, 0, null);
		}
	}

	private UserSecurity saveUserSecurity(User user,UserInput userObj) {
		UserSecurity userSecurity = null;
		try {
			userSecurity = new UserSecurity();
			userSecurity.setUser(user);
			userSecurity.setPassword(passwordencoder.encode(basicValidation.checkStringnullandempty(userObj.getPassword())?userObj.getPassword():user.getUserName()));
			if(basicValidation.checkStringnullandempty(userObj.getFacebookId()))
			{
				userSecurity.setSocialPassword(passwordencoder.encode(userObj.getFacebookId()));
				userSecurity.setSocial(true);
			}
			if(basicValidation.checkStringnullandempty(userObj.getGoogleId()))
			{
				userSecurity.setSocialPassword(passwordencoder.encode(userObj.getGoogleId()));
				userSecurity.setSocial(true);
			}
			userSecurity = userservice.saveOrUpdateUserSecurity(userSecurity);
		} catch (Exception e) {
			logger.error(e);
			userservice.deleteUser(user);
		}
		return userSecurity;
	}

	public ResponseMessage<Subscriptionuser> subscribeUser(UserInput userObj, HttpServletResponse response) {
		ResponseStatus status = null;
		Subscriptionuser subscriptionuser = null;
		try
		{
			if(basicValidation.checkStringnullandempty(userObj.getEmail()) && userObj.getUserType() > 0)
			{
				subscriptionuser = userservice.getSubscriptionuserByEmailAndUserType(userObj.getEmail(), userObj.getUserType());
				if(subscriptionuser == null)
				{
					subscriptionuser = new Subscriptionuser();
					subscriptionuser.setEmail(userObj.getEmail());
					UserType ut = new UserType();
					ut.setUserTypeId(userObj.getUserType());
					subscriptionuser.setUserType(ut);
					subscriptionuser.setLocation(userObj.getLocation());
					subscriptionuser.setRollName(getRollName(userObj.getUserType(),userObj.isMentor()));
					subscriptionuser = userservice.saveOrUpdateSubscriptionuser(subscriptionuser);
					status = subscriptionuser != null ? new ResponseStatus(ResponseStatusCode.STATUS_OK, MessageConstants.SUCCESS):new ResponseStatus(ResponseStatusCode.STATUS_NOTEXIST,"SomeThing went wrong");
					EmailManager.genertesubscribeUserMail(subscriptionuser);
				}
				else
				{
					response.setStatus(ResponseStatusCode.STATUS_ALREADY_EXISTS);
					status=new ResponseStatus(ResponseStatusCode.STATUS_ALREADY_EXISTS,userObj.getEmail()+" "+env.getProperty("alreadysubscribed"));
					return new ResponseMessage<>(status, null);
				}
			}
			else
			{
				status = new ResponseStatus(ResponseStatusCode.STATUS_REQUIRED, env.getProperty("datarequired"));
				response.setStatus(ResponseStatusCode.STATUS_REQUIRED);
			}
		}
		catch(Exception e)
		{
			logger.error(e);
		}
		return new ResponseMessage<>(status, subscriptionuser);
	}

	private String getRollName(long userType, boolean isMentor) {
		if(isMentor) {
			return MessageConstants.MENTOR;
		}
		else if(userType == 2) {
			return MessageConstants.EMPLOYER;
		}
		else {
			return MessageConstants.JOBSEEKER;
		}
		
	}

	public ResponseMessage<User> verifyUserByVerificationLink(UserInput userObj, HttpServletResponse response) {
		ResponseStatus status = null;
		User user=null;
		AccountStatus accStatus = new AccountStatus();
		try
		{
			user= (userObj.getVerifyLink()!=null && !userObj.getVerifyLink().isEmpty())?userservice.getUserByVerifyLink(userObj.getVerifyLink()):null;
			if(user!=null)
			{
				Date currentDate = new Date();
				Date dateFromDatabase = user.getLinkcreatedon();// get the date from the database
				long duration = currentDate.getTime() - dateFromDatabase.getTime();

				if (userObj.getPassword() != null && !userObj.getPassword().isEmpty()) {
					long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
					
					if ((diffInMinutes >= 15)) {
						
						status = new ResponseStatus(ResponseStatusCode.STATUS_GONE, "Link expired");
						response.setStatus(ResponseStatusCode.STATUS_GONE);
						return new ResponseMessage<>(status, null);
					}
				} else if (userObj.getPassword() == null ) {
				
					long diffInHours = TimeUnit.MILLISECONDS.toHours(duration);
					
					if ((diffInHours >= 12)) {
						
						status = new ResponseStatus(ResponseStatusCode.STATUS_GONE, "Link expired");
						response.setStatus(ResponseStatusCode.STATUS_GONE);
						return new ResponseMessage<>(status, null);
					}
				}
				
				UserSecurity usc = userservice.getUserSecurityByUserName(user.getUserId());
				usc.setPassword(basicValidation.checkStringnullandempty(userObj.getPassword())?passwordencoder.encode(userObj.getPassword()):usc.getPassword());
				userservice.saveOrUpdateUserSecurity(usc);
				accStatus.setId((long)2);
				user.setAccountstatus(accStatus);
				userservice.saveOrUpdateUser(user);
				user = userservice.getUserById(user.getUserId());
				status=new ResponseStatus(ResponseStatusCode.STATUS_OK,MessageConstants.SUCCESS);
				response.setStatus(ResponseStatusCode.STATUS_OK); 
			}
			else
			{
				status=new ResponseStatus(ResponseStatusCode.STATUS_NORECORD,env.getProperty(NORECORD));
				response.setStatus(ResponseStatusCode.STATUS_NORECORD); 
				return new ResponseMessage<>(status,null);
			}
		}
		catch(Exception e)
		{
			logger.error(e);
			status = new ResponseStatus(ResponseStatusCode.STATUS_INTERNAL_ERROR,env.getProperty(INTERNALERROR));
			response.setStatus(ResponseStatusCode.STATUS_INTERNAL_ERROR); 
			return new ResponseMessage<>(status,null);
		}
		return new ResponseMessage<>(status,user);
	}

	public ResponseMessage<User> getUserByVerificationLink(UserInput userObj,HttpServletResponse response) {
		ResponseStatus status = null;
		User user=null;
		try
		{
			if(userObj.getVerifyLink()!=null)
			{
				user=userservice.getUserByVerifyLink(userObj.getVerifyLink());
			}
			if(user!=null)
			{				
				status=new ResponseStatus(ResponseStatusCode.STATUS_OK,MessageConstants.SUCCESS);
				response.setStatus(ResponseStatusCode.STATUS_OK); 
			}
			else
			{
				status=new ResponseStatus(ResponseStatusCode.STATUS_NORECORD,env.getProperty(NORECORD));
				response.setStatus(ResponseStatusCode.STATUS_NORECORD); 
				return new ResponseMessage<>(status,null);
			}
		}
		catch(Exception e)
		{
			logger.error(e);
			status = new ResponseStatus(ResponseStatusCode.STATUS_INTERNAL_ERROR,env.getProperty(INTERNALERROR));
			response.setStatus(ResponseStatusCode.STATUS_INTERNAL_ERROR); 
			return new ResponseMessage<>(status,null);
		}
		return new ResponseMessage<>(status,user);
	}

	public ResponseMessage<User> forgotPassword(UserInput userObj,final HttpServletResponse response){

		ResponseStatus status = null;
		User user      = null;
		response.setHeader("Cache-Control", "no-cache");
		int channel = 2;
		String firstName = null;
		try {
			user = userservice.getUserByUserNameOrEmailorMobile(userObj.getEmail());

			if(user != null){
				if(user.getUserType() != null) {
					if(user.getUserType().getUserTypeId() == 2 )
					{
						Employer emp = employerService.getEmployerByUserId(user.getUserId());
						firstName = emp != null?emp.getFirstName():null;
					}
					if(user.getUserType().getUserTypeId() == 3)
					{
						Jobseeker jsk = jobseekerservice.getJobseekerbyUserId(user.getUserId());
						firstName = jsk != null?jsk.getFirstName():null;
					}
				}
				String verifyLink = commonUtils.randomReferenceString(6)+"_"+user.getUserName().substring(0, 3);
				String verificationPage =  CommonProperties.getUiPath()+CommonProperties.getUiDomain()+"?verfyLink="+verifyLink+"&channel="+channel+"&userType="+userObj.getUserType();
				user.setVerifyLink(verifyLink);
				user.setLinkcreatedon(new Date());
				String msg = "Hopefully you requested a new password for trewlink account. No changes have been made to your account yet.";
				EmailManager.forgotPasswordMail(user.getEmail(),msg, firstName != null?firstName:user.getUserName(),"", verificationPage);
				userservice.saveOrUpdateUser(user);
				status=new ResponseStatus(ResponseStatusCode.STATUS_OK,MessageConstants.SUCCESS);
				response.setStatus(ResponseStatusCode.STATUS_OK);
			}
			else{
				status=new ResponseStatus(ResponseStatusCode.STATUS_NORECORD,env.getProperty(NORECORD));

			}    	

		} catch (Exception e) {
			status = new ResponseStatus(ResponseStatusCode.STATUS_INTERNAL_ERROR,env.getProperty(INTERNALERROR));
			response.setStatus(ResponseStatusCode.STATUS_INTERNAL_ERROR); 
			logger.error(e);
		}

		return new ResponseMessage<>(status, user);
	}


	@SuppressWarnings({ "unchecked", "static-access", "unused" })
	public JSONObject doLogin(String username, String password,int type, HttpServletRequest request, HttpServletResponse res)
	{
		logger.info(">>>>>doLogin>>>>>>>>>>>>>>>"+username+password);
		User user = userservice.getUserByUserNameOrEmailorMobile(username);
		if(username == null || username.isEmpty() || password == null || password.isEmpty())
		{
			res.setStatus(ResponseStatusCode.STATUS_INVALID);
			return createErrorJSON("Required ", (username == null || username.isEmpty())?"username is required":"password is required", 1);
		}
		if(user != null)
		{	
			UserSecurity usc = userservice.getUserSecurityByUserName(user.getUserId());	
			JSONObject json = accountValiadtion(usc, password,res);
			if(json!= null)
			{
				return json;
			}

		}
		else
		{
			res.setStatus(ResponseStatusCode.STATUS_INVALID);
			return createErrorJSON(INVALID,env.getProperty("error.noUser"), 1);
		}
		logger.info(request.getLocalAddr());
		return accessToken(user.getUserId(),password);

	}

	private JSONObject accountValiadtion(UserSecurity usc,String password, HttpServletResponse res)
	{
		JSONObject json = null;
		User user = usc.getUser();
		if(!user.isActive())
		{
			res.setStatus(ResponseStatusCode.STATUS_INVALID);
			return createErrorJSON(INVALID, env.getProperty("error.inactive"), 4);
		}
		if(user.getAccountstatus().getId() == 1)
		{
			sendResendVerificationEmail(user);
			res.setStatus(ResponseStatusCode.STATUS_UNAUTHORIZED);
			return createErrorJSON(INVALID, env.getProperty("error.unverifiedUser")+" "+env.getProperty(CHECKYOURMAIL), 3);
		}
		if(user.isLocked())
		{
			Instant then = user.getLockedOn().toInstant();
			Instant now = Instant.now();
			Instant thirtyMin = now.minus(30, ChronoUnit.MINUTES);
			boolean check = then.isBefore(thirtyMin);
			if(!check)
			{ 
				res.setStatus(ResponseStatusCode.STATUS_INVALID);
				return createErrorJSON(env.getProperty("error.accountlocked"), env.getProperty("error.exceedAttemps"), 2);
			}
			else
			{
				user.setLocked(false);
				user.setInvalidAttempts(0);
				userservice.saveOrUpdateUser(user);
			}
		}
		if(!passwordencoder.matches(password, usc.getPassword()) && !usc.isSocial()) {
			user.setInvalidAttempts(user.getInvalidAttempts()+1);
			if(user.getInvalidAttempts()>6 && !user.isLocked())
			{
				user.setLockedOn(new Date());
				user.setLocked(true);
			}
			userservice.saveOrUpdateUser(user);
			res.setStatus(ResponseStatusCode.STATUS_INVALID);
			return createErrorJSON(INVALID, env.getProperty("error.invalidCredentials"), 1);
		}

		return json;

	}
	

	private String getDataString(HashMap<String, String> params) throws UnsupportedEncodingException{
		StringBuilder result = new StringBuilder();
		boolean first = true;
		for(Map.Entry<String, String> entry : params.entrySet()){
			if (first)
				first = false;
			else
				result.append("&");    
			result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
			result.append("=");
			result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
		}    
		return result.toString();
	}

	private JSONObject accessToken(String username,String password)
	{
		String baseUrl = CommonProperties.getBaseURL() + CommonProperties.getContextPath() + MessageConstants.OAUTH;
		System.out.println("base url----->"+baseUrl);
		HashMap<String,String> input = new HashMap<>();
		User user = null;
		UserSecurity usc = userservice.getUserSecurityByUserName(username);
		System.out.println("usc------->"+usc);
		JSONObject json1 = new JSONObject();
		try {
			user = usc.getUser();
			String socialPwd  = user.getFacebookId() != null && !user.getFacebookId().isEmpty()?user.getFacebookId() :user.getGoogleId();
			input.put(PASS, usc.isSocial()?socialPwd:password);
			input.put("grant_type",PASS);
			input.put("username",username);
			String body = getDataString(input);
			System.out.println("body------->"+body);
			// create headers
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/x-www-form-urlencoded");
			headers.setBasicAuth(MessageConstants.OAUTHTC, MessageConstants.OAUTHTC);
			System.out.println("headers------->"+headers);
			// build the request
			HttpEntity<String> entity = new HttpEntity<>(body, headers);
			// send POST request
			ResponseEntity<JSONObject> response = restTemplate.postForEntity(baseUrl, entity, JSONObject.class);
			System.out.println("response---->"+response);
			json1 = response.getBody();

			if(response.getStatusCodeValue() == 200)
			{				
				user.setInvalidAttempts(0);
				userservice.saveOrUpdateUser(user);
				json1.put("newUser", user.isNewUser());
				if(user.getUserType().getUserTypeId()== 3 && user.getCompletedStep() < 2)
				{
		 			json1.put("step", user.getCompletedStep());
				}
				if(user.getUserType().getUserTypeId()== 2 && user.getCompletedStep() < 1)
				{
					json1.put("step", user.getCompletedStep());
				}
			}
		} catch (UnsupportedEncodingException e) {
			logger.error(e);}
		System.out.println("json1--------->"+json1);
		return json1;
	}

	@SuppressWarnings("unchecked")
	private JSONObject createErrorJSON(String error,String errDescription,int errorCode)
	{
		JSONObject json = new JSONObject();
		json.put(ERROR, error);
		json.put(ERRORDESCRIPTION, errDescription);
		json.put(ERRORCODE, errorCode);
		return json;
	}
	public ResponseMessageWithToken<Jobseeker> registerJobseeker(UserInput userObj, HttpServletResponse res) {
		ResponseStatus status = null;
		Jobseeker jobseeker = null;
		String validation = null;	
		User user = null;
		JSONObject login = null;
		try
		{
			validation = userValidation.registrationJobseekerValidation(userObj);

			if(basicValidation.checkStringnullandempty(validation) && validation.equalsIgnoreCase(MessageConstants.SUCCESS))
			{
				user = checkUserExistence(userObj) ? saveUser(userObj):null;
				if(user != null)
				{
					jobseeker = saveJobSeeker(userObj, user);
					login = accessToken(user.getUserId(), userObj.getPassword());
					logger.info(login);
					status = new ResponseStatus(ResponseStatusCode.STATUS_OK, MessageConstants.SUCCESS);
				}
				else
				{
					
					status = new ResponseStatus(ResponseStatusCode.STATUS_ALREADY_EXISTS,userObj.getEmail()+" "+env.getProperty(USEREXISTS)+" "+env.getProperty(CHECKYOURMAIL));
					return new ResponseMessageWithToken<>(status, null);
				}

			}
			else
			{
				status = new ResponseStatus(ResponseStatusCode.STATUS_REQUIRED, validation);
				res.setStatus(ResponseStatusCode.STATUS_REQUIRED);
			}
		}
		catch(Exception e)
		{
			logger.error(e);
		}
		return new ResponseMessageWithToken<>(status, jobseeker, login);
	}

	private Jobseeker saveJobSeeker(UserInput userObj,User user)
	{
		Jobseeker jobseeker = null;
		try
		{
			Jobseeker jsr = new Jobseeker(userObj.getFirstName(), userObj.getLastName(),userObj.getOccupation(),user);
			Country nationality = new Country();
			nationality.setId(userObj.getNationalityId());
			jsr.setNationality(nationality);
			jobseeker = jobseekerservice.saveOrUpdateJobseeker(jsr);
			

		}
		catch (Exception e) {
			logger.error(e);		}
		return jobseeker;
	}

	public ResponseMessage<PostJob> registerPostJob(PostJobInput postJobObj,HttpServletRequest request, HttpServletResponse res) {
		ResponseStatus status = null;
		PostJob postJob = null;
		User user = null;
		try
		{
			user = commonUtils.getUser(request);
			Employer emp = postJobObj.getEmployerId() != null && !postJobObj.getEmployerId().isEmpty()? employerService.getEmployerById(postJobObj.getEmployerId()):null;
			emp  = (emp != null) ? emp: commonUtils.getEmployer(request);
			
			if(emp != null)
			{
				if(!user.getUserId().equalsIgnoreCase(emp.getUserInfo().getUserId()))
				{
					status = new ResponseStatus(ResponseStatusCode.STATUS_UNAUTHORIZED, "Unauthorized employer");
					return new ResponseMessage<>(status, null);
				}
				PostJob job = new PostJob(postJobObj.getName(), postJobObj.getDescription(), postJobObj.getSkillsRequired(), postJobObj.getDepartment());
				job.setPathway(postJobObj.getPathway());
				job.setEmployer(emp);
				job.setJobType(postJobObj.getJobType());
				job.setSalary(postJobObj.getSalary());
				job.setFee(postJobObj.getSalary() > 0 ? 10%postJobObj.getSalary() : 0);
				job.setSalaryMin(postJobObj.getSalaryMin());
				job.setSalaryMax(postJobObj.getSalaryMax());
				job.setActive(true);
				job.setQuestions(postJobObj.getQuestions());
				postJob = employerService.saveOrUpdatePostJob(job);
				user.setCompletedStep(1);
				userservice.saveOrUpdateUser(user);
				setInAppNotificationPostJob(postJob);
				
				saveNewSkillsToDb(postJobObj.getSkillsRequired());
				
				if(user.isNewUser()) {
				String jobLink1 =  CommonProperties.getUiPath()+"employer/jobs";
				String askQuestionLink2 =  CommonProperties.getUiPath()+"progress";						
				EmailManager.generteRegistrationMail(user, " ",  jobLink1, askQuestionLink2, "", "", 2);
				}
				
				status = new ResponseStatus(ResponseStatusCode.STATUS_OK, MessageConstants.SUCCESS);
			}
			else
			{
				status = new ResponseStatus(ResponseStatusCode.STATUS_NORECORD,env.getProperty(NORECORD));
				res.setStatus(ResponseStatusCode.STATUS_NORECORD);
			}
		}
		catch(Exception e)
		{
			logger.error(e);
		}
		return new ResponseMessage<>(status, postJob);
	}
	
	public ResponseListMessage<PostJobQuestion> getPostJobQuestionById(PostJobInput postJobObj,HttpServletRequest request, HttpServletResponse res)
	{
		List<PostJobQuestion> postJobQuestion = null;
		ResponseStatus status = null;
		try {
			if(basicValidation.checkStringnullandempty(postJobObj.getPostId())) {
				PostJob job = employerService.getPostJobById(postJobObj.getPostId());
				if(job != null){
					postJobQuestion = job.getQuestions();
					status = new ResponseStatus(ResponseStatusCode.STATUS_OK, MessageConstants.SUCCESS);
				}else{
					status = new ResponseStatus(ResponseStatusCode.STATUS_NORECORD,env.getProperty(NORECORD));
					res.setStatus(ResponseStatusCode.STATUS_NORECORD);
				}
			}else{
				status = new ResponseStatus(ResponseStatusCode.STATUS_REQUIRED, "post job id required");
				res.setStatus(ResponseStatusCode.STATUS_REQUIRED);
			}
			
		}catch(Exception e){
			logger.info(e);
		}
		return new ResponseListMessage<>(status , postJobQuestion);
	}
	
	public ResponseListMessage<DefaultPostJobQuestion> getPostJobQuestion ( HttpServletRequest request, HttpServletResponse res)
	{
		ResponseStatus status = null;
		List<DefaultPostJobQuestion> postJobQuestion = null;
	
		try{
			
			postJobQuestion = employerService.getPostJobQuestion();
			if(postJobQuestion != null){
				status = new ResponseStatus(ResponseStatusCode.STATUS_OK, MessageConstants.SUCCESS);
			}else{
				status = new ResponseStatus(ResponseStatusCode.STATUS_NORECORD,env.getProperty(NORECORD));
				res.setStatus(ResponseStatusCode.STATUS_NORECORD);
			}
			
		}catch(Exception e)
		{
			logger.info(e);
		}
		
		return new ResponseListMessage<>(status,  postJobQuestion);
	}
	
	
	public ResponseStatus saveOrUpdatePostJobQuestion(String question, String id, HttpServletRequest request, HttpServletResponse res){
		ResponseStatus status = null;
		DefaultPostJobQuestion postJobQuestion = null;
		
		try {
			postJobQuestion = new DefaultPostJobQuestion();
			postJobQuestion.setQuestion(question);
			postJobQuestion = postJobQuestionRepository.save(postJobQuestion);
			status = new ResponseStatus(ResponseStatusCode.STATUS_OK, MessageConstants.SUCCESS);
			res.setStatus(ResponseStatusCode.STATUS_OK);	
			
		}catch(Exception e) {
			logger.info(e);
		}
		return status;
	}
	

	private void saveNewSkillsToDb(String skillsRequired) {
		try {
			
			Thread thread = new Thread(() -> {				
			
					SkillsData skillsData = null;
					String[] skillsSplit = skillsRequired.split(",");
					for (String singleSkill : skillsSplit) {
						
						skillsData = employerService.getSkillDataByName(singleSkill);
						if(skillsData == null) {
							skillsData = new SkillsData();
							skillsData.setSkill(singleSkill);
							skillsDataRepository.save(skillsData);
						}

					}					
			});
			thread.start();
			
		} catch (Exception e) {
			logger.error(e);
		}
		
	}

	private void setInAppNotificationPostJob(PostJob postJob) {		
		String senderId = postJob.getEmployer().getUserInfo().getUserId();
		String senderName = postJob.getEmployer().getEmployerName();
		String description = senderName+" has posted a job which matches one of your pathways. View it here: ";
		String hintDescription = "Post a job";	
		String postId = postJob.getPostId();
		try {			
			List<JobseekerPathwayView> lstJobseeker = jobseekerservice.getJobseekerByPathwayName(postJob.getPathway());
			if (lstJobseeker != null) {
				for (JobseekerPathwayView jobseeker : lstJobseeker) {
					String receiverId = jobseeker.getUserId();
					String receiverName = jobseeker.getJobseekerName();
					setInAppNotification(senderId, senderName, receiverId, receiverName, description, hintDescription, 11, postId);
					
					String jobLink =  CommonProperties.getUiPath()+"employee/jobs/"+postJob.getPostId();
					EmailManager.genertePostJobEmail(jobseeker, postJob, jobLink);
				}
			}	
			
		} catch (Exception e) {
			logger.error(e);
		}				
		
	}

	private void setInAppNotification(String senderId, String senderName, String receiverId, String receiverName,
			String description, String hintDescription, int notificationType, String postId) {
		try {
			if(senderId != null && !senderId.equalsIgnoreCase(receiverId)) {
			Notification notification = new Notification();
			notification.setSenderId(senderId );
			notification.setSenderName(senderName != null ? senderName : null);
			
			notification.setReceiverId(receiverId != null ? receiverId : null);
			notification.setRecieverName(receiverName != null ? receiverName : null);
			
			notification.setHintDescription(hintDescription);
			notification.setDescription(description);
			
			notification.setNotificationType(notificationType);
			notification.setPostId(postId);
			
			notificationRepository.save(notification);
			
			}else {
				logger.info("same id");
			}
			
		} catch (Exception e) {
			logger.error(e);
		}
		
	}

	private ReferTrewlink saveReferTrewlink(UserInput userobj,Jobseeker jobseeker)
	{
		ReferTrewlink rft = new ReferTrewlink();
		rft.setReferType(1);
		rft.setRefer(userobj.getRefer());
		rft.setJobseeker(jobseeker);
		rft = referTrewlinkRepository.save(rft);
		return rft;

	}

	public ResponseMessage<Jobseeker> setPathway(UserInput userObj,HttpServletRequest request, HttpServletResponse res) {
		ResponseStatus status = null;
		Jobseeker jobseeker  = null;
		User user = null;
		try
		{
			user = commonUtils.getUser(request);
			System.out.println("first call---->");
			if(user.getAccountstatus().getId() == 1)
			{
				status = new ResponseStatus(ResponseStatusCode.STATUS_INVALID, "Verify email address");
				return new ResponseMessage<>(status, null);
			}
			System.out.println("second call--->");
			jobseeker = jobseekerservice.getJobseekerbyUserId(user.getUserId());
			System.out.println("third call---->");
			if(jobseeker != null)
			{
				System.out.println("fourth call---->");
				jobseeker.setPathway(userObj.getPathway());
				jobseeker.setTraining(userObj.isTraining());
				System.out.println("fivth call ----->");
				jobseeker = jobseekerservice.saveOrUpdateJobseeker(jobseeker);
				user.setCompletedStep(1);
				System.out.println("sixthcall ---->");
				userservice.saveOrUpdateUser(user);
				System.err.println("seventh call ---->");
				if(userObj.getRefer() != null && !userObj.getRefer().isEmpty())
				{					
					System.out.println("eight call ---->");
					saveReferTrewlink(userObj, jobseeker);
					addTokenByNetwork(userObj, jobseeker);					
				}
				System.out.println("ninth call --->");
				setInAppNotificationRegister(userObj, jobseeker);
				System.out.println("tenth call- -->");
				updateJonseekerIdInRefferJobUser(jobseeker);
				System.out.println("eleventh call --->");
				updateJonseekerIdInTokenEarnedUser(jobseeker);
				System.out.println("twelth call");
				status = new ResponseStatus(ResponseStatusCode.STATUS_OK, MessageConstants.SUCCESS);
			}
			else
			{
				status = new ResponseStatus(ResponseStatusCode.STATUS_NORECORD, env.getProperty(NORECORD));
				res.setStatus(ResponseStatusCode.STATUS_NORECORD);
			}
		}
		catch(Exception e)
		{
			logger.error(e);
		}
		return new ResponseMessage<>(status, jobseeker);
	}

	private void updateJonseekerIdInTokenEarnedUser(Jobseeker jobseeker) {
		try {
			List<TokenEarned> lstTokenEarned = jobseekerservice.getTokenEarnByEmail(jobseeker.getUserInfo().getEmail());
			if(lstTokenEarned != null) {
				for (TokenEarned tokenEarn : lstTokenEarned) {
					if(tokenEarn.getJobSeekerEmail() != null && !tokenEarn.getJobSeekerEmail().isEmpty() && tokenEarn.getUser() == null) {
						tokenEarn.setJobseekerId(jobseeker.getJobseekerId());
						tokenEarn.setUser(jobseeker.getUserInfo());
						tokenEarnedRepository.save(tokenEarn);
					}
				}
			}
			
		} catch (Exception e) {
			logger.error(e);
		}
		
	}
		
	

	private void updateJonseekerIdInRefferJobUser(Jobseeker jobseeker) {
		try {
			List<JobRefer> jobRefer = jobseekerservice.getJobreferByRefferEmail(jobseeker.getUserInfo().getEmail());
			if(jobRefer != null) {
				for (JobRefer referObj : jobRefer) {
					if(referObj.getReferEmail() != null && !referObj.getReferEmail().isEmpty() && referObj.getReferedjobseeker() == null) {
					referObj.setReferedjobseeker(jobseeker);
					jobReferRepository.save(referObj);
					}
				}
			}
			
		} catch (Exception e) {
			logger.error(e);
		}
		
	}

	private void setInAppNotificationRegister(UserInput userObj, Jobseeker jobseeker) {
		User adminUser = userservice.getUserByType(1L);
		
		
		String senderId = (adminUser != null) ? adminUser.getUserId() : null;
		String senderName = (adminUser != null && adminUser.getFullName() != null) ? adminUser.getFullName() : null;
		String description = jobseeker.getUserInfo().getFullName()+"  has just joined and has interest in your chosen pathway of "+userObj.getPathway().get(0).getCurrentPath()+". Say Hi and welcome them to the community.";
		String hintDescription = "New User Joined";	
		try {	
			/* Send In App notyification to list of jobseeker related to pathway */
			List<JobseekerPathwayView> lstJobseeker = jobseekerservice.getJobseekerByPathwayName(userObj.getPathway().get(0).getCurrentPath());
			if (lstJobseeker != null) {
				for (JobseekerPathwayView jobseekerView : lstJobseeker) {
					
					String receiverId = jobseekerView.getUserId();
					String receiverName = jobseekerView.getJobseekerName();
					
					if(!jobseeker.getJobseekerId().equalsIgnoreCase(jobseekerView.getJobseekerId())) {
					setInAppNotification(senderId, senderName, receiverId, receiverName, description, hintDescription, 3, null);
					}
				}
			}
			
			/* Registraion email to particular jobseker */
			String viewEligiblityScoreLink1 =  CommonProperties.getUiPath()+"employee/profile";
			String askQuestionLink2 =  CommonProperties.getUiPath()+"progress";
			String storeLink3 =  CommonProperties.getUiPath()+"employee/Rewards";
			String inviteFrndLink4 =  askQuestionLink2;

			 EmailManager.generteRegistrationMail(jobseeker.getUserInfo(),userObj.getPathway().get(0).getCurrentPath(), viewEligiblityScoreLink1, askQuestionLink2, storeLink3, inviteFrndLink4, 1);
			
		} catch (Exception e) {
			logger.error(e);
		}		
		
	}

	private void addTokenByNetwork(UserInput userObj, Jobseeker referrerUser) {
		TokenEarned tokenEarned = null;
		User userEntity = userservice.getUserByUserNameOrEmailorMobile(userObj.getRefer());
		
		if(userEntity != null ) {
			Jobseeker jobseeker = jobseekerservice.getJobseekerbyUserId(userEntity.getUserId());				
			
			tokenEarned = new TokenEarned();
			tokenEarned.setApprove(false);
			tokenEarned.setTokenByNetwork(MessageConstants.TOKENBYNETWORK);
			tokenEarned.setTotalToken(MessageConstants.TOKENBYNETWORK);
			tokenEarned.setType(2);
			tokenEarned.setTokenStatus(MessageConstants.REQUESTED);
			tokenEarned.setUser(userEntity);
			tokenEarned.setJobseekerId(jobseeker.getJobseekerId());
			tokenEarned.setReferrerUser(referrerUser.getUserInfo());
			tokenEarned.setReferrerJobseekerId(referrerUser.getJobseekerId());
			tokenEarned = tokenEarnedRepository.save(tokenEarned);
			
			setAdminInAppNotificationAppInvite(tokenEarned);
		
		}
		
	}

	private void setAdminInAppNotificationAppInvite(TokenEarned tokenEarned) {
		
		List<User> lstAdminUser = userservice.getUsersByType(1L);
		
		for (User adminUser : lstAdminUser) {	

		String senderId = tokenEarned.getReferrerUser().getUserId();
		String senderName = tokenEarned.getReferrerUser().getFullName();
		String receiverId = (adminUser != null) ? adminUser.getUserId() : null;
		String receiverName = (adminUser != null && adminUser.getFullName() != null) ? adminUser.getFullName() : null;
		

		String description = tokenEarned.getUser().getFullName() + " has invited "+senderName+ " to TrewLink";
		String hintDescription = "Invite Trewlink(admin)";

		setInAppNotification(senderId, senderName, receiverId, receiverName, description, hintDescription, 0, null);
		}	
	}

	public ResponseMessage<Jobseeker> setExperience(UserInput userObj,HttpServletRequest request, HttpServletResponse res) {
		ResponseStatus status = null;
		Jobseeker jobseeker  = null;
		User user = null;
		try
		{
			user = commonUtils.getUser(request);
			if(user.getAccountstatus().getId() == 1)
			{
				status = new ResponseStatus(ResponseStatusCode.STATUS_INVALID, "Verify email address");
				return new ResponseMessage<>(status, null);
			}
			jobseeker = jobseekerservice.getJobseekerbyUserId(user.getUserId());
			if(jobseeker != null)
			{
				jobseeker.setExperience(userObj.getExperience());
				jobseeker = jobseekerservice.saveOrUpdateJobseeker(jobseeker); 
				user.setCompletedStep(2);
				userservice.saveOrUpdateUser(user);
				status = new ResponseStatus(ResponseStatusCode.STATUS_OK, MessageConstants.SUCCESS);
			}
			else
			{
				status = new ResponseStatus(ResponseStatusCode.STATUS_NORECORD,env.getProperty(NORECORD));
				res.setStatus(ResponseStatusCode.STATUS_NORECORD);
			}
		}
		catch(Exception e)
		{
			logger.error(e);
		}
		return new ResponseMessage<>(status, jobseeker);
	}

	public ResponseListMessage<Country> getAllCountry( HttpServletResponse res) {
		ResponseStatus status = null;
		List<Country> country = null;
		try
		{
			country = userservice.getAllCountry();
			if(basicValidation.checkListnullandsize(country))
			{
				status = new ResponseStatus(ResponseStatusCode.STATUS_OK, MessageConstants.SUCCESS);
			}
			else
			{
				status = new ResponseStatus(ResponseStatusCode.STATUS_NORECORD, env.getProperty(NORECORD));
				res.setStatus(ResponseStatusCode.STATUS_NORECORD);
			}
		}
		catch(Exception e)
		{
			logger.error(e);
		}
		return new ResponseListMessage<>(status, country);
	}

	public ResponseStatus changePassword(UserInput userObj,HttpServletRequest request,HttpServletResponse res) {
		ResponseStatus status = null;
		User user = commonUtils.getUser(request);
		try
		{
			UserSecurity usc = userservice.getUserSecurityByUserName(user.getUserId());
			String validation = userValidation.changePassword(userObj, usc);
			if(MessageConstants.SUCCESS.equalsIgnoreCase(validation))
			{
				usc.setPassword(passwordencoder.encode(userObj.getPassword()));
				userservice.saveOrUpdateUserSecurity(usc);
				userservice.deleteAccessToken(user.getUserId());
				status = new ResponseStatus(ResponseStatusCode.STATUS_OK, MessageConstants.SUCCESS);
				res.setStatus(ResponseStatusCode.STATUS_OK);
			}
			else
			{
				status = new ResponseStatus(ResponseStatusCode.STATUS_NOTMATCHED,validation);
				res.setStatus(ResponseStatusCode.STATUS_NOTMATCHED);

			}
		}
		catch(Exception e)
		{
			logger.error(e);
		}
		return new ResponseStatus(status);
	}

	public ResponseStatus logout(HttpServletRequest request) {
		ResponseStatus status = null;
		User user = commonUtils.getUser(request);
		if (user.getUserId() != null) {
			userservice.deleteAccessToken(user.getUserId());
			status=new ResponseStatus(ResponseStatusCode.STATUS_OK,"User LoggedOut Successfully");			
		}else{
			status=new ResponseStatus(ResponseStatusCode.STATUS_OK,"Cannot able to logged out");
		}	
		return new ResponseStatus(status);
	}

	public ResponseStatus changeNewUserStatus(UserInput userObj, HttpServletRequest request, HttpServletResponse res) {
		ResponseStatus status = null;
		User user = commonUtils.getUser(request);
		user = (user == null && userObj.getUserId() != null) ? userservice.getUserById(userObj.getUserId())	: user;
		try {
			if (user != null) {				
				if(user.isNewUser()) {
					user.setNewUser(false);
					userservice.saveOrUpdateUser(user);
					status = new ResponseStatus(ResponseStatusCode.STATUS_OK, MessageConstants.SUCCESS);
					res.setStatus(ResponseStatusCode.STATUS_OK);
				}else {
					
					status=new ResponseStatus(ResponseStatusCode.STATUS_OK,"Old user");
				}
				
			} else {
				status = new ResponseStatus(ResponseStatusCode.STATUS_INVALID,	env.getProperty(PropertyConstants.USERIDNOTEXISTS));

			}
		} catch (Exception e) {
			logger.error(e);
		}
		return status;
	}

	public Map<String, Boolean> getNewUsertatus(String userId, HttpServletRequest request) {
		
		HashMap<String, Boolean> newUserStatus = new HashMap<>();
		User user = commonUtils.getUser(request);
		user = (user == null && userId != null) ? userservice.getUserById(userId)	: user;
		try {
			if (user != null) {				
				newUserStatus.put("newUser", user.isNewUser());
				return newUserStatus;
				
			} else {

				return null;
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return newUserStatus;
	}

	public ResponseStatus changeVerficationStatus(String email, HttpServletResponse res) {
		ResponseStatus status = null;
		User user = userservice.getUserByUserNameOrEmailorMobile(email);
		try {
			if (user != null) {				
				if(user.isNewUser()) {
					AccountStatus accountStatus = userservice.getAccountStatusById(2l);
					user.setAccountstatus(accountStatus);
					userservice.saveOrUpdateUser(user);
					status = new ResponseStatus(ResponseStatusCode.STATUS_OK, MessageConstants.SUCCESS);
					res.setStatus(ResponseStatusCode.STATUS_OK);
				}else {					
					status=new ResponseStatus(ResponseStatusCode.STATUS_OK,INVALID);
				}
				
			} else {
				status = new ResponseStatus(ResponseStatusCode.STATUS_INVALID,	env.getProperty(PropertyConstants.USERIDNOTEXISTS));

			}
		} catch (Exception e) {
			logger.error(e);
		}
		return status;
	}

	public ResponseMessage<User> reSendVerificationEmail(UserInput userObj, HttpServletResponse response) {
		ResponseStatus status = null;
		User user   = null;
		response.setHeader("Cache-Control", "no-cache");
		
		try {
			user = userservice.getUserByUserNameOrEmailorMobile(userObj.getEmail());

			if(user != null){
				
				
//					String verifyLink = commonUtils.randomReferenceString(6)+"_"+user.getUserName().substring(0, 3);
//					int channel=1;
//					long userType = userObj.getUserType() <= 0 ? user.getUserType().getUserTypeId() : userObj.getUserType();
//					String verificationPage =  CommonProperties.getUiPath()+CommonProperties.getUiDomain()+"?verfyLink="+verifyLink+"&channel="+channel+"&userType="+userType;
//					user.setVerifyLink(verifyLink);
//					user.setLinkcreatedon(new Date());
//					String msg = userObj.getUserType() == 2 ?"Thank you for registering with us. You can now login to the application and get your jobs posted and find employees who suit your requirement":env.getProperty("jobseekerVerficationMsg");
//					EmailManager.userVerificationMail(user.getEmail(),msg, userObj.getFirstName() != null && !userObj.getFirstName().isEmpty()?commonUtils.toTitleCase(userObj.getFirstName()):user.getFullName(),"", verificationPage);
//					
//
//					
//					userservice.saveOrUpdateUser(user);
				
					user = commomResendVerficationEmail(user, userObj);
				
					status=new ResponseStatus(ResponseStatusCode.STATUS_OK,MessageConstants.SUCCESS);
					response.setStatus(ResponseStatusCode.STATUS_OK);
					return new ResponseMessage<>(status, user);
							
				
			}
			else{
				status=new ResponseStatus(ResponseStatusCode.STATUS_NORECORD,env.getProperty(NORECORD));

			}    	

		} catch (Exception e) {
			status = new ResponseStatus(ResponseStatusCode.STATUS_INTERNAL_ERROR,env.getProperty(INTERNALERROR));
			response.setStatus(ResponseStatusCode.STATUS_INTERNAL_ERROR); 
			logger.error(e);
		}

		return new ResponseMessage<>(status, null);
	}
	
	
	private void sendResendVerificationEmail(User user) {
		try {
			
			if(user!=null)
			{
				Date currentDate = new Date();
				Date dateFromDatabase = user.getLinkcreatedon();// get the date from the database
				long duration = currentDate.getTime() - dateFromDatabase.getTime();
				
				long diffInHours = TimeUnit.MILLISECONDS.toHours(duration);
					
					if ((diffInHours >= 12)) {
						UserInput userObj = new UserInput();
						
						commomResendVerficationEmail(user, userObj);
					}			
								
			}
			else
			{
				logger.error("User null");
			}
			
		} catch (Exception e) {
			logger.error(e);
		}
		
		
	}

	private User commomResendVerficationEmail(User user, UserInput userObj) {
		
		String verifyLink = commonUtils.randomReferenceString(6)+"_"+user.getUserName().substring(0, 3);
		int channel=1;
		long userType = userObj.getUserType() <= 0 ? user.getUserType().getUserTypeId() : userObj.getUserType();
		String verificationPage =  CommonProperties.getUiPath()+CommonProperties.getUiDomain()+"?verfyLink="+verifyLink+"&channel="+channel+"&userType="+userType;
		user.setVerifyLink(verifyLink);
		user.setLinkcreatedon(new Date());
		
		String msg = userType == 2 ?"Thank you for registering with us. You can now login to the application and get your jobs posted and find employees who suit your requirement":env.getProperty("jobseekerVerficationMsg");
		EmailManager.userVerificationMail(user.getEmail(),msg, userObj.getFirstName() != null && !userObj.getFirstName().isEmpty()?commonUtils.toTitleCase(userObj.getFirstName()):user.getFullName(),"", verificationPage);
		
				
		return userservice.saveOrUpdateUser(user);
		
	}
}
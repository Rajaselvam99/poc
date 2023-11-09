package com.obs.controller;

import java.security.Principal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.obs.SwaggerClass;
import com.obs.customdomain.PostJobInput;
import com.obs.customdomain.UserInput;
import com.obs.domain.Country;
import com.obs.domain.Employer;
import com.obs.domain.Jobseeker;
import com.obs.domain.PostJob;
import com.obs.domain.PostJobQuestion;
import com.obs.domain.DefaultPostJobQuestion;
import com.obs.domain.Subscriptionuser;
import com.obs.domain.User;
import com.obs.helper.LoginServiceHelper;
import com.obs.messages.ResponseListMessage;
import com.obs.messages.ResponseMessage;
import com.obs.messages.ResponseMessageWithToken;
import com.obs.messages.ResponseStatus;



@RestController
@SwaggerClass
public class LoginController {

	@Autowired
	LoginServiceHelper loginServiceHelper;
	
	@GetMapping("/authenticate")
	public ResponseEntity<Principal> user(Principal user) {
		return ResponseEntity.<Principal>ok(user);
	}	 
	
	@PostMapping("/register")
	public @ResponseBody ResponseMessage<User> registerUser(@RequestBody UserInput userObj,HttpServletRequest request,final HttpServletResponse res) {
		return loginServiceHelper.registerUser(userObj, res);
	}

	@PostMapping("/subscribeuser")
	public @ResponseBody ResponseMessage<Subscriptionuser> subscribeUser(@RequestBody UserInput userObj,HttpServletRequest request,final HttpServletResponse res) {
		return loginServiceHelper.subscribeUser(userObj, res);
	}

	@PostMapping("/registeremployer")
	public @ResponseBody ResponseMessageWithToken<Employer> registerEmployer(@RequestBody UserInput userObj,HttpServletRequest request,final HttpServletResponse res) {
		return loginServiceHelper.registerEmployer(userObj, res);
	}
	
	@PostMapping("/getuserbyverificationlink")
	public @ResponseBody ResponseMessage<User> getUserByVerificationLink(@RequestBody UserInput userObj,HttpServletRequest request,final HttpServletResponse response)
	{
		return loginServiceHelper.getUserByVerificationLink(userObj, response);

	}
	
	@PostMapping("/verifyuserbyverificationlink")
	public @ResponseBody ResponseMessage<User> verifyUserByVerificationLink(@RequestBody UserInput userObj,HttpServletRequest request,final HttpServletResponse response)
	{

		return loginServiceHelper.verifyUserByVerificationLink(userObj,  response);

	}
	
	@PostMapping("/forgotpassword")
	public @ResponseBody ResponseMessage<User> forgotPassword(@RequestBody UserInput userObj,HttpServletRequest request,final HttpServletResponse response)
	{
		return loginServiceHelper.forgotPassword(userObj, response);
	}
	
	@PostMapping("/login")
	public @ResponseBody JSONObject doLogin(@FormParam("username") String username,
			@FormParam("password") String password, 
	@Value("0")	@FormParam(value = "type") int type, HttpServletRequest request, final HttpServletResponse res) {
		return loginServiceHelper.doLogin(username, password, type, request, res);
	}
	
	@PostMapping("/registerjobseeker")
	public @ResponseBody ResponseMessageWithToken<Jobseeker> registerJobseeker(@RequestBody UserInput userObj,HttpServletRequest request,final HttpServletResponse res) {
		return loginServiceHelper.registerJobseeker(userObj, res);
	}	
	
	@PostMapping("/postjob")
	public @ResponseBody ResponseMessage<PostJob> registerPostJob(@RequestBody PostJobInput postJobObj,HttpServletRequest request,final HttpServletResponse res) {
		return loginServiceHelper.registerPostJob(postJobObj,request, res);
	}
	
	@PostMapping("/setpathway")
	public @ResponseBody ResponseMessage<Jobseeker> setPathway(@RequestBody UserInput userObj,HttpServletRequest request,final HttpServletResponse res) {
		return loginServiceHelper.setPathway(userObj, request, res);
	}
	
	@PostMapping("/setexperience")
	public @ResponseBody ResponseMessage<Jobseeker> setExperience(@RequestBody UserInput userObj,HttpServletRequest request,final HttpServletResponse res) {
		return loginServiceHelper.setExperience(userObj, request, res);
	}
	
	@GetMapping("/allcountry")
	public @ResponseBody ResponseListMessage<Country> getAllCountry(HttpServletRequest request,final HttpServletResponse res) {
		return loginServiceHelper.getAllCountry(res);
	}
	
	@PostMapping("/changepassword")
	public @ResponseBody ResponseStatus changePassword(@RequestBody UserInput userObj,HttpServletRequest request,final HttpServletResponse res) {
		return loginServiceHelper.changePassword(userObj,request, res);
	}
	
	@PutMapping("/revoke")
	public @ResponseBody ResponseStatus logout(HttpServletRequest request) {
		return loginServiceHelper.logout(request);
		
	}
	
	@PostMapping("/changenewuserstatus")
	public @ResponseBody ResponseStatus changeNewUserStatus(@RequestBody UserInput userObj,HttpServletRequest request,final HttpServletResponse res) {
		return loginServiceHelper.changeNewUserStatus(userObj,request, res);
	}
	
	@GetMapping("/getnewuserstatus")
	public Map<String, Boolean> getNewUsertatus(@RequestParam(value = "userId", required = false) String userId,HttpServletRequest request,final HttpServletResponse res) {
		return loginServiceHelper.getNewUsertatus(userId, request);
	}
	
	@PostMapping("/changeverficationstatus")
	public @ResponseBody ResponseStatus changeVerficationStatus(@RequestParam(value = "email") String email, HttpServletRequest request,final HttpServletResponse res) {
		return loginServiceHelper.changeVerficationStatus(email, res);
	}
	
	@PostMapping("/resendverificationemail")
	public @ResponseBody ResponseMessage<User> reSendVerificationEmail(@RequestBody UserInput userObj,HttpServletRequest request,final HttpServletResponse response)
	{
		return loginServiceHelper.reSendVerificationEmail(userObj, response);
	}
	
	@GetMapping("/getpostjobquestion")
	public @ResponseBody ResponseListMessage<DefaultPostJobQuestion> getPostJobQuestion( @RequestParam(value="pageNumber",required = false,defaultValue = "0") int pageNumber,@RequestParam(value="pageSize",required = false,defaultValue = "0") int pageSize, HttpServletRequest request,
			final HttpServletResponse res) {
		return loginServiceHelper.getPostJobQuestion( request, res);
	}
	
	@PostMapping("/addpostjobquestion")
	public @ResponseBody ResponseStatus saveOrUpdatePostJobQuestion(@RequestParam(value = "question" ,  required = false) String question, @RequestParam(value = "id" ,  required = false ) String id, HttpServletRequest request,final HttpServletResponse res) {
		return loginServiceHelper.saveOrUpdatePostJobQuestion(question, id, request, res);
	}
	
	
	@PostMapping("/postjobquestionbyid")
	public @ResponseBody  ResponseListMessage<PostJobQuestion> getPostJobQuestionById( @RequestBody PostJobInput postJobObj, HttpServletRequest request,final HttpServletResponse res) {
		return loginServiceHelper.getPostJobQuestionById( postJobObj, request, res);
	}
	
	

}

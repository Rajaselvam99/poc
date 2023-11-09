package com.obs.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.obs.SwaggerClass;
import com.obs.customdomain.PostJobInput;
import com.obs.domain.PostJob;
import com.obs.helper.EmployerServiceHelper;
import com.obs.messages.ResponseMessage;
import com.obs.messages.ResponseStatus;

@RestController
@SwaggerClass
public class EmployerController {

	@Autowired
	EmployerServiceHelper employerServiceHelper;
	
	@GetMapping("/getjobbyinvite")
	public @ResponseBody ResponseMessage<PostJob> getPostByInvite(@RequestParam("invite") String invite,HttpServletRequest request,final HttpServletResponse res) {
		return employerServiceHelper.getPostByInvite(invite, res);
	}
	
	@PostMapping("/generateinvite")
	public @ResponseBody ResponseMessage<PostJob> updatePostJobInviteLink(@RequestBody PostJobInput postJobObj,HttpServletRequest request,final HttpServletResponse res) {
		return employerServiceHelper.updatePostJobInviteLink(postJobObj, res);
	}
	
	@GetMapping("/getjobbypostid")
	public @ResponseBody ResponseMessage<PostJob> getPostByPostId(@RequestParam("postId") String postId,HttpServletRequest request,final HttpServletResponse res) {
		return employerServiceHelper.getPostByPostId(postId, request, res);
	}
	
	@GetMapping("/sendjobinvite")
	public @ResponseBody ResponseStatus sendJobInvite(@RequestParam(value = "postId") String postId, @RequestParam(value = "jobseekerId") String jobseekerId, @RequestParam(value = "userid", required = false) String userid, HttpServletRequest request,final HttpServletResponse res) {
		return employerServiceHelper.sendJobInvite(postId, userid, jobseekerId, request, res);
	}
}

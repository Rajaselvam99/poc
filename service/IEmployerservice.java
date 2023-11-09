package com.obs.service;

import java.util.List;

import com.obs.domain.Employer;
import com.obs.domain.PostJob;
import com.obs.domain.DefaultPostJobQuestion;

public interface IEmployerservice {

	Employer saveOrUpdateEmployer(Employer employer);

	PostJob saveOrUpdatePostJob(PostJob postJob);

	Employer getEmployerById(String employerId);
	
	PostJob getPostJobById(String postJobId);

	PostJob getPostJobByInvite(String invite);

	List<String> getALLPostJobByInvite();

	Employer getEmployerByUserId(String userId);
	
	List<DefaultPostJobQuestion> getPostJobQuestion();

	DefaultPostJobQuestion getPostJobQuestionById(String postJobQuestionId);
}

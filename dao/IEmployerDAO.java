package com.obs.dao;

import java.util.List;

import com.obs.domain.Employer;
import com.obs.domain.PostJob;
import com.obs.domain.DefaultPostJobQuestion;
import com.obs.domain.SkillsData;

public interface IEmployerDAO {

	Employer saveOrUpdateEmployer(Employer employer);

	PostJob saveOrUpdatePostJob(PostJob postJob);

	Employer getEmployerById(String employerId);

	PostJob getPostJobById(String postJobId);

	PostJob getPostJobByInvite(String invite);

	List<String> getALLPostJobByInvite();

	Employer getEmployerByUserId(String userId);

	SkillsData getSkillDataByName(String singleSkill);
	
	DefaultPostJobQuestion getPostJobQuestionById(String  postJobQuestionId);
	
	List<DefaultPostJobQuestion> getPostJobQuestion();

}

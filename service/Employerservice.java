package com.obs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.obs.dao.IEmployerDAO;
import com.obs.domain.Employer;
import com.obs.domain.PostJob;
import com.obs.domain.DefaultPostJobQuestion;
import com.obs.domain.SkillsData;

@Service
public class Employerservice implements IEmployerservice{

	@Autowired
	IEmployerDAO employerDAO;

	@Override
	public Employer saveOrUpdateEmployer(Employer employer) {
		return employerDAO.saveOrUpdateEmployer(employer);
	}

	@Override
	public PostJob saveOrUpdatePostJob(PostJob postJob) {
		return employerDAO.saveOrUpdatePostJob(postJob);
	}

	@Override
	public Employer getEmployerById(String employerId) {
		return employerDAO.getEmployerById(employerId);
	}

	@Override
	public PostJob getPostJobById(String postJobId) {
		return employerDAO.getPostJobById(postJobId);
	}

	@Override
	public PostJob getPostJobByInvite(String invite) {
		return employerDAO.getPostJobByInvite(invite);
	}

	@Override
	public List<String> getALLPostJobByInvite() {
		return employerDAO.getALLPostJobByInvite();
	}

	@Override
	public Employer getEmployerByUserId(String userId) {
		return employerDAO.getEmployerByUserId(userId);
	}

	public SkillsData getSkillDataByName(String singleSkill) {
		return employerDAO.getSkillDataByName(singleSkill);
	}

	@Override
	public DefaultPostJobQuestion getPostJobQuestionById(String postJobQuestionId) {
		return employerDAO.getPostJobQuestionById(postJobQuestionId);
	}

	@Override
	public List<DefaultPostJobQuestion> getPostJobQuestion() {
		return employerDAO.getPostJobQuestion();
	}
}

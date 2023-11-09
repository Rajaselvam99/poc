package com.obs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.obs.dao.IJobseekerDAO;
import com.obs.domain.JobApply;
import com.obs.domain.JobRefer;
import com.obs.domain.JobSave;
import com.obs.domain.Jobseeker;
import com.obs.domain.TokenEarned;
import com.obs.view.JobseekerPathwayView;

@Service
public class Jobseekerservice implements IJobseekerservice{

	@Autowired
	IJobseekerDAO jobseekerDAO;

	@Override
	public Jobseeker saveOrUpdateJobseeker(Jobseeker jobseeker) {
		return jobseekerDAO.saveOrUpdateJobseeker(jobseeker);
	}

	@Override
	public Jobseeker getJobseekerbyId(String jobseekerId) {
		return jobseekerDAO.getJobseekerbyId(jobseekerId);
	}

	@Override
	public Jobseeker getJobseekerbyUserId(String userId) {
		return jobseekerDAO.getJobseekerbyUserId(userId);
	}

	@Override
	public List<JobseekerPathwayView> getJobseekerByPathwayName(String pathway) {
		return jobseekerDAO.getJobseekerByPathwayName(pathway);
	}

	@Override
	public List<JobRefer> getJobreferByRefferEmail(String email) {
		return jobseekerDAO.getJobreferByRefferEmail(email);
	}

	@Override
	public List<TokenEarned> getTokenEarnByEmail(String email) {
		return jobseekerDAO.getTokenEarnByEmail(email);
	}


	@Override
	public JobApply getJobApplyByUserIdAndPostId(String userId, String postId) {
		return jobseekerDAO.getJobApplyByUserIdAndPostId(userId, postId);
	}

	@Override
	public JobSave getJobSaveStatusByUserIdAndPostId(String jobseekerId, String postId) {
		return jobseekerDAO.getJobSaveStatusByUserIdAndPostId(jobseekerId, postId);
	}
}

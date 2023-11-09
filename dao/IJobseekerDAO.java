package com.obs.dao;

import java.util.List;

import com.obs.domain.JobApply;
import com.obs.domain.JobRefer;
import com.obs.domain.JobSave;
import com.obs.domain.Jobseeker;
import com.obs.domain.TokenEarned;
import com.obs.view.JobseekerPathwayView;

public interface IJobseekerDAO {

	Jobseeker saveOrUpdateJobseeker(Jobseeker jobseeker);

	Jobseeker getJobseekerbyId(String jobseekerId);

	Jobseeker getJobseekerbyUserId(String userId);

	List<JobseekerPathwayView> getJobseekerByPathwayName(String pathway);

	List<JobRefer> getJobreferByRefferEmail(String email);

	List<TokenEarned> getTokenEarnByEmail(String email);

	JobApply getJobApplyByUserIdAndPostId(String userId, String postId);

	JobSave getJobSaveStatusByUserIdAndPostId(String jobseekerId, String postId);

}

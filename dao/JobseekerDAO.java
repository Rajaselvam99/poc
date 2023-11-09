package com.obs.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.obs.domain.JobApply;
import com.obs.domain.JobRefer;
import com.obs.domain.JobSave;
import com.obs.domain.Jobseeker;
import com.obs.domain.TokenEarned;
import com.obs.repository.JobseekerRepository;
import com.obs.repository.JobsekerExperienceRepository;
import com.obs.view.JobseekerPathwayView;

@Transactional
@Repository
public class JobseekerDAO implements IJobseekerDAO {

	private static final Logger logger = LogManager.getLogger(JobseekerDAO.class);
	
	@Autowired
	JobseekerRepository jobseekerRepository;
	
	@Autowired
	EntityManager em;
	
	@Autowired
	JobsekerExperienceRepository jobsekerExperienceRepository;
	
	@Override
	public Jobseeker saveOrUpdateJobseeker(Jobseeker jobseeker) {
		Jobseeker jsr = null;
		try
		{
			jsr = jobseekerRepository.save(jobseeker);	
		}
		catch(Exception e)
		{
			logger.error(e);
		}
		return jsr;
	}

	@Override
	public Jobseeker getJobseekerbyId(String jobseekerId) {
		Jobseeker jsr = null;
		try
		{
			Optional<Jobseeker> jsk = jobseekerRepository.findById(jobseekerId);
			jsr = jsk.isPresent()?jsk.get():jsr;
		}
		catch(Exception e)
		{
			logger.error(e);
		}
		return jsr;
	}
	
	@Override
	public Jobseeker getJobseekerbyUserId(String userId) {
		List<Jobseeker> list = null;
		try
		{
			TypedQuery<Jobseeker> query = em.createQuery("from Jobseeker where userInfo.userId =:d",Jobseeker.class).setParameter("d", userId);
		list = query.getResultList();
		}
		catch(Exception e)
		{
			logger.error(e);
		}
		return list != null && !list.isEmpty() ? list.get(0):null;
	}

	@Override
	public List<JobseekerPathwayView> getJobseekerByPathwayName(String pathway) {
		List<JobseekerPathwayView> list = null;
		try
		{
			String qlString ="from JobseekerPathwayView where (currentPath =:pathway or targetPath =:pathway) group by jobseekerId";
			
			TypedQuery<JobseekerPathwayView>  query = em.createQuery(qlString, JobseekerPathwayView.class).setParameter("pathway", pathway);
			
			list = query.getResultList();
		}
		catch(Exception e)
		{
			logger.error(e);
		}
		return list != null && !list.isEmpty()?list:null;	
	}

	@Override
	public List<JobRefer> getJobreferByRefferEmail(String email) {
		List<JobRefer> list = null;
		try
		{
			TypedQuery<JobRefer> query = em.createQuery("from JobRefer where referEmail =:e ", JobRefer.class).setParameter("e", email);
		list = query.getResultList();
		}
		catch(Exception e)
		{
			logger.error(e);
		}
		return list != null && !list.isEmpty()?list:null;
	}

	@Override
	public List<TokenEarned> getTokenEarnByEmail(String email) {
		List<TokenEarned> list = null;
		try
		{
			TypedQuery<TokenEarned> query = em.createQuery("from TokenEarned where jobSeekerEmail =:e ", TokenEarned.class).setParameter("e", email);
		list = query.getResultList();
		}
		catch(Exception e)
		{
			logger.error(e);
		}
		return list != null && !list.isEmpty()?list:null;
	}


	@Override
	public JobApply getJobApplyByUserIdAndPostId(String userId, String postId) {
		List<JobApply> list = null;
		try
		{
			TypedQuery<JobApply> query = em.createQuery("from JobApply where job.postId =:p and jobseeker.userInfo.userId =:d  ",JobApply.class).setParameter("d", userId).setParameter("p", postId);
		list = query.getResultList();
		}
		catch(Exception e)
		{
			logger.error(e);
		}
		return list != null && !list.isEmpty() ? list.get(0):null;
	}

	@Override
	public JobSave getJobSaveStatusByUserIdAndPostId(String jobseekerId, String postId) {
		List<JobSave> list = null;
		try
		{
			TypedQuery<JobSave> query = em.createQuery("from JobSave where postJobId =:p and jobseekerId =:d  ",JobSave.class).setParameter("d", jobseekerId).setParameter("p", postId);
		list = query.getResultList();
		}
		catch(Exception e)
		{
			logger.error(e);
		}
		return list != null && !list.isEmpty() ? list.get(0):null;
	}

}

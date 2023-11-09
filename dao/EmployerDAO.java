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

import com.obs.domain.Employer;
import com.obs.domain.PostJob;
import com.obs.domain.DefaultPostJobQuestion;
import com.obs.domain.SkillsData;
import com.obs.query.EmployerQuery;
import com.obs.repository.EmployerRepository;
import com.obs.repository.DefaultPostJobQuestionRepository;
import com.obs.repository.PostJobRepository;

@Repository
@Transactional
public class EmployerDAO implements IEmployerDAO {

	private static final Logger logger = LogManager.getLogger(EmployerDAO.class); 

	@Autowired
	private EntityManager em;

	@Autowired
	EmployerRepository employerRepository;

	@Autowired
	PostJobRepository postJobRepository;
	
	@Autowired
	DefaultPostJobQuestionRepository postJobQuestionRepo; 
	
	@Override
	public Employer saveOrUpdateEmployer(Employer employer) {
		Employer emp = null;
		try
		{
			emp = employerRepository.save(employer);	
		}
		catch(Exception e)
		{
			logger.error(e);
		}
		return emp;
	}

	@Override
	public PostJob saveOrUpdatePostJob(PostJob postJob) {
		PostJob pjb = null;
		try
		{
			pjb = postJobRepository.save(postJob);	
		}
		catch(Exception e)
		{
			logger.error(e);
		}
		return pjb;
	}
	
	@Override
	public Employer getEmployerById(String employerId) {
		Employer emp = null;
		try
		{
			Optional<Employer> employer =employerRepository.findById(employerId);
			emp = employer.isPresent() ? employer.get():null;
		}
		catch(Exception e)
		{
			logger.error(e);
		}
		return emp;
	}
	
	@Override
	public PostJob getPostJobById(String postJobId) {
		PostJob pjb = null;
		try
		{
			Optional<PostJob> opj = postJobRepository.findById(postJobId);
			pjb = opj.isPresent()?opj.get():null;
		}
		catch(Exception e)
		{
			logger.error(e);
		}
		return pjb;
	}
	
	
	@Override
	public PostJob getPostJobByInvite(String invite) {
		List<PostJob> list = null;
		try
		{
			TypedQuery<PostJob>  query = em.createQuery(EmployerQuery.POSTJOBBYINVITE, PostJob.class).setParameter("v", invite);
			list = query.getResultList();
		}
		catch(Exception e)
		{
			logger.error(e);
		}
		return list != null && !list.isEmpty()?list.get(0):null;
	}
	
	@Override
	public List<String> getALLPostJobByInvite() {
		List<String> list = null;
		try
		{
			TypedQuery<String>  query = em.createQuery(EmployerQuery.ALLPOSTJOBBYINVITE, String.class);
			list = query.getResultList();
		}
		catch(Exception e)
		{
			logger.error(e);
		}
		return list != null && !list.isEmpty()?list:null;
	}
	
	@Override
	public Employer getEmployerByUserId(String userId) {
		List<Employer> list = null;
		try
		{
			TypedQuery<Employer>  query = em.createQuery(EmployerQuery.EMPLOYERBYUSERID, Employer.class).setParameter("uid", userId);
			list = query.getResultList();
		}
		catch(Exception e)
		{
			logger.error(e);
		}
		return list != null && !list.isEmpty()?list.get(0):null;
	}

	@Override
	public SkillsData getSkillDataByName(String singleSkill) {
		List<SkillsData> list = null;
		try
		{
			TypedQuery<SkillsData> query = em.createQuery("from SkillsData where LOWER(skill) =:s ", SkillsData.class).setParameter("s", singleSkill.toLowerCase());
			list = query.getResultList();
		}
		catch(Exception e)
		{
			logger.error(e);
		}
		return list != null && !list.isEmpty() ? list.get(0):null;
	}

	@Override
	public DefaultPostJobQuestion  getPostJobQuestionById(String  postJobQuestionId) {
		
		DefaultPostJobQuestion question=null;
		try
		{
			Optional<DefaultPostJobQuestion> pjq=postJobQuestionRepo.findById(postJobQuestionId);
			question = pjq.isPresent()? pjq.get() : null;
			
		}
		catch(Exception e)
		{
			logger.info(e);
		}
		return question;
	}

	@Override
	public List<DefaultPostJobQuestion> getPostJobQuestion() {
		List<DefaultPostJobQuestion> pjq=null;
		try
		{
			TypedQuery<DefaultPostJobQuestion> query = em.createQuery("from DefaultPostJobQuestion", DefaultPostJobQuestion.class);
			pjq=query.getResultList();
		}
		catch(Exception e)
		{
			logger.info(e);
		}
		return  pjq;
	}
}

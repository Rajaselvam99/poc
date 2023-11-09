package com.obs.helper;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.obs.customdomain.PostJobInput;
import com.obs.domain.JobApply;
import com.obs.domain.JobSave;
import com.obs.domain.Jobseeker;
import com.obs.domain.Notification;
import com.obs.domain.PostJob;
import com.obs.domain.User;
import com.obs.email.EmailManager;
import com.obs.messages.MessageConstants;
import com.obs.messages.PropertyConstants;
import com.obs.messages.ResponseMessage;
import com.obs.messages.ResponseStatus;
import com.obs.messages.ResponseStatusCode;
import com.obs.repository.NotificationRepository;
import com.obs.service.Employerservice;
import com.obs.service.IJobseekerservice;
import com.obs.service.IUserservice;
import com.obs.util.CommonProperties;
import com.obs.util.CommonUtils;
import com.obs.validation.BasicValidation;
import com.obs.validation.UserValidation;

@PropertySources(value = { @PropertySource("classpath:application-msg.properties" ),@PropertySource("classpath:ValidationMessage/userValidation-msg.properties")})
@Service
public class EmployerServiceHelper {

	private static final Logger logger = LogManager.getLogger(EmployerServiceHelper.class);

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
	NotificationRepository notificationRepository;
	
	public ResponseMessage<PostJob> getPostByInvite(String inviteLink, HttpServletResponse res) {
		ResponseStatus status = null;
		PostJob postJob = null;
		try
		{
			postJob = employerService.getPostJobByInvite(inviteLink);
			if(postJob != null)
			{
				status = new ResponseStatus(ResponseStatusCode.STATUS_OK, MessageConstants.SUCCESS);
			}
			else
			{
				status = new ResponseStatus(ResponseStatusCode.STATUS_NORECORD, PropertyConstants.NORECORD);
				res.setStatus(ResponseStatusCode.STATUS_NORECORD);
				return new ResponseMessage<>(status, null);
			}
		}
		catch(Exception e)
		{
			logger.error(e);
		}
		return new ResponseMessage<>(status, postJob);
	}

	public ResponseMessage<PostJob> getPostByPostId(String id, HttpServletRequest request, HttpServletResponse res) {
		ResponseStatus status = null;
		PostJob postJob = null;
		
		User user = commonUtils.getUser(request);
		try
		{
			if (user != null) {	
			postJob = employerService.getPostJobById(id);
			if(postJob != null)
			{
				
				postJob.setApplyStatus(getJobApplyStatus(user, postJob));
				postJob.setIsApplied(getJobApplyStatus(user, postJob));
				postJob.setSaved(getSavedStatys(user, postJob));
				
				status = new ResponseStatus(ResponseStatusCode.STATUS_OK, MessageConstants.SUCCESS);
			}
			else
			{
				status = new ResponseStatus(ResponseStatusCode.STATUS_NORECORD, PropertyConstants.NORECORD);
				res.setStatus(ResponseStatusCode.STATUS_NORECORD);
				return new ResponseMessage<>(status, null);
			}
		}
			else 
			{
				status = new ResponseStatus(ResponseStatusCode.STATUS_INVALID,	env.getProperty(PropertyConstants.USERIDNOTEXISTS));

			}
		}
		catch(Exception e)
		{
			logger.error(e);
		}
		return new ResponseMessage<>(status, postJob);
	}
	
	private boolean getSavedStatys(User user, PostJob postJob) {
		try {

			Jobseeker jobseeker = jobseekerservice.getJobseekerbyUserId(user.getUserId());
			if (jobseeker != null) {
				JobSave jobSave = jobseekerservice.getJobSaveStatusByUserIdAndPostId(jobseeker.getJobseekerId(), postJob.getPostId());
				if (jobSave != null) {
					return jobSave.isSaved();
				}
			}
		
		} catch (Exception e) {
			logger.error(e);
		}
		return false;
	}

	private String getJobApplyStatus(User user, PostJob postJob) {
		try {
			
			JobApply jobApply = jobseekerservice.getJobApplyByUserIdAndPostId(user.getUserId(), postJob.getPostId());
			if(jobApply != null) {
				return jobApply.getApplyStatus();
			}
			
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}

	public ResponseMessage<PostJob> updatePostJobInviteLink(PostJobInput postJobObj,  HttpServletResponse res) {
		ResponseStatus status = null;
		PostJob postJob = null;
		try
		{
			postJob = employerService.getPostJobById(postJobObj.getPostId());
			if(postJob != null)
			{
				String job = (postJob.getName().length() > 4)?postJob.getName().substring(0, 3):postJob.getName();
				String inviteLink = commonUtils.randomReferenceString(6)+"_"+job;
				List<String> postJob1 = employerService.getALLPostJobByInvite();
				while(postJob1 != null && !postJob1.isEmpty() && postJob1.contains(inviteLink))
				{
					inviteLink = commonUtils.randomReferenceString(6)+"_"+job;
				}
				String inviteUrl = CommonProperties.getUiPath()+CommonProperties.getUiInvite()+"?inviteLink="+inviteLink;
				postJob.setInviteUrl(inviteUrl);
				postJob.setInviteLink(inviteLink);
				postJob = employerService.saveOrUpdatePostJob(postJob);
				status = new ResponseStatus(ResponseStatusCode.STATUS_OK, MessageConstants.SUCCESS);
			}
			else
			{
				status = new ResponseStatus(ResponseStatusCode.STATUS_NORECORD, PropertyConstants.NORECORD);
				res.setStatus(ResponseStatusCode.STATUS_NORECORD);
				return new ResponseMessage<>(status, null);
			}
		}
		catch(Exception e)
		{
			logger.error(e);
		}
		return new ResponseMessage<>(status, postJob);
	}

	public ResponseStatus sendJobInvite(String postId, String userid, String jobseekerId, HttpServletRequest request, HttpServletResponse res) {
		ResponseStatus status = null;
		User user = commonUtils.getUser(request);
		user = (user == null && userid != null) ? userservice.getUserById(userid) : user;
		PostJob postJob = null;
		Jobseeker jobseeker = null;
		try {
			if (user != null) {	
				
				postJob = employerService.getPostJobById(postId);
				jobseeker = jobseekerservice.getJobseekerbyId(jobseekerId);
				if(postJob != null && jobseeker != null)
				{
					
					String link =  CommonProperties.getUiPath()+"employee/jobs/"+postJob.getPostId();
					EmailManager.generteJobInviteEmail(postJob, jobseeker, link);
					setInAppNotificationInviteJob(postJob, jobseeker);
					status = new ResponseStatus(ResponseStatusCode.STATUS_OK, MessageConstants.SUCCESS);
					res.setStatus(ResponseStatusCode.STATUS_OK);
				}
				else
				{
					status = new ResponseStatus(ResponseStatusCode.STATUS_NORECORD, PropertyConstants.NORECORD);
					res.setStatus(ResponseStatusCode.STATUS_NORECORD);
					
				}			
				
			} else {
				status = new ResponseStatus(ResponseStatusCode.STATUS_INVALID,	env.getProperty(PropertyConstants.USERIDNOTEXISTS));

			}
		} catch (Exception e) {
			logger.error(e);
		}
		return status;
	}

	private void setInAppNotificationInviteJob(PostJob postJob, Jobseeker jobseeker) {
		String senderId = postJob.getEmployer().getUserInfo().getUserId();
		String senderName = postJob.getEmployer().getUserInfo().getFullName();
		String receiverId = jobseeker.getUserInfo().getUserId();
		String receiverName = jobseeker.getUserInfo().getFullName();
		String description = postJob.getEmployer().getEmployerName()+ " invited a job";
		String hintDescription = "Invite a job";
		String postId = postJob.getPostId();
		
		setInAppNotification(senderId, senderName, receiverId, receiverName, description, hintDescription, 14, postId);
		
		
	}
	
	private void setInAppNotification(String senderId, String senderName, String receiverId, String receiverName, String description, String hintDescription, int notificationType, String postId) {
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
}

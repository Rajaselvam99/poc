package com.obs.email;

import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.obs.domain.Jobseeker;
import com.obs.domain.PostJob;
import com.obs.domain.Subscriptionuser;
import com.obs.domain.User;
import com.obs.messages.MessageConstants;
import com.obs.util.CommonProperties;
import com.obs.view.JobseekerPathwayView;



public class EmailManager {

	private EmailManager(){}
	private static final Logger logger = LogManager.getLogger(EmailManager.class );

	protected static EmailConfiguration emailConfig1 = new EmailConfiguration();

	static Thread emailThread;
	
	private static String getProfile() {
		ResourceBundle bundlePrf = null;
		bundlePrf = ResourceBundle.getBundle("application");
		return bundlePrf.getString("spring.profiles.active");
	}
	
	public static boolean userVerificationMail(String emailAddress,String msg,String firstname,String passcode, String link) {
		final String emailAddressFinal = emailAddress;
		final String firstnameFinal = firstname;		 
		final String linkFinal = link;
		emailThread = new Thread(){
			@Override
			public void run() {
				try {
					EmailConfiguration emailConfig = new EmailConfiguration();
					EmailSender sender = new EmailSender();
					String url  = CommonProperties.getBaseURL() + CommonProperties.getUiPath();
					String base = !getProfile().equalsIgnoreCase("local")?CommonProperties.getPublicBaseURL():"http://localhost:8080/";
					String logoURL  = base + CommonProperties.getContextPath()+"emailTemplate/Logo.png";
					String message = emailConfig.userVerificationMail(emailAddressFinal,msg,firstnameFinal,passcode,linkFinal,url,logoURL);
					sender.sendFromCustomerService(emailAddressFinal, emailConfig.getUserActivationAdminSubject(), message);
				} catch (Exception e) {
					logger.error("Error while sending Email at verify email ",e);
				} 
				finally{
					emailThread.interrupt();
					emailThread = null;
				}
			}
		};
		emailThread.start();
		return true;
	}
	
	public static boolean forgotPasswordMail(String emailAddress,String msg,String firstname,String passcode, String link) {
		final String emailAddressFinal = emailAddress;
		final String firstnameFinal = firstname;		 
		final String linkFinal = link;
		emailThread = new Thread(){
			@Override
			public void run() {
				try {
					EmailConfiguration emailConfig = new EmailConfiguration();
					EmailSender sender = new EmailSender();
					String url  = CommonProperties.getBaseURL() + CommonProperties.getUiPath();
					String base = !getProfile().equalsIgnoreCase("local")?CommonProperties.getPublicBaseURL():"http://localhost:8080/";
					String logoURL  = base + CommonProperties.getContextPath()+"emailTemplate/Logo.png";
					String message = emailConfig.forgotPasswordMail(emailAddressFinal,msg,firstnameFinal,passcode,linkFinal,url,logoURL);
					sender.sendFromCustomerService(emailAddressFinal, emailConfig.getForgotPasswordSubject(), message);
				} catch (Exception e) {
					logger.error("Error while sending Email at forgotPasswordMail",e);
				} 
				finally{
					emailThread.interrupt();
					emailThread = null;
				}
			}
		};
		emailThread.start();
		return true;
	}
	
    public static boolean forgotPassword(EmailObject emailObj) {
		emailThread = new Thread(){
			@Override
			public void run() {
				try {
					EmailConfiguration emailConfig = new EmailConfiguration();
					EmailSender sender = new EmailSender();
					String message = emailConfig.getForgotPasswordEmailMessage(emailObj);
					sender.sendFromCustomerService(emailObj.getRecieveEmail(), emailConfig.getForgotPasswordSubject(), message);
				} catch (Exception e) {
					logger.error("forgotPassword EmailManger ",e);
				} 
				finally{
					emailThread.interrupt();
					emailThread = null;
				}
			}
		};
		emailThread.start();
		return true;
	}

	public static boolean genertesubscribeUserMail(Subscriptionuser subscriptionuser) {
		final String emailAddressFinal = MessageConstants.CLIENTEMAIL;
		emailThread = new Thread(){
			@Override
			public void run() {
				try {					
					EmailConfiguration emailConfig = new EmailConfiguration();
					EmailSender sender = new EmailSender();
					String url  = CommonProperties.getBaseURL() + CommonProperties.getUiPath();
					String base = !getProfile().equalsIgnoreCase("local")?CommonProperties.getPublicBaseURL():"http://localhost:8080/";
					String logoURL  = base + CommonProperties.getContextPath()+"emailTemplate/Logo.png";
					String message = emailConfig.genertesubscribeUserEmailMessage(subscriptionuser,url,logoURL);
					sender.sendFromCustomerService(emailAddressFinal, emailConfig.getSubscribeUserSubject(), message);
				} catch (Exception e) {
					logger.error("genertesubscribeUserMail ",e);
				} 
				finally{
					emailThread.interrupt();
					emailThread = null;
				}
			}
		};
		emailThread.start();
		return true;
		
	}

	public static boolean generteRegistrationMail(User user, String pathway, String link1, String link2, String link3, String link4, int origin) {
		emailThread = new Thread(){
			@Override
			public void run() { 
				try {
					
					EmailSender sender = new EmailSender();
					String url  = CommonProperties.getBaseURL() + CommonProperties.getUiPath();
					String base = !getProfile().equalsIgnoreCase("local")?CommonProperties.getPublicBaseURL():"http://localhost:8080/";
					String logoURL  = base + CommonProperties.getContextPath()+"emailTemplate/Logo.png";
					if(origin == 1)
 					{
					String message = emailConfig1.generteJobseekerRegistrationMail(user, pathway, link1, link2, link3,link4, url, logoURL);					
					sender.sendFromCustomerService(user.getEmail(), emailConfig1.getJobseekerRegistrationSubject(), message);
 					}
					if(origin == 2)
 					{
						String message = emailConfig1.generteEmployerRegistrationMail(user, link1, link2, url, logoURL);					
						sender.sendFromCustomerService(user.getEmail(), emailConfig1.getEmployerRegistrationSubject(), message);
 					}
				} catch (Exception e) {
					logger.error("Subscripton upgrade Email Manager",e);
				} 
			}
		};
		emailThread.start();
    	return true;
		
	}

	public static boolean genertePostJobEmail(JobseekerPathwayView jobseeker, PostJob postJob, String link) {
		emailThread = new Thread(){
			@Override
			public void run() { 
				try {					
					
					EmailSender sender = new EmailSender();
					String url  = CommonProperties.getBaseURL() + CommonProperties.getUiPath();
					String base = !getProfile().equalsIgnoreCase("local")?CommonProperties.getPublicBaseURL():"http://localhost:8080/";
					String logoURL  = base + CommonProperties.getContextPath()+"emailTemplate/Logo.png";
					
					String message = emailConfig1.genertePostJobEmail(jobseeker, link, url, logoURL);						
					sender.sendFromCustomerService(jobseeker.getEmail(),"A "+postJob.getPathway()+" "+ emailConfig1.getPostJobEmailSubject(), message);
 					
					
				} catch (Exception e) {
					logger.error("genertePostJobEmail",e);
				} 
			}
		};
		emailThread.start();
    	return true;
		
	}

	public static boolean generteJobInviteEmail(PostJob postJob, Jobseeker jobseeker, String link) {
		emailThread = new Thread(){
			@Override
			public void run() { 
				try {					
					
					EmailSender sender = new EmailSender();
					String url  = CommonProperties.getBaseURL() + CommonProperties.getUiPath();
					String base = !getProfile().equalsIgnoreCase("local")?CommonProperties.getPublicBaseURL():"http://localhost:8080/";
					String logoURL  = base + CommonProperties.getContextPath()+"emailTemplate/Logo.png";
					
					String message = emailConfig1.generteJobInviteEmail(postJob, jobseeker, link, url, logoURL);						
					sender.sendFromCustomerService(jobseeker.getUserInfo().getEmail(), postJob.getEmployer().getEmployerName()+" "+ emailConfig1.getJobInviteEmailSubject(), message);
 					
					
				} catch (Exception e) {
					logger.error("generteJobInviteEmail",e);
				} 
			}
		};
		emailThread.start();
    	return true;
		
	}
     
	
}
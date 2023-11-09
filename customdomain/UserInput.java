package com.obs.customdomain;

import java.util.List;

import com.obs.domain.JobseekerPathway;
import com.obs.domain.JobsekerExperience;

public class UserInput {

	private String userName;

	private String email;

	private String firstName;

	private String lastName;

	private String password;

	private String profileId;

	private String oldPassword;

	private String confirmPassword;

	private int origin;

	private String bio;

	private double lattitude;

	private double longitude;

	private long countryId;

	private String userId;

	private long userType;

	private String countryCode;

	private String phone;

	private boolean marketing;

	private String employerName;

	private String verifyLink;

	private long nationalityId;

	private long employerDataId;

	private String facebookId;

	private String googleId;

	private String profileImage;

	private int referType;

	private String search;

	private int mediaType;

	private String refer;

	private String referlink;

	private List<JobsekerExperience> experience;

	private String location;

	private boolean mentor;

	private boolean training;

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	private List<JobseekerPathway> pathway;

	private String occupation;

	public List<JobseekerPathway> getPathway() {
		return pathway;
	}

	public void setPathway(List<JobseekerPathway> pathway) {
		this.pathway = pathway;
	}

	public List<JobsekerExperience> getExperience() {
		return experience;
	}

	public void setExperience(List<JobsekerExperience> experience) {
		this.experience = experience;
	}

	public long getNationalityId() {
		return nationalityId;
	}

	public void setNationalityId(long nationalityId) {
		this.nationalityId = nationalityId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getProfileId() {
		return profileId;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public int getOrigin() {
		return origin;
	}

	public void setOrigin(int origin) {
		this.origin = origin;
	}

	public double getLattitude() {
		return lattitude;
	}

	public void setLattitude(double lattitude) {
		this.lattitude = lattitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public long getCountryId() {
		return countryId;
	}

	public void setCountryId(long countryId) {
		this.countryId = countryId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public long getUserType() {
		return userType;
	}

	public void setUserType(long userType) {
		this.userType = userType;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public boolean isMarketing() {
		return marketing;
	}

	public void setMarketing(boolean marketing) {
		this.marketing = marketing;
	}

	public String getEmployerName() {
		return employerName;
	}

	public void setEmployerName(String employerName) {
		this.employerName = employerName;
	}

	public String getVerifyLink() {
		return verifyLink;
	}

	public void setVerifyLink(String verifyLink) {
		this.verifyLink = verifyLink;
	}

	public String getFacebookId() {
		return facebookId;
	}

	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}

	public String getGoogleId() {
		return googleId;
	}

	public void setGoogleId(String googleId) {
		this.googleId = googleId;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	public int getReferType() {
		return referType;
	}

	public void setReferType(int referType) {
		this.referType = referType;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public int getMediaType() {
		return mediaType;
	}

	public void setMediaType(int mediaType) {
		this.mediaType = mediaType;
	}

	public String getRefer() {
		return refer;
	}

	public void setRefer(String refer) {
		this.refer = refer;
	}

	public String getReferlink() {
		return referlink;
	}

	public void setReferlink(String referlink) {
		this.referlink = referlink;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public boolean isMentor() {
		return mentor;
	}

	public void setMentor(boolean mentor) {
		this.mentor = mentor;
	}

	public boolean isTraining() {
		return training;
	}

	public void setTraining(boolean training) {
		this.training = training;
	}

	public long getEmployerDataId() {
		return employerDataId;
	}

	public void setEmployerDataId(long employerDataId) {
		this.employerDataId = employerDataId;
	}

}
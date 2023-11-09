package com.obs.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tbl_user")
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", nullable = false, unique = true)
	private String userId;

	private String email;

	private String countryCode;

	@OneToOne(targetEntity = UserType.class)
	@JoinColumn(name = "user_type")
	private UserType userType;

	@JsonIgnore
	private String verifyLink;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
	private Date linkcreatedon;

	@Column(name = "DN_INVALID_ATTEMPT", columnDefinition = "int default 0")
	@JsonIgnore
	private int invalidAttempts = 0;

	@Column(name = "DB_LOCKED", columnDefinition = "tinyint(1) default 0")
	@JsonIgnore
	private boolean locked;

	@Column(name = "DD_LOCKED_ON")
	@JsonIgnore
	private Date lockedOn;

	private double availableToken;

	private long availablePoints;

	private long totalEligblityScore;

	private long totalFollowing;

	public long getTotalFollowing() {
		return totalFollowing;
	}

	public void setTotalFollowing(long totalFollowing) {
		this.totalFollowing = totalFollowing;
	}

	@Column(name = "newUser", columnDefinition = "tinyint(1) default 1")
	private boolean newUser;

	public User() {
		super();
	}

	@OneToOne(targetEntity = AccountStatus.class)
	@JoinColumn(name = "account_status")
	private AccountStatus accountstatus;

	public User(String email, String countryCode, String phone, String userName) {
		super();
		this.email = email;
		this.countryCode = countryCode;
		this.phone = phone;
		this.userName = userName;
	}

	private String userName;

	private String fullName;

	private String profileImage;

	@JsonIgnore
	private String facebookId;

	@JsonIgnore
	private String googleId;

	private int completedStep;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	private String phone;

	@Column(name = "active", columnDefinition = "tinyint(1) default 1")
	private boolean active;

	@Column(name = "deleted", columnDefinition = "tinyint(1) default 0")
	private boolean deleted;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
	private Date createdOn = new Date();

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public AccountStatus getAccountstatus() {
		return accountstatus;
	}

	public void setAccountstatus(AccountStatus accountstatus) {
		this.accountstatus = accountstatus;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getVerifyLink() {
		return verifyLink;
	}

	public void setVerifyLink(String verifyLink) {
		this.verifyLink = verifyLink;
	}

	public int getInvalidAttempts() {
		return invalidAttempts;
	}

	public void setInvalidAttempts(int invalidAttempts) {
		this.invalidAttempts = invalidAttempts;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public String getFacebookId() {
		return facebookId;
	}

	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}

	public Date getLockedOn() {
		return lockedOn;
	}

	public void setLockedOn(Date lockedOn) {
		this.lockedOn = lockedOn;
	}

	public String getGoogleId() {
		return googleId;
	}

	public void setGoogleId(String googleId) {
		this.googleId = googleId;
	}

	public int getCompletedStep() {
		return completedStep;
	}

	public void setCompletedStep(int completedStep) {
		this.completedStep = completedStep;
	}

	public double getAvailableToken() {
		return availableToken;
	}

	public void setAvailableToken(double availableToken) {
		this.availableToken = availableToken;
	}

	public long getAvailablePoints() {
		return availablePoints;
	}

	public void setAvailablePoints(long availablePoints) {
		this.availablePoints = availablePoints;
	}

	public boolean isNewUser() {
		return newUser;
	}

	public void setNewUser(boolean newUser) {
		this.newUser = newUser;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	public long getTotalEligblityScore() {
		return totalEligblityScore;
	}

	public void setTotalEligblityScore(long totalEligblityScore) {
		this.totalEligblityScore = totalEligblityScore;
	}

	public Date getLinkcreatedon() {
		return linkcreatedon;
	}

	public void setLinkcreatedon(Date linkcreatedon) {
		this.linkcreatedon = linkcreatedon;
	}

}

package com.obs.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tbl_usersecurity")
public class UserSecurity implements Serializable { // @JsonInclude(JsonInclude.Include.NON_EMPTY)

	/**
	 * user security
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name="id", nullable = false, unique=true)
	private String userSecurityId;
	
	@OneToOne (targetEntity = User.class, fetch = FetchType.EAGER)
	@JoinColumn (name = "user")
	@JsonIgnore
	private User user;
	
	@JsonIgnore
	@Column(name = "password")
	private String password;
	
	@JsonIgnore
	private String socialPassword;
	
	@JsonIgnore
	@Column(name="DB_SOCIAL", columnDefinition="tinyint(1) default 0")
	private boolean social;
	
	@Column(name="username")
	private String userName;

	public String getUserSecurityId() {
		return userSecurityId;
	}

	public void setUserSecurityId(String userSecurityId) {
		this.userSecurityId = userSecurityId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSocialPassword() {
		return socialPassword;
	}

	public void setSocialPassword(String socialPassword) {
		this.socialPassword = socialPassword;
	}

	public boolean isSocial() {
		return social;
	}

	public void setSocial(boolean social) {
		this.social = social;
	}
}

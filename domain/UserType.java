package com.obs.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tbl_user_type")
public class UserType implements Serializable {

	/**
	 * user type 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="DN_TYPE_ID" , nullable = false )
	private Long userTypeId;

	@Column(name="DC_TYPE_NAME")
	private String userTypeName;

	public Long getUserTypeId() {
		return userTypeId;
	}

	public void setUserTypeId(Long userTypeId) {
		this.userTypeId = userTypeId;
	}

	public String getUserTypeName() {
		return userTypeName;
	}

	public void setUserTypeName(String userTypeName) {
		this.userTypeName = userTypeName;
	}

	public UserType() {
		super();
	}

	public UserType(Long userTypeId, String userTypeName) {
		super();
		this.userTypeId = userTypeId;
		this.userTypeName = userTypeName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}

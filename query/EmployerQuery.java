package com.obs.query;

public class EmployerQuery {

	public static final String POSTJOBBYINVITE = "from PostJob where inviteLink=:v";
	public static final String ALLPOSTJOBBYINVITE = "select inviteLink from PostJob";
	public static final String EMPLOYERBYUSERID = "from Employer where userInfo.userId =:uid";

	private EmployerQuery() {}
}

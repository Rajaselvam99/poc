package com.obs.query;

public class UserQuery {


	private UserQuery() {}

	public static final String USERBYID = "select u from User u where u.userId=:uid";
	public static final String USERSECURITYBYUSERNAME = "select u from UserSecurity u where u.user.userId=:nm";
	public static final String USERBYUSERNAMEOREMAILORMOBILE = "from User where (userName=:nm or email= :nm or CONCAT(countryCode,phone) =:nm or phone =:nm) ";
	public static final String SUBSCRIPTIONUSERBYEMAILANDUSERTYPE = "from Subscriptionuser where email=:em and userType.userTypeId=:t";
	public static final String USERBYUSERNAMEOREMAILORMOBILEANDUSERTYPE = "from User where (userName=:nm or email= :nm or CONCAT(countryCode,phone) =:nm or phone =:nm or facebookId =:nm or googleId=:nm) and userType.userTypeId=:t";
	public static final String USERBYVERIFYLINK = "from User where verifyLink =:v";
	public static final String USERBYUSERNAMEOREMAILORMOBILEORSOCIAL = "from User where (userName=:nm or email= :nm or CONCAT(countryCode,phone) =:nm or phone =:nm or facebookId =:nm or googleId=:nm) ";
	public static final String ALLCOUNTRY = "from Country";
}

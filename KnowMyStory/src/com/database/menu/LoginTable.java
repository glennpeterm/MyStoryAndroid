/*
 * Know My Story 
 */
package com.database.menu;

public interface LoginTable extends Table {

	public static final String TABLE_NAME = "t_userlogin";

	public static final String PROVIDER = "login_type";
	public static final String FIRST_NAME = "first_name";
	public static final String LAST_NAME = "last_name";
	public static final String PROFILE_PIC = "profile_pic";
	public static final String TOKEN = "token";
	public static final String EMAIL = "email";
	public static final String ADDRESS = "address";
	public static final String GENDER = "gender";
	public static final String DOB = "dob";
	public static final String CITY = "city";
	public static final String STATE = "state";
	public static final String COUNTRY = "country";
	public static final String PHONE = "phone";
	public static final String ZIPCODE = "zipcode";
	public static final String ACTIVE = "isactive";
	public static final String LOGOUT = "logout";
	public static final String AUTH_TOKEN = "authToken";
	public static final String LANGUAGE = "language";

}

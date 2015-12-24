/*
 * Know My Story 
 */
package com.model;

import android.os.Parcel;
import android.os.Parcelable;

public class UserDetail implements Parcelable {

	private String type;	
	private String firstName,lastName;
	private String profilepic;
	private String email;
	private String token;
	private String address;
	private String gender;
	private String dob;
	private String city;
	private String state;
	private String country;
	private String phone;
	private String zipCode;
	private String active;
	private String authtoken;
	private String logout;
	
	

	public UserDetail() {

	}
	
	/*public UserDetail(String type, String firstName, String lastName,
			String profilepic, String email,String token) {
		super();
		this.type = type;
		this.firstName = firstName;
		this.lastName = lastName;
		this.profilepic = profilepic;
		this.email = email;
		this.token=token;
	}*/





	public UserDetail(String type, String firstName, String lastName,
			String profilepic, String email, String token, String address,
			String gender, String dob, String city, String state, String country,String phone,String zipCode,String active,String authtoken) {
		super();
		this.type = type;
		this.firstName = firstName;
		this.lastName = lastName;
		this.profilepic = profilepic;
		this.email = email;
		this.token = token;
		this.address = address;
		this.gender = gender;
		this.dob = dob;
		this.city = city;
		this.state = state;
		this.country = country;
		this.phone=phone;
		this.zipCode=zipCode;
		this.active=active;
		this.authtoken=authtoken;
		//this.logout=logout;
	}

	@Override
	public int describeContents() {

		return 0;
	}

	@Override
	public void writeToParcel( Parcel dest, int flags ) {

		dest.writeValue( getType() );
		dest.writeValue( getFirstName() );
		dest.writeValue( getLastName() );
		dest.writeValue( getProfilepic() );
		dest.writeValue( getEmail() );
		dest.writeValue( getToken() );
		dest.writeValue( getAddress());
		dest.writeValue( getGender());
		dest.writeValue( getDob());
		dest.writeValue( getCity());
		dest.writeValue( getState());
		dest.writeValue( getCountry());		
		dest.writeValue( getPhone());
		dest.writeValue( getZipCode());
		dest.writeValue( getActive());
		dest.writeValue( getAuthtoken());
		dest.writeValue( getLogout());
		
		

	}

	private UserDetail(Parcel source) {

		setType( (String) source.readValue( Integer.class.getClassLoader() ) );
		setFirstName( (String) source.readValue( String.class.getClassLoader() ) );
		setLastName( (String) source.readValue( String.class.getClassLoader() ) );
		setProfilepic( (String) source.readValue( String.class.getClassLoader() ) );
		setEmail( (String) source.readValue( String.class.getClassLoader() ) );
		setToken( (String) source.readValue( String.class.getClassLoader() ));
		setAddress( (String) source.readValue( String.class.getClassLoader() ));
		setGender( (String) source.readValue( String.class.getClassLoader() ));
		setDob( (String) source.readValue( String.class.getClassLoader() ));
		setCity( (String) source.readValue( String.class.getClassLoader() ));
		setState( (String) source.readValue( String.class.getClassLoader() ));
		setCountry( (String) source.readValue( String.class.getClassLoader() ));		
		setPhone( (String) source.readValue( String.class.getClassLoader() ));
		setZipCode((String) source.readValue( String.class.getClassLoader() ));
		setActive( (String) source.readValue( String.class.getClassLoader() ));
		setAuthtoken( (String) source.readValue( String.class.getClassLoader() ));
		setLogout( (String) source.readValue( String.class.getClassLoader() ));
		
	}

	public static final Parcelable.Creator<UserDetail> CREATOR = new Parcelable.Creator<UserDetail>() {

		public UserDetail createFromParcel( Parcel in ) {

			return new UserDetail( in );
		}

		public UserDetail[] newArray( int size ) {

			return new UserDetail[size];
		}
	};

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
		System.out.println("UserDetails:firstname: "+firstName);
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getProfilepic() {
		System.out.println("UserDetails: get profile pic: "+profilepic);
		return profilepic;
	}

	public void setProfilepic(String profilepic) {
		
		this.profilepic = profilepic;
		System.out.println("UserDetails:set profile pic: "+profilepic);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAuthtoken() {
		return authtoken;
	}

	public void setAuthtoken(String authtoken) {
		this.authtoken = authtoken;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	public String getLogout() {
		return logout;
	}

	public void setLogout(String logout) {
		this.logout = logout;
	}

}
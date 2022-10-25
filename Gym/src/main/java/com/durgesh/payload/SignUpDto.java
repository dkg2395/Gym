package com.durgesh.payload;

import lombok.Data;

@Data
public class SignUpDto {
	private String firstName;
	private String lastName;
	private String username;
	private String mobileNumber;
	private String email;
	private String image;
	private String state;
	private String country;
	private String city;
	private String zipcode;
	private String password;
}

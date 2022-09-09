package com.pradeep.dtos;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
	private Long userId;
	private String firstName;
	private String lastName;
	private String email;
	private String encryptedEmail;
	private String username;
	private String password;
	private String userStatus;
	private String guid;
	private Boolean isNotlocked=Boolean.TRUE;
	private Date lastLogin;
	private String profilePic;	
	private UserAddressDto useraddress;
}

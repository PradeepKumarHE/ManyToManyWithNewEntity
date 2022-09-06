package com.pradeep.dtos;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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

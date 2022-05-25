package com.pradeep.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.CascadeType;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "userId")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	private String firstName;
	private String lastName;
	private String email;
	private String encryptedEmail;
	private String username;
	private String password;
	private String userStatus;
	private String guid;
	private Boolean isNotlocked;
	private Date lastLogin;
	private String profilePic;
	@OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
	Set<CompanyUserMapping> companyUserMapping;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id")
	private UserAddress address;
}

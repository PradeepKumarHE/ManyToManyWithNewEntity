package com.pradeep.domain;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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
	private String userStatus;
	private String username;
	private String email;
	private String encryptedEmail;
	private String password;
	private String guid;
	
	@OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
	Set<CompanyUserMapping> companyUserMapping;
}

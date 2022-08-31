package com.pradeep.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity

public class UserConformation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userConformationId;
	private String encryptedEmail;
	private Integer code;
	private Long generatedTime;	
}

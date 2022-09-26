package com.pradeep.domain;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.vladmihalcea.hibernate.type.json.JsonType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@TypeDef(name = "json", typeClass = JsonType.class)
public class UserConformation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userConformationId;
	private Long userId;
	private String tinyLink;
	private String verificationContext;
	private LocalDateTime linkCreationDateTime;
	@Type(type = "json")
	@Column(columnDefinition = "json")
	private Map<String, Object> details = new HashMap<>();
}

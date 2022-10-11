package com.pradeep.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BooleanTest {
	
	@NotNull
    @Pattern(regexp = "^true$|^false$", message = "allowed input: true or false")
    private String booleanField;
}

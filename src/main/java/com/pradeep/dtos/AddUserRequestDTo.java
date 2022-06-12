package com.pradeep.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddUserRequestDTo {
    private String email;
    private String encryptedEmail;
    private String username;
    private String userStatus;
    private String role;
    private String [] authorities;
    private boolean isExternal;
    private Long customerId;
}

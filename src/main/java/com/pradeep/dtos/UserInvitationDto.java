package com.pradeep.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInvitationDto {
	private String invitee;
	private String inviter;
}

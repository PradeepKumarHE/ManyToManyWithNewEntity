package com.pradeep.controllers;

import com.pradeep.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pradeep.domain.ExternalCompany;
import com.pradeep.domain.User;
import com.pradeep.enums.UserVerificationContexts;
import com.pradeep.exceptions.ResourceNotFoundException;
import com.pradeep.exceptions.UserVerificationException;
import com.pradeep.responses.Response1;
import com.pradeep.responses.Response2;
import com.pradeep.service.IUserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private IUserService userService;
	
	@GetMapping("/{userid}")
	public ResponseEntity<User> getUserinfoById(@PathVariable("userid") Long userid) throws ResourceNotFoundException {
		User savedCompanyUserMapping = userService.getUserinfoById(userid);
		return new ResponseEntity<User>(savedCompanyUserMapping, HttpStatus.OK);
	}
	
	@GetMapping("/{userid}/associatedcompanies")
	public ResponseEntity<List<UserAssociatedCompanyDto>> getUserAssociatedCompaniesById(@PathVariable("userid") Long userid) throws ResourceNotFoundException {
		List<UserAssociatedCompanyDto> userAssociatedCompanyDto = userService.getUserAssociatedCompaniesById(userid);
		return new ResponseEntity<List<UserAssociatedCompanyDto>>(userAssociatedCompanyDto, HttpStatus.OK);
	}
	
	@PostMapping("/invite")
	public ResponseEntity<Response1> inviteUserById(@RequestBody UserInvitationDto userInvitationDto) throws ResourceNotFoundException {
		Response1 response = userService.inviteUserById(userInvitationDto);
		return new ResponseEntity<Response1>(response, HttpStatus.OK);
	}	
	
	@PostMapping("/register-invite-status/{encryptedemail}")
	public ResponseEntity<Response2> verifyRegistrationInvitationStatus(@PathVariable("encryptedemail") String encryptedEmail,@RequestBody ValidateUserDto validateUserDto) throws UserVerificationException {
		Response2 response =  userService.verifyRegistrationInvitationStatus(encryptedEmail,validateUserDto.getTinystring(),UserVerificationContexts.REGISTRATION_INVITE);
		return new ResponseEntity<Response2>(response, HttpStatus.OK);
	}
	
	@PostMapping("/mobile/{encryptedemail}")
	public ResponseEntity<Response1> updateMobile(@PathVariable("encryptedemail") String encryptedEmail,@RequestBody UpdateMobileDto updateMobileDto) throws ResourceNotFoundException, UserVerificationException {
		Response1 response = userService.updateMobile(encryptedEmail,updateMobileDto);
		return new ResponseEntity<Response1>(response, HttpStatus.OK);
	}
	
	@PostMapping("/otp/{encryptedemail}")
	public ResponseEntity<Response1> validateOTP(@PathVariable("encryptedemail") String encryptedEmail,@RequestBody ValidateUserDto validateUserDto) throws UserVerificationException {
		Response1 response = userService.validateOTP(encryptedEmail,validateUserDto.getTinystring(),UserVerificationContexts.OTP);
		return new ResponseEntity<Response1>(response, HttpStatus.OK);
	}
	
	@PatchMapping("/password")// taking company id here to make active mapping table
	public ResponseEntity<Response1> updatePassword(@RequestBody UpdatePasswordDto updatePasswordDto) throws ResourceNotFoundException {
		Response1 response = userService.updatePassword(updatePasswordDto);
		return new ResponseEntity<Response1>(response, HttpStatus.OK);
	}
}
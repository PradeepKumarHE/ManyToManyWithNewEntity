package com.pradeep.service;

import com.pradeep.domain.User;
import com.pradeep.dtos.UserDto;
import com.pradeep.exceptions.ResourceNotFoundException;

public interface IUserService {

	public User findByEmail(String email) throws ResourceNotFoundException;

	public UserDto getUserById(Long userId) throws ResourceNotFoundException;

}

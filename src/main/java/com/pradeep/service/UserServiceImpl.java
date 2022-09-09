package com.pradeep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pradeep.domain.ExternalCompany;
import com.pradeep.domain.User;
import com.pradeep.repositories.IUserRepository;

@Service
public class UserServiceImpl implements IUserService {
	
	@Autowired
	IUserRepository userRepository;

	@Override
	public User getUserinfoById(Long userid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExternalCompany getUserAssociatedCompaniesById(Long userid) {
		// TODO Auto-generated method stub
		return null;
	}

	
}

package com.pradeep.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pradeep.domain.User;

@Repository
public interface IUserRepository extends JpaRepository <User, Long>{
	public Optional<User> findByEmail(String email);
	
}
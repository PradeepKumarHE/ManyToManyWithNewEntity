package com.pradeep.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pradeep.domain.UserConformation;

@Repository
public interface IUserConformationRepository extends JpaRepository <UserConformation, Long>{

	Optional<UserConformation> findByUserIdAndVerificationContext(Long userId,String verificationContext);
	
	Optional<UserConformation> findByTinyLinkAndVerificationContext(String tinyLink,String verificationContext);

}

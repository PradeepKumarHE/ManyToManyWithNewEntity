package com.pradeep.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pradeep.domain.UserConformation;
import com.pradeep.enums.UserVerificationContexts;

@Repository
public interface IUserConformationRepository extends JpaRepository <UserConformation, Long>{

	Optional<UserConformation> findByEncryptedEmailAndVerificationContext(String encryptedEmail,UserVerificationContexts verificationContext);
	
	Optional<UserConformation> findByEncryptedEmailAndVerificationContextAndTinyString(String encryptedEmail,UserVerificationContexts verificationContext,String tinyLink);
	
	//Optional<UserConformation> findByTinyLinkAndVerificationContext(String tinyLink,String verificationContext);

	//Optional<UserConformation> findByTinyLinkAndVerificationContextAndUserId(String otp, String string, Long userId);

}

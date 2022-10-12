package com.pradeep.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pradeep.domain.Company;
import com.pradeep.domain.CompanyUserMapping;
import com.pradeep.domain.User;


@Repository
public interface ICompanyUserMappingRepository extends JpaRepository <CompanyUserMapping, Long>{

	Optional<CompanyUserMapping> findByCompanyAndUser(Company company, User user);

	@Query("select cum,c from CompanyUserMapping cum join cum.company c JOIN cum.user u where cum.company.companyStatus=:companyStatus")
	List<CompanyUserMapping> getCompanyListByCompanyStatus(@Param("companyStatus") Integer companystatus);
	
	@Query("select cum from CompanyUserMapping cum join cum.company c JOIN cum.user u where cum.company.companyId=:companyId")
   	List<CompanyUserMapping> getUserMappingByCompanyId(@Param("companyId") Long companyId);
	
	@Query("select cum from CompanyUserMapping cum where cum.user.userId=:userId")
   	List<CompanyUserMapping> getUserMappingByUserId(@Param("userId") Long userId);

	@Query("select cum from CompanyUserMapping cum where cum.company.companyId=:companyId and cum.user.userId=:userId")
	Optional<CompanyUserMapping> findByCompanyIdAndUserId(@Param("companyId") Long companyId, @Param("userId") Long userId);

	@Query("select (count(c) > 0) from CompanyUserMapping c where c.user.userId=:userId and c.isActive=:isActive")
	boolean findByUserIdAndIsActive(@Param("userId") Long userId,@Param("isActive") Boolean isActive);

	List<CompanyUserMapping> findByUser_UserIdAndIsActive(Long userId, Boolean isActive);
}

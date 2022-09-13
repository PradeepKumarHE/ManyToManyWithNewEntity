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

	@Query("SELECT cum,c FROM CompanyUserMapping cum JOIN cum.company c JOIN cum.user u WHERE cum.company.companyStatus=:companyStatus")
	List<CompanyUserMapping> getCompanyListByCompanyStatus(@Param("companyStatus") Integer companystatus);
	
	@Query("SELECT cum FROM CompanyUserMapping cum JOIN cum.company c JOIN cum.user u WHERE cum.company.companyId=:companyId")
   	public List<CompanyUserMapping> getUserMappingByCompanyId(@Param("companyId") Long companyId);
}

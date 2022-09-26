package com.pradeep.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pradeep.domain.ExternalCompany;

@Repository
public interface IExternalCompanyRepository extends JpaRepository <ExternalCompany, Long>{

	@Query("SELECT ec FROM ExternalCompany ec WHERE ec.companyName=:companyName AND ec.parentCompany.companyId=:companyId")
	Optional<ExternalCompany> findByCompanyNameAndParentCompanyId(@Param("companyName") String companyName,@Param("companyId") Long companyId);
}

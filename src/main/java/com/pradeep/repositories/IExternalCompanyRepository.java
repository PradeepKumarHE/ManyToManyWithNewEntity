package com.pradeep.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pradeep.domain.ExternalCompany;

@Repository
public interface IExternalCompanyRepository extends JpaRepository <ExternalCompany, Long>{

	@Query("select ec from ExternalCompany ec where ec.companyName=:companyName and ec.parentCompany.companyId=:companyId")
	Optional<ExternalCompany> findByCompanyNameAndParentCompanyId(@Param("companyName") String companyName,@Param("companyId") Long companyId);

	@Query("select e from ExternalCompany e where e.parentCompany.companyId=:companyId")
	List<ExternalCompany> findByParentCompanyId(@Param("companyId") Long companyId);
}

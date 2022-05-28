package com.pradeep.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pradeep.domain.Company;
import com.pradeep.domain.CompanyUserMapping;
import com.pradeep.domain.User;


@Repository
public interface ICompanyUserMappingRepository extends JpaRepository <CompanyUserMapping, Long>{

	Optional<CompanyUserMapping> findByCompanyAndUser(Company company, User user2);

    @Query("select u, c from User u inner join u.companyUserMapping cu inner join cu.company c where c.companyStatus <> 3")
    List<CompanyUserMapping> listPotentialCompanies();
}

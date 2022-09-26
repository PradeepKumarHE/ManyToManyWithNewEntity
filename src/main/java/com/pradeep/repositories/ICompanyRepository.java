package com.pradeep.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pradeep.domain.Company;


@Repository
public interface ICompanyRepository extends JpaRepository <Company, Long>{

    List<Company> findBycompanyStatus(Integer companyStatus);   
    
    Optional<Company> findByCompanyEmailDomain(String companyEmailDomain);
    
}

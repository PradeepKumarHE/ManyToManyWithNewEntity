package com.pradeep.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pradeep.domain.Company;
import com.pradeep.domain.CompanyUserMapping;

import java.util.List;


@Repository
public interface ICompanyRepository extends JpaRepository <Company, Long>{

    public List<Company> findBycompanyStatus(Integer companyStatus);    
    
    
}

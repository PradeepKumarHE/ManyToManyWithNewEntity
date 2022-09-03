package com.pradeep.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pradeep.domain.Company;

import java.util.List;


@Repository
public interface ICompanyRepository extends JpaRepository <Company, Long>{

    public List<Company> findBycompanyStatus(Integer companyStatus);

    @Query("SELECT cum FROM CompanyUserMapping cum JOIN cum.company c JOIN cum.user u WHERE cum.company.companyStatus=0")
	public List<Company> getCompanyListByCompanyStatus();
}

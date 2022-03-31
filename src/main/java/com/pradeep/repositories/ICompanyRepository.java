package com.pradeep.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pradeep.domain.Company;


@Repository
public interface ICompanyRepository extends JpaRepository <Company, Long>{

}

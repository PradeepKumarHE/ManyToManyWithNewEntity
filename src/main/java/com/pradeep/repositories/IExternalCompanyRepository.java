package com.pradeep.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pradeep.domain.ExternalCompany;

@Repository
public interface IExternalCompanyRepository extends JpaRepository <ExternalCompany, Long>{

}

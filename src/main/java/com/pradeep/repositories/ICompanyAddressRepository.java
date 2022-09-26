package com.pradeep.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pradeep.domain.CompanyAddress;

@Repository
public interface ICompanyAddressRepository extends JpaRepository <CompanyAddress, Long>{

}

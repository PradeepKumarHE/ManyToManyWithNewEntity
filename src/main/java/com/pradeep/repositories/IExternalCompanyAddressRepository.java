package com.pradeep.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pradeep.domain.ExternalCompanyAddress;

@Repository
public interface IExternalCompanyAddressRepository extends JpaRepository <ExternalCompanyAddress, Long>{

}

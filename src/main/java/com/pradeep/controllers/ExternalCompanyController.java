package com.pradeep.controllers;

import com.pradeep.domain.ExternalCompany;
import com.pradeep.exceptions.ResourceExistsException;
import com.pradeep.exceptions.ResourceNotFoundException;
import com.pradeep.service.ICompanyService;
import com.pradeep.service.IExternalCompanyServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/externalcompanies")
public class ExternalCompanyController {

    @Autowired
    private IExternalCompanyServices externalCompanyServices;

    @GetMapping("/{externalcompanyid}")
    public ResponseEntity<ExternalCompany> getExternalCompaniesByCompanyId(@PathVariable("externalcompanyid") Long externalcompanyid) throws ResourceNotFoundException {
        ExternalCompany savedExternalCompany = externalCompanyServices.getExternalCompanyById(externalcompanyid);
        return new ResponseEntity<ExternalCompany>(savedExternalCompany, HttpStatus.OK);
    }
}

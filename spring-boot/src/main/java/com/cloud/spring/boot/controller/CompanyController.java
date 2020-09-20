package com.cloud.spring.boot.controller;

import com.cloud.spring.boot.entity.Company;
import com.cloud.spring.boot.common.exception.ApplicationException;
import com.cloud.spring.boot.controller.modal.request.CreateCompanyRequest;
import com.cloud.spring.boot.controller.modal.request.UpdateCompanyRequest;
import com.cloud.spring.boot.common.util.APIStatus;
import com.cloud.spring.boot.common.util.RestAPIResponse;
import com.cloud.spring.boot.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("api/company")
public class CompanyController extends AbstracBaseController {
    @Autowired
    CompanyService companyService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<RestAPIResponse> createCompany(
            @RequestBody CreateCompanyRequest companyRequest,
            HttpServletRequest request
    ) {

        Company company = new Company();
        company.setName(companyRequest.getName());
        company.setAddress(companyRequest.getAddress());
        company.setEmail(companyRequest.getEmail());
        company.setDescriptions(companyRequest.getDescriptions());
        company.setCreateDate(new Date());
        companyService.saveCompany(company);
        return responseUtil.successResponse(company);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<RestAPIResponse> updateCompany(
            @PathVariable Long id,
            @RequestBody UpdateCompanyRequest companyRequest,
            HttpServletRequest request
    ) {
        Company company = companyService.getCompany(id);
        if (company == null) {
            throw new ApplicationException(APIStatus.ERR_NOT_FOUND);
        }
        //  Company company= new Company();
        company.setName(companyRequest.getName());
        company.setAddress(companyRequest.getAddress());
        company.setEmail(companyRequest.getEmail());
        company.setDescriptions(companyRequest.getDescriptions());
        company.setUpdateDate(new Date());
        companyService.saveCompany(company);
        return responseUtil.successResponse(company);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<RestAPIResponse> deleteCompany(
            @PathVariable Long id,
            HttpServletRequest request
    ) {
        Company company = companyService.getCompany(id);
        if (company == null) {
            throw new ApplicationException(APIStatus.ERR_NOT_FOUND);
        }
        companyService.deleteCompany(company);
        return responseUtil.successResponse("OK");
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<RestAPIResponse> getCompany(
            @PathVariable Long id,
            HttpServletRequest request
    ) {
        Company company = companyService.getCompany(id);
        if (company == null) {
            throw new ApplicationException(APIStatus.ERR_NOT_FOUND);
        }

        return responseUtil.successResponse(company);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<RestAPIResponse> getAllCompany(
            HttpServletRequest request

    ) {
      List<Company> companies = companyService.getAllCompany();
      if (companies.isEmpty()) {
            throw new ApplicationException(APIStatus.NO_RESULT);
        }
        return responseUtil.successResponse(companies);


    }

    @RequestMapping(path= "/search",method = RequestMethod.GET)
    public ResponseEntity<RestAPIResponse> getAllCompanyByName(
            @RequestParam(value = "key_words") String keyWords,
            HttpServletRequest request
    ) {
        List<Company> companies = companyService.getAllByName(keyWords);
        if (companies.isEmpty()) {
            throw new ApplicationException(APIStatus.NO_RESULT);
        }
        return responseUtil.successResponse(companies);
    }
    @RequestMapping(path="/search_like",method = RequestMethod.GET)
    public ResponseEntity<RestAPIResponse>getAllByNameLike(
            @RequestParam( value = "key_words") String keyWords,
            HttpServletRequest request

    ) {
        List<Company> companies = companyService.getAllByNameLike(keyWords);
        if (companies.isEmpty()) {
            throw new ApplicationException(APIStatus.NO_RESULT);
        }
        return responseUtil.successResponse(companies);
    }
    @RequestMapping(path = "/page", method = RequestMethod.GET)
    public ResponseEntity<RestAPIResponse> getAllCompanyPage(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "5") int size,
            @RequestParam(name = "search", defaultValue = "") String searchString,
            @RequestParam(name = "sort", defaultValue = "true") boolean sort,
            @RequestParam(name = "sort_string", defaultValue = "email") String sortString) {

        Page<Company> companies = companyService.pageCompany(searchString, page, size, sort, sortString);
        return responseUtil.successResponse(companies);
    }
}

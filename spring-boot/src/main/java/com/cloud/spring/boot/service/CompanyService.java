package com.cloud.spring.boot.service;

import com.cloud.spring.boot.entity.Company;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CompanyService {
    Company saveCompany(Company company);
    Company getCompany(Long id);
    void deleteCompany(Company company);
    List<Company> getAllCompany();
    List<Company> getAllByName(String keyWords);
   List<Company> getAllByNameLike(String keyWords);
   Page<Company> pageCompany (String searchString, int page, int size, boolean sort, String sortString);

}

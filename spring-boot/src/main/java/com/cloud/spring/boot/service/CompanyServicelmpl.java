package com.cloud.spring.boot.service;

import com.cloud.spring.boot.entity.Company;
import com.cloud.spring.boot.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class CompanyServicelmpl implements CompanyService {

    @Autowired
    CompanyRepository companyReposition;
    @Override
    public Company saveCompany(Company company) {
        return companyReposition.save(company);//post and put
    }

    @Override
    public Company getCompany(Long id) {
        return companyReposition.findOneById(id);//by id
    }

    @Override
    public void deleteCompany(Company company) {
        companyReposition.delete(company);//delete
    }
    @Override
    public List<Company> getAllCompany() {
        return (List<Company>) companyReposition.findAll();//get all
    }

    @Override
    public List<Company> getAllByName(String keyWords) {
        return (List<Company>) companyReposition.findAllByName(keyWords);
    }

    @Override
    public List<Company> getAllByNameLike(String keyWords) {
        return (List<Company>) companyReposition.findByNameLike('%' + keyWords + '%');
    }

    @Override
    public Page<Company> pageCompany(String searchString, int page, int size, boolean sort, String sortString) {
        Sort.Direction direction;
        if (sort) {
            direction = Sort.Direction.DESC;
        } else {
            direction = Sort.Direction.ASC;
        }

        String properties = "";
        switch (sortString) {
            case "name":
                properties = "name";
                break;
            case "descriptions":
                properties = "descriptions";
                break;
            default:
                properties = "email";
                break;
        }
        Pageable pageable = PageRequest.of(page-1, size, direction, properties);

        return companyReposition.listCompany("%" + searchString + "%", pageable);
    }

}

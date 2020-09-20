package com.cloud.spring.boot.service;

import com.cloud.spring.boot.entity.Address;
import com.cloud.spring.boot.entity.Company;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AddressService {
    Address saveAddress(Address address);

    Address getAddress(Long id);

    void deleteAddress(Address address);

    List<Address> getAllAddress();

    List<Address> getAllByAddress1(String keyWords);

    List<Address> getAllByAddress1Like(String keyWords);

    Page<Address> pageAddress (String searchString, int page, int size, boolean sortAsc, String sortString);

}

package com.cloud.spring.boot.service;

import com.cloud.spring.boot.entity.Address;
import com.cloud.spring.boot.entity.Company;
import com.cloud.spring.boot.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServicelmpl implements AddressService {

    @Autowired
    AddressRepository addressReposition;
    @Override
    public Address saveAddress(Address address) {
        return addressReposition.save(address);//post and put
    }

    @Override
    public Address getAddress(Long id) {
        return addressReposition.findOneById(id);//by id
    }

    @Override
    public void deleteAddress(Address address) {
        addressReposition.delete(address);//delete
    }
    @Override
    public List<Address> getAllAddress() {
        return (List<Address>) addressReposition.findAll();//get all
    }
    @Override
    public List<Address> getAllByAddress1(String keyWords) {
        return (List<Address>) addressReposition.findAllByAddress1(keyWords);
    }

    @Override
    public List<Address> getAllByAddress1Like(String keyWords) {
        return (List<Address>) addressReposition.findByAddress1Like('%' + keyWords + '%');
    }

    @Override
    public Page<Address> pageAddress(String searchString, int page, int size, boolean sortAsc, String sortString) {
        Sort.Direction direction;
        if (sortAsc) {
            direction = Sort.Direction.DESC;
        } else {
            direction = Sort.Direction.ASC;
        }

        String properties = "";
        switch (sortString) {
            case "address1":
                properties = "address1";
                break;
            case "address2":
                properties = "address2";
                break;
            default:
                properties = "createDate";
                break;
        }
        Pageable pageable = PageRequest.of(page-1, size, direction, properties);

        return addressReposition.listAddress("%" + searchString + "%", pageable);
    }


}

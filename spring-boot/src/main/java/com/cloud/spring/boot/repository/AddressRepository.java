package com.cloud.spring.boot.repository;

import com.cloud.spring.boot.entity.Address;
import com.cloud.spring.boot.entity.Company;
import com.cloud.spring.boot.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AddressRepository extends PagingAndSortingRepository<Address,Long> {
    Address findOneById(Long id);
    List<Address> findAllByAddress1(String keyWords);

    @Query("SELECT c FROM Address c WHERE c.address1 LIKE :key_words or c.address2 LIKE :key_words or c.city LIKE :key_words or c.country LIKE :key_words  ")
    List<Address> findByAddress1Like(@Param("key_words") String keyWords);

    @Query("select a from Address a where a.address1 like :searchString or a.address2 like :searchString ")
    Page<Address> listAddress(@Param("searchString") String searchString,
                        Pageable pageable);


}

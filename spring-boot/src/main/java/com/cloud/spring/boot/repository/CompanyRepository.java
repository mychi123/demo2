package com.cloud.spring.boot.repository;


import com.cloud.spring.boot.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface CompanyRepository extends PagingAndSortingRepository<Company, Long> {
    Company findOneById(Long id);

    List<Company> findAllByName(String keyWords);

    @Query("SELECT c FROM Company c WHERE c.name LIKE :key_words or c.email LIKE :key_words or c.descriptions LIKE :key_words or c.address LIKE :key_words")
    List<Company> findByNameLike(@Param("key_words") String keyWords);

    @Query("select a from Company a where a.name like :searchString or a.address like :searchString or a.descriptions like :searchString ")
    Page<Company> listCompany(@Param("searchString") String searchString,
                                      Pageable pageable);

}

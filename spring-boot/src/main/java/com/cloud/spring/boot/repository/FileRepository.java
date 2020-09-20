package com.cloud.spring.boot.repository;

import com.cloud.spring.boot.entity.Company;
import com.cloud.spring.boot.entity.File;
import com.cloud.spring.boot.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FileRepository extends PagingAndSortingRepository<File,Long> {
   File findOneById(Long id);
   List<File> findAllByName(String keyWords);

   @Query("SELECT c FROM File c WHERE c.name LIKE :key_words or c.extensionType LIKE :key_words ")
   List<File> findByNameLike(@Param("key_words") String keyWords);

   @Query("select a from File a where a.name like :searchString or a.extensionType like :searchString  ")
   Page<File> listFile(@Param("searchString") String searchString,
                       Pageable pageable);

   @Query("SELECT f FROM Company c, User u, File f WHERE  c.id=u.companyId and u.id= f.userId and c.name LIKE :company_name")
   List<File> findByCityLike(@Param("company_name") String companyNames);

}

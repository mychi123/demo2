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

public interface UserRepository extends PagingAndSortingRepository<User ,Long> {
   User findOneById(Long id);

   User findOneByEmail(String email);
//method
   List<User> findAllByFirstName(String keyWords);

   @Query("SELECT c FROM User c WHERE c.firstName LIKE :key_words or c.lastName LIKE :key_words or c.email LIKE :key_words")
   List<User> findByFirstNameLike(@Param("key_words") String keyWords);

   @Query("select a from User a where a.firstName like :searchString or a.lastName like :searchString  ")
   Page<User> listUser(@Param("searchString") String searchString,
                             Pageable pageable);
//jpql
   @Query("SELECT u FROM Address a, User u WHERE  u.id=a.userId and a.city LIKE :city ")
   List<User> findByCityLike(@Param("city") String city);
//updateusser
//    User  update(user);

}

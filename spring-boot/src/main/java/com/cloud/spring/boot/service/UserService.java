package com.cloud.spring.boot.service;

import com.cloud.spring.boot.entity.Address;
import com.cloud.spring.boot.entity.Company;
import com.cloud.spring.boot.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
   User saveUser(User user);

 User savelistUser(User users);
    User getUser(Long id);
    User getUser(String email);
    void deleteUser(User user);
    List<User> getAllUser();
    List<User> getAllUserByFirstName(String keyWords);
    List<User> getAllByFirstNameLike(String keyWords);
 Page<User> pageUser (String searchString, int page, int size, boolean sort, String sortString);
 List<User> getAllAddressByCityLike(String city);

 //update user list
//  void updateUser(User user);
}

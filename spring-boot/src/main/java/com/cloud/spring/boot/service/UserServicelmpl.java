package com.cloud.spring.boot.service;

import com.cloud.spring.boot.entity.User;
import com.cloud.spring.boot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServicelmpl implements UserService {

    @Autowired
    UserRepository userReposition;
    @Override
    public User saveUser(User user) {
        return  userReposition.save(user);//post and put
    }
    @Override
    public User savelistUser(User users) {
        return  userReposition.save(users);//post and put
    }
    @Override
    public User getUser(Long id) {
        return  userReposition.findOneById(id);//by id
    }
    @Override
    public User getUser(String email) {
        return  userReposition.findOneByEmail(email);//by id
    }
    @Override
    public void deleteUser(User user) {
        userReposition.delete(user);//delete
    }


    @Override
    public List<User> getAllUser() {
        return (List<User>) userReposition.findAll();//get all
    }

    @Override
    public List<User> getAllUserByFirstName(String keyWords) {
        return (List<User>) userReposition. findAllByFirstName(keyWords);
    }

    @Override
    public List<User> getAllByFirstNameLike(String keyWords) {
        return (List<User>) userReposition.findByFirstNameLike('%' + keyWords + '%');
    }
    @Override
    public Page<User> pageUser(String searchString, int page, int size, boolean sort, String sortString) {
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

        return userReposition.listUser("%" + searchString + "%", pageable);
    }
    @Override
    public List<User> getAllAddressByCityLike(String city) {
        return (List<User>) userReposition.findByCityLike( city );
    }
///updatelisst
//    @Override
//    public void updateUser(User user) {
//
//    }


}

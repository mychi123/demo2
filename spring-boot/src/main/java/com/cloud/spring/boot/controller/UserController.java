package com.cloud.spring.boot.controller;

import com.cloud.spring.boot.common.exception.ApplicationException;
import com.cloud.spring.boot.common.util.APIStatus;
import com.cloud.spring.boot.common.util.RestAPIResponse;
import com.cloud.spring.boot.controller.modal.request.CreateUserRequest;
import com.cloud.spring.boot.controller.modal.request.UpdateListUserRequest;
import com.cloud.spring.boot.controller.modal.request.UpdateUserRequest;
import com.cloud.spring.boot.entity.Company;
import com.cloud.spring.boot.entity.User;
import com.cloud.spring.boot.service.AddressService;
import com.cloud.spring.boot.service.CompanyService;
import com.cloud.spring.boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("api/user")
public class UserController extends AbstracBaseController {
    @Autowired
    UserService userService;

    @Autowired
    CompanyService companyService;
    @Autowired
    AddressService addressService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<RestAPIResponse> createUser(
            @RequestBody CreateUserRequest userRequest,
            HttpServletRequest request
    ) {
        //chuyển String thành Date
        Date dayOfBirth = null;
        try {
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            dayOfBirth = df.parse(userRequest.getDayOfBirth());
        } catch (ParseException pe) {
            pe.printStackTrace();
        }

        //ktra không trùng id
        Company company = companyService.getCompany(userRequest.getCompanyId());
        if (company == null) {
            throw new ApplicationException(APIStatus.ERR_NOT_FOUND);
        }
        User user = userService.getUser(userRequest.getEmail());
        if (user != null) {
            throw new ApplicationException(APIStatus.ERR_NOT_FOUND);
        }
//
//        Address address = addressService.getAddress(userRequest.getCompanyId());
//        if (address == null) {
//            throw new ApplicationException(APIStatus.ERR_NOT_FOUND);
//        }
        //
        User newUser = new User();
        newUser.setFirstName(userRequest.getFirstName());
        newUser.setLastName(userRequest.getLastName());
        newUser.setEmail(userRequest.getEmail());
        newUser.setDayOfBirth(dayOfBirth);
        newUser.setCompanyId(userRequest.getCompanyId());
        newUser.setCreateDate(new Date());
        userService.saveUser(newUser);
        return responseUtil.successResponse(newUser);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<RestAPIResponse> updateUser(
            @PathVariable Long id,
            @RequestBody UpdateUserRequest userRequest,
            HttpServletRequest request

    ) {

        Date dayOfBirth = null;
        try {
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            dayOfBirth = df.parse(userRequest.getDayOfBirth());
        } catch (ParseException pe) {
            pe.printStackTrace();
        }

        //kiểm tra id có trong database
        User user = userService.getUser(id);
        if (user == null) {
            throw new ApplicationException(APIStatus.ERR_NOT_FOUND);
        }
        //  User user= new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setDayOfBirth(dayOfBirth);
        user.setUpdateDate(new Date());
        userService.saveUser(user);
        return responseUtil.successResponse(user);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<RestAPIResponse> deleteAddressBook(
            @PathVariable Long id,
            HttpServletRequest request
    ) {
        User user = userService.getUser(id);
        if (user == null) {
            throw new ApplicationException(APIStatus.ERR_NOT_FOUND);
        }
        userService.deleteUser(user);
        return responseUtil.successResponse("OK");
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<RestAPIResponse> getUser(
            @PathVariable Long id,
            HttpServletRequest request
    ) {
        User user = userService.getUser(id);
        if (user == null) {
            throw new ApplicationException(APIStatus.ERR_NOT_FOUND);
        }

        return responseUtil.successResponse(user);
    }

    @RequestMapping(path = "/{email}", method = RequestMethod.GET)
    public ResponseEntity<RestAPIResponse> getUser(
            @PathVariable String email,
            HttpServletRequest request
    ) {
        User user = userService.getUser(email);
        if (user == null) {
            throw new ApplicationException(APIStatus.ERR_NOT_FOUND);
        }

        return responseUtil.successResponse(user);
    }


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<RestAPIResponse> getAllUser(
            HttpServletRequest request
    ) {
        List<User> users = userService.getAllUser();
        if (users.isEmpty()) {
            throw new ApplicationException(APIStatus.NO_RESULT);
        }

        return responseUtil.successResponse(users);
    }


    @RequestMapping(path = "/search", method = RequestMethod.GET)
    public ResponseEntity<RestAPIResponse> getAllUserByFirstName(
            @RequestParam(value = "key_words") String keyWords,
            HttpServletRequest request
    ) {
        List<User> users = userService.getAllUserByFirstName(keyWords);
        if (users.isEmpty()) {
            throw new ApplicationException(APIStatus.NO_RESULT);
        }
        return responseUtil.successResponse(users);
    }


    @RequestMapping(path = "/search_like", method = RequestMethod.GET)
    public ResponseEntity<RestAPIResponse> getAllByFirstNameLike(
            @RequestParam(value = "key_words") String keyWords,
            HttpServletRequest request

    ) {
        List<User> users = userService.getAllByFirstNameLike(keyWords);
        if (users.isEmpty()) {
            throw new ApplicationException(APIStatus.NO_RESULT);
        }
        return responseUtil.successResponse(users);
    }

    @RequestMapping(path = "/page", method = RequestMethod.GET)
    public ResponseEntity<RestAPIResponse> getAllUserPage(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "5") int size,
            @RequestParam(name = "search", defaultValue = "") String searchString,
            @RequestParam(name = "sort", defaultValue = "true") boolean sort,
            @RequestParam(name = "sort_string", defaultValue = "email") String sortString) {

        Page<User> users = userService.pageUser(searchString, page, size, sort, sortString);
        return responseUtil.successResponse(users);
    }

    @RequestMapping(path = "/search_city", method = RequestMethod.GET)
    public ResponseEntity<RestAPIResponse> getAllAddressByCity(
            @RequestParam(value = "city") String city,
            HttpServletRequest request
    ) {
        List<User> users = userService.getAllAddressByCityLike(city);
        if (users.isEmpty()) {
            throw new ApplicationException(APIStatus.NO_RESULT);
        }
        return responseUtil.successResponse(users);
    }


    // put method
// update list users (id, dob, fn, ln, email, companyId)
// requestbody
    @RequestMapping(path = "/update-list", method = RequestMethod.PUT)
    public ResponseEntity<RestAPIResponse> updatelistUser(
            @RequestBody List<UpdateListUserRequest> userRequests,
            HttpServletRequest request

    ) {
        // list response
        List<User> userListResponse = new ArrayList<>();
        // get each user in list request
        for (UpdateListUserRequest updateListUserRequest : userRequests) {
            //find userId in user table

            User user = userService.getUser(updateListUserRequest.getId());
            if (user == null ) {
                continue;
            }
            //find companyId in company table
            Company company = companyService.getCompany(updateListUserRequest.getCompanyId());
            if (company == null) {
                continue;
            }
            if (user.getCompanyId() != updateListUserRequest.getCompanyId()){
                continue;
            }


            // check user != null and company != null
            // parse date

            Date dayOfBirth = null;
            try {
                DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                dayOfBirth = df.parse(updateListUserRequest.getDayOfBirth());
            } catch (ParseException pe) {
                pe.printStackTrace();
            }

            // set new data for each user

            user.setFirstName(updateListUserRequest.getFirstName());
            user.setLastName(updateListUserRequest.getLastName());
            user.setEmail(updateListUserRequest.getEmail());
            user.setDayOfBirth(dayOfBirth);
            // save user list
            userService.saveUser(user);
            // add each user to list response
            userListResponse.add(user);
        }
        return responseUtil.successResponse(userListResponse);
    }
}

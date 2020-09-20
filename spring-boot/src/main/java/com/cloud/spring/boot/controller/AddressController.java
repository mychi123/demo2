package com.cloud.spring.boot.controller;

import com.cloud.spring.boot.entity.Address;

import com.cloud.spring.boot.common.exception.ApplicationException;
import com.cloud.spring.boot.controller.modal.request.CreateAddressRequest;
import com.cloud.spring.boot.controller.modal.request.UpdateAddressRequest;
import com.cloud.spring.boot.common.util.APIStatus;
import com.cloud.spring.boot.common.util.RestAPIResponse;
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
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("api/address")
public class AddressController extends AbstracBaseController {
    @Autowired
    AddressService addressService;

    @Autowired
    UserService userService;
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<RestAPIResponse> createAddress(
            @RequestBody CreateAddressRequest addressRequest,
            HttpServletRequest request
    ) {

        User user = userService.getUser(addressRequest.getUserId());
        if (user == null) {
            throw new ApplicationException(APIStatus.ERR_NOT_FOUND);
        }

       Address address= new Address();
        address.setUserId(addressRequest.getUserId());
        address.setAddress1(addressRequest.getAddress1());
        address.setAddress2(addressRequest.getAddress2());
        address.setCity(addressRequest.getCity());
        address.setCountry(addressRequest.getCountry());
        address.setUserId(addressRequest.getUserId());
        address.setCreateDate(new Date());

        addressService.saveAddress(address);
        return responseUtil.successResponse(address);
    }
    @RequestMapping(path = "/{id}",method = RequestMethod.PUT)
    public ResponseEntity<RestAPIResponse> updateAddress(
            @PathVariable Long id,
            @RequestBody UpdateAddressRequest addressRequest,
            HttpServletRequest request
    ) {
        Address address = addressService.getAddress(id);
        if (address == null) {
            throw new ApplicationException(APIStatus.ERR_NOT_FOUND);
        }

    //    Address address= new Address();
      // address.setUserId(addressRequest.getUserId());
        address.setAddress1(addressRequest.getAddress1());
        address.setAddress2(addressRequest.getAddress2());
        address.setCity(addressRequest.getCity());
        address.setCountry(addressRequest.getCountry());
        address.setUpdateDate(new Date());

        addressService.saveAddress(address);
        return responseUtil.successResponse(address);
    }
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<RestAPIResponse> deleteAddress(
            @PathVariable Long id,
            HttpServletRequest request
    ) {
        Address address = addressService.getAddress(id);
        if (address == null) {
            throw new ApplicationException(APIStatus.ERR_NOT_FOUND);
        }
        addressService.deleteAddress(address);
        return responseUtil.successResponse("OK");
    }
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<RestAPIResponse> getAddress(
            @PathVariable Long id,
            HttpServletRequest request
    ) {
       Address address = addressService.getAddress(id);
        if (address == null) {
            throw new ApplicationException(APIStatus.ERR_NOT_FOUND);
        }

        return responseUtil.successResponse(address);
    }


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<RestAPIResponse> getAllAddress(
            HttpServletRequest request
    ) {
        List<Address> addresses = addressService.getAllAddress();
        if (addresses.isEmpty()) {
            throw new ApplicationException(APIStatus.NO_RESULT);
        }

        return responseUtil.successResponse(addresses);
    }
    @RequestMapping(path= "/search",method = RequestMethod.GET)
    public ResponseEntity<RestAPIResponse> getAllAddressByAddress1(
            @RequestParam(value = "key_words") String keyWords,
            HttpServletRequest request
    ) {
        List<Address> addresses = addressService.getAllByAddress1(keyWords);
        if (addresses.isEmpty()) {
            throw new ApplicationException(APIStatus.NO_RESULT);
        }
        return responseUtil.successResponse(addresses);
    }
    @RequestMapping(path="/search_like",method = RequestMethod.GET)
    public ResponseEntity<RestAPIResponse>getAllByAddress1(
            @RequestParam( value = "key_words") String keyWords,
            HttpServletRequest request

    ) {
        List<Address> addresses = addressService.getAllByAddress1Like(keyWords);
        if (addresses.isEmpty()) {
            throw new ApplicationException(APIStatus.NO_RESULT);
        }
        return responseUtil.successResponse(addresses);
    }
    @RequestMapping(path = "/page", method = RequestMethod.GET)
    public ResponseEntity<RestAPIResponse> getAllAddressPage(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "5") int size,
            @RequestParam(name = "search", defaultValue = "") String searchString,
            @RequestParam(name = "sort_asc", defaultValue = "true") boolean sortAsc,
            @RequestParam(name = "sort_string", defaultValue = "createDate") String sortString) {

        Page<Address> addresses = addressService.pageAddress(searchString, page, size, sortAsc, sortString);
        return responseUtil.successResponse(addresses);
    }


}

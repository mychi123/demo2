package com.cloud.spring.boot.controller;

import com.cloud.spring.boot.entity.Company;
import com.cloud.spring.boot.entity.File;
import com.cloud.spring.boot.common.exception.ApplicationException;
import com.cloud.spring.boot.controller.modal.request.CreateFileRequest;
import com.cloud.spring.boot.controller.modal.request.UpdateFileRequest;
import com.cloud.spring.boot.common.util.APIStatus;
import com.cloud.spring.boot.common.util.RestAPIResponse;
import com.cloud.spring.boot.entity.User;
import com.cloud.spring.boot.service.FileService;
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
@RequestMapping("api/file")
public class FileController extends AbstracBaseController {
    @Autowired
    FileService fileService;
    @Autowired
    UserService userService;
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<RestAPIResponse> createFile(
            @RequestBody CreateFileRequest fileRequest,
            HttpServletRequest request
    ) {
        User user = userService.getUser(fileRequest.getUserId());
        if (user == null) {
            throw new ApplicationException(APIStatus.ERR_NOT_FOUND);
        }
        File file= new File();
        file.setUserId(fileRequest.getUserId());
        file.setName(fileRequest.getName());
        file.setExtensionType(fileRequest.getExtensionType());
        fileRequest.setUserId(fileRequest.getUserId());
        file.setCreateDate(new Date());
        fileService.saveFile(file);
        return responseUtil.successResponse(file);
    }
    @RequestMapping(path = "/{id}",method = RequestMethod.PUT)
    public ResponseEntity<RestAPIResponse> updateFile(
            @PathVariable Long id,
            @RequestBody UpdateFileRequest fileRequest,
            HttpServletRequest request
    ) {

        File file = fileService.getFile(id);
        if (file == null) {
            throw new ApplicationException(APIStatus.ERR_NOT_FOUND);
        }
       // File file= new File();
        file.setName(fileRequest.getName());
        file.setExtensionType(fileRequest.getExtensionType());
        file.setUpdateDate(new Date());
        fileService.saveFile(file);
        return responseUtil.successResponse(file);
    }
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<RestAPIResponse> deleteFile(
            @PathVariable Long id,
            HttpServletRequest request
    ) {
        File file = fileService.getFile(id);
        if (file == null) {
            throw new ApplicationException(APIStatus.ERR_NOT_FOUND);
        }
        fileService.deleteFile(file);
        return responseUtil.successResponse("OK");
    }
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<RestAPIResponse> getFile(
            @PathVariable Long id,
            HttpServletRequest request
    ) {
        File file = fileService.getFile(id);
        if (file == null) {
            throw new ApplicationException(APIStatus.ERR_NOT_FOUND);
        }

        return responseUtil.successResponse(file);
    }
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<RestAPIResponse> getAllFile(
            HttpServletRequest request
    ) {
        List<File> files = fileService.getAllFile();
        if (files.isEmpty()) {
            throw new ApplicationException(APIStatus.NO_RESULT);
        }
        return responseUtil.successResponse(files);
    }
    @RequestMapping(path= "/search",method = RequestMethod.GET)
    public ResponseEntity<RestAPIResponse> getAllFileByName(
            @RequestParam(value = "key_words") String keyWords,
            HttpServletRequest request
    ) {
        List<File> files = fileService.getAllByName(keyWords);
        if (files.isEmpty()) {
            throw new ApplicationException(APIStatus.NO_RESULT);
        }
        return responseUtil.successResponse(files);
    }
    @RequestMapping(path="/search_like",method = RequestMethod.GET)
    public ResponseEntity<RestAPIResponse>getAllByNameLike(
            @RequestParam( value = "key_words") String keyWords,
            HttpServletRequest request

    ) {
        List<File> files = fileService.getAllByNameLike(keyWords);
        if (files.isEmpty()) {
            throw new ApplicationException(APIStatus.NO_RESULT);
        }
        return responseUtil.successResponse(files);
    }
    @RequestMapping(path = "/page", method = RequestMethod.GET)
    public ResponseEntity<RestAPIResponse> getAllFilePage(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "5") int size,
            @RequestParam(name = "search", defaultValue = "") String searchString,
            @RequestParam(name = "sort", defaultValue = "true") boolean sort,
            @RequestParam(name = "sort_string", defaultValue = "createDate") String sortString) {

        Page<File> files = fileService.pageFile(searchString, page, size, sort, sortString);
        return responseUtil.successResponse(files);
    }


    @RequestMapping(path= "/search_name",method = RequestMethod.GET)
    public ResponseEntity<RestAPIResponse> getAllFileByNameLike(
            @RequestParam(value ="company_name") String companyNames,
            HttpServletRequest request
    ) {
        List<File> files = fileService.getAllFileByNameLike(companyNames);
        if (files.isEmpty()) {
            throw new ApplicationException(APIStatus.NO_RESULT);
        }
        return responseUtil.successResponse(files);
    }
    // get method
    // param: company_name
    // response: list files
}

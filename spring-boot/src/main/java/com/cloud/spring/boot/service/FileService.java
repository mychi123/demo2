package com.cloud.spring.boot.service;

import com.cloud.spring.boot.entity.Company;
import com.cloud.spring.boot.entity.File;
import com.cloud.spring.boot.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FileService {
    File saveFile(File file);
    File getFile(Long id);
    void deleteFile(File file);
    List<File> getAllFile();
    List<File> getAllByName(String keyWords);
    List<File> getAllByNameLike(String keyWords);
    Page<File> pageFile (String searchString, int page, int size, boolean sort, String sortString);
    List<File> getAllFileByNameLike(String companyNames);
}

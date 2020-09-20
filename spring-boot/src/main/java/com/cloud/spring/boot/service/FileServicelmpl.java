package com.cloud.spring.boot.service;

import com.cloud.spring.boot.entity.Company;
import com.cloud.spring.boot.entity.File;
import com.cloud.spring.boot.entity.User;
import com.cloud.spring.boot.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileServicelmpl implements FileService {

    @Autowired
    FileRepository fileReposition;
    @Override
    public File saveFile(File file) {
        return fileReposition.save(file);//post and put
    }

    @Override
    public File getFile(Long id) {
        return fileReposition.findOneById(id);//by id
    }

    @Override
    public void deleteFile(File file) {
        fileReposition.delete(file);//delete
    }
    @Override
    public List<File> getAllFile() {
        return (List<File>) fileReposition.findAll();//get all
    }
    @Override
    public List<File> getAllByName(String keyWords) {
        return (List<File>) fileReposition.findAllByName(keyWords);
    }

    @Override
    public List<File> getAllByNameLike(String keyWords) {
        return (List<File>) fileReposition.findByNameLike('%' + keyWords + '%');
    }
    @Override
    public Page<File> pageFile(String searchString, int page, int size, boolean sort, String sortString) {
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
            case "extensionType":
                properties = "extensionType";
                break;
            default:
                properties = "createDate";
                break;
        }
        Pageable pageable = PageRequest.of(page-1, size, direction, properties);

        return fileReposition.listFile("%" + searchString + "%", pageable);
    }

    @Override
    public List<File> getAllFileByNameLike(String companyNames) {
        return (List<File>) fileReposition.findByCityLike( companyNames );
    }


}

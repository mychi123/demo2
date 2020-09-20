package com.cloud.spring.boot.controller.modal.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {

    private  String firstName;
    private  String lastName;
    private String email;
    private String dayOfBirth;
    private Date updateDate;
   // private  Long companyId;
}

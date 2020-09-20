package com.cloud.spring.boot.controller.modal.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {
    private  String firstName;
    private  String lastName;
    private String email;
    private  Long companyId;
    private String dayOfBirth;
}

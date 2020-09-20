package com.cloud.spring.boot.controller.modal.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCompanyRequest {
    private  String name;
    private String address;
    private String email;
    private String descriptions;
}

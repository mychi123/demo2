package com.cloud.spring.boot.controller.modal.request;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAddressRequest {
    private Long userId;
    private String address1;
    private String address2;
    private String city;
    private String country;

}

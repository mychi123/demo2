package com.cloud.spring.boot.controller.modal.request;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateFileRequest {
    private Long userId;
    private String name;
    private String extensionType;
}

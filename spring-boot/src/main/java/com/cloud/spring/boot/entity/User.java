package com.cloud.spring.boot.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Quy Duong
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "user")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="company_id")
    private Long companyId;

    @Column(name="first_name")
    private  String firstName;

    @Column(name="last_name")
    private  String lastName;

    @Column(name="email")
    private String email;

    @Column(name="day_of_birth")
    private Date dayOfBirth;

    @Column(name="create_date")
    private Date createDate;

    @Column(name="update_date")
    private Date updateDate;


}

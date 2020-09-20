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
@Table(name = "company")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Company implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
   private  String name;

    @Column(name="address")
   private String address;

    @Column(name="email")
   private String email;

    @Column(name="descriptions")
   private String descriptions;

    @Column(name="create_date")
   private Date createDate;

    @Column(name="update_date")
   private Date updateDate;

}

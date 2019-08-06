package com.liwen.demo.user.entity;

import java.util.Date;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tb_user")
public class User {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    private String username;

    private Integer age;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8") //Jackson包使用注解
    @Column(name = "create_time")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8") //Jackson包使用注解
    @Column(name = "update_time")
    private Date updateTime;
}
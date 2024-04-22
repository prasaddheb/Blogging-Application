package com.backend.blog.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "role")
public class RoleEntity {

    @Id
    private  Integer id;

    private String name;

}

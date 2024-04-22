package com.backend.blog.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "post")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;

    @Column(name = "post_title",length = 100,nullable = false)
    private  String title;

    @Column(name = "content")
    private  String content;

    @Column(name = "image_name")
    private  String imageName;

    @Column(name = "date")
    private Date addedDate;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private  CategoryEntity category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private  User user;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private Set<CommentEntity> comments = new HashSet<>();
}

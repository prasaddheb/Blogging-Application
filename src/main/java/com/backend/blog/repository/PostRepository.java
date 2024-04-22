package com.backend.blog.repository;

import com.backend.blog.entity.CategoryEntity;
import com.backend.blog.entity.PostEntity;
import com.backend.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity,Integer> {

    List<PostEntity> findByUser(User user);

    List<PostEntity> findByCategory(CategoryEntity categoryEntity);

    List<PostEntity> findByCategoryCategoryId(Integer categoryId);

    List<PostEntity> findByUserId(Integer userId);

    List<PostEntity> findByTitleContaining(String keyword);
}

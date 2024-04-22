package com.backend.blog.service;

import com.backend.blog.entity.PostEntity;
import com.backend.blog.model.dto.PostDto;
import com.backend.blog.model.dto.PostResponse;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);

    PostDto  updatePost(PostDto postDto,Integer postId);

    void deletePost(Integer postId);

    List<PostDto> searchPosts(String keyword);

    PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy,String sortDir );

    PostDto getPostById(Integer postId);

    List<PostDto> getPostsByCategory(Integer categoryId);

    List<PostDto> getPostsByUserId(Integer userId);
}

package com.backend.blog.service.impl;

import com.backend.blog.entity.CategoryEntity;
import com.backend.blog.entity.PostEntity;
import com.backend.blog.entity.User;
import com.backend.blog.exception.ResourceNotFoundException;
import com.backend.blog.mapper.PostMapper;
import com.backend.blog.model.dto.PostDto;
import com.backend.blog.model.dto.PostResponse;
import com.backend.blog.repository.CategoryRepository;
import com.backend.blog.repository.PostRepository;
import com.backend.blog.repository.UserRepository;
import com.backend.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private  final UserRepository userRepository;

    private  final CategoryRepository categoryRepository;

    private  final PostRepository postRepository;

    private  final PostMapper postMapper;

    @Autowired
    public PostServiceImpl(UserRepository userRepository, CategoryRepository categoryRepository, PostRepository postRepository, PostMapper postMapper) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

        User  user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user","userId",userId));

        CategoryEntity categoryEntity = categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","categoryId",categoryId));

        System.out.println("postdrto"+postDto);
        PostEntity postEntity =postMapper.toPostEntity(postDto);
        postEntity.setImageName("default.png");
        postEntity.setAddedDate(new Date());
        postEntity.setCategory(categoryEntity);
        postEntity.setUser(user);
        PostEntity savedPostEntity = postRepository.save(postEntity);
        return postMapper.toPostDto(postEntity);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {

        PostEntity postEntity = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","postId",postId));

        postEntity.setTitle(postDto.getTitle());
        postEntity.setContent(postDto.getContent());
        postEntity.setImageName(postDto.getImageName());

        PostEntity updatedPostEntity = postRepository.save(postEntity);

        return postMapper.toPostDto(updatedPostEntity);
    }

    @Override
    public void deletePost(Integer postId) {

        PostEntity postEntity = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","postId",postId));

        postRepository.delete(postEntity);
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<PostEntity>  postEntityList = postRepository.findByTitleContaining(keyword);

        return postEntityList.stream().map(postMapper::toPostDto).collect(Collectors.toList());
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy ,String sortDir) {

        Sort sort=(sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber,pageSize, sort);

        Page<PostEntity> postDtoList = postRepository.findAll(pageable);

        List<PostEntity>  postEntityList = postDtoList.getContent();

        List<PostDto> dtoList= postEntityList.stream().map(postMapper::toPostDto).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(dtoList);
        postResponse.setPageNumber(postDtoList.getNumber());
        postResponse.setPageSize(postDtoList.getSize());
        postResponse.setTotalElements(postDtoList.getTotalElements());
        postResponse.setTotalPages(postDtoList.getTotalPages());
        postResponse.setLastPage(postDtoList.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        PostEntity postEntity = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","postId",postId));

        return postMapper.toPostDto(postEntity);
    }

    @Override
    public List<PostDto> getPostsByCategory(Integer categoryId) {
        CategoryEntity categoryEntity =  categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","categoryId",categoryId));

        List<PostEntity>  postEntityList = postRepository.findByCategoryCategoryId(categoryId);

        return postEntityList.stream().map(postMapper::toPostDto).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getPostsByUserId(Integer userId) {


        List<PostEntity>  postEntityList = postRepository.findByUserId(userId);
        return postEntityList.stream().map(postMapper::toPostDto).collect(Collectors.toList());
    }
}

package com.backend.blog.service.impl;

import com.backend.blog.entity.CommentEntity;
import com.backend.blog.entity.PostEntity;
import com.backend.blog.exception.ResourceNotFoundException;
import com.backend.blog.mapper.CommentMapper;
import com.backend.blog.model.dto.CommentDto;
import com.backend.blog.repository.CommentRepository;
import com.backend.blog.repository.PostRepository;
import com.backend.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    private  final PostRepository postRepository;

    private  final CommentRepository commentRepository;

    private  final CommentMapper commentMapper;

    @Autowired
    public CommentServiceImpl(PostRepository postRepository, CommentRepository commentRepository, CommentMapper commentMapper) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {

        PostEntity postEntity = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","postId",postId));

        CommentEntity commentEntity = commentMapper.toCommentEntity(commentDto);
        commentEntity.setPost(postEntity);

        CommentEntity savedCommentEntity =  commentRepository.save(commentEntity);

        return commentMapper.toCommentDto(savedCommentEntity);
    }

    @Override
    public void deleteComment(Integer commentId) {

        CommentEntity commentEntity = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment","commentId",commentId));

        commentRepository.delete(commentEntity);
    }
}

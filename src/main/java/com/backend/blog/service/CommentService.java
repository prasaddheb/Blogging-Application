package com.backend.blog.service;

import com.backend.blog.model.dto.CommentDto;

public interface CommentService {

    CommentDto createComment(CommentDto commentDto,Integer postId);

    void deleteComment(Integer commentId);

}

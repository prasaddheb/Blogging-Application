package com.backend.blog.mapper;

import com.backend.blog.entity.CommentEntity;
import com.backend.blog.model.dto.CommentDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentEntity toCommentEntity(CommentDto commentDto);

    CommentDto  toCommentDto(CommentEntity commentEntity);
}

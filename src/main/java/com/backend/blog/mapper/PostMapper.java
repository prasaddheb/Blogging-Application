package com.backend.blog.mapper;

import com.backend.blog.entity.PostEntity;
import com.backend.blog.model.dto.PostDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {

    PostEntity toPostEntity(PostDto postDto);

    PostDto toPostDto(PostEntity postEntity);
}

package com.backend.blog.controller;

import com.backend.blog.model.dto.ApiResponse;
import com.backend.blog.model.dto.CommentDto;
import com.backend.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {

    private  final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable("postId") Integer postId){
       CommentDto createdDto=  commentService.createComment(commentDto,postId);
        return  new ResponseEntity<>(createdDto, HttpStatus.OK);
    }


    @DeleteMapping("/comments/{commentId}")
   public ResponseEntity<ApiResponse> deleteComment(@PathVariable("commentId")Integer commentId){
        commentService.deleteComment(commentId);

        return new ResponseEntity<>(new ApiResponse("Comments Deleted SuccessFully",Boolean.TRUE),HttpStatus.OK);
   }

}

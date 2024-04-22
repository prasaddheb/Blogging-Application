package com.backend.blog.controller;

import com.backend.blog.config.Constants;
import com.backend.blog.model.dto.ApiResponse;
import com.backend.blog.model.dto.PostDto;
import com.backend.blog.model.dto.PostResponse;
import com.backend.blog.service.FileService;
import com.backend.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    private final  PostService postService;

    private  final FileService fileService;

    @Value("${project.image}")
    private String path;

    @Autowired
    public PostController(PostService postService, FileService fileService) {
        this.postService = postService;
        this.fileService = fileService;
    }

    @PostMapping("/users/{userId}/categories/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId,@PathVariable Integer categoryId){

         PostDto dto=  postService.createPost(postDto,userId,categoryId);

         return  new ResponseEntity<PostDto>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/users/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByUserId(@PathVariable Integer userId){

        List<PostDto> postDtoList = postService.getPostsByUserId(userId);

        return  new ResponseEntity<List<PostDto>>(postDtoList,HttpStatus.OK);
    }

    @GetMapping("/categories/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByCategoryId(@PathVariable Integer categoryId){

        List<PostDto> postDtoList = postService.getPostsByCategory(categoryId);

        return  new ResponseEntity<List<PostDto>>(postDtoList,HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(@RequestParam(value ="pageNumber",defaultValue = Constants.PAGE_NUMBER,required = false) Integer pageNumber,
                                                    @RequestParam(value ="pageSize",defaultValue = Constants.PAGE_SIZE,required = false) Integer pageSize,
                                                     @RequestParam(value = "sortBy",defaultValue = Constants.SORT_BY,required = false) String sortBy,
                                                    @RequestParam(value = "sortDir",defaultValue = Constants.SORT_DIR,required = false) String sortDir
                                                    ) {

        PostResponse postDtoList = postService.getAllPost(pageNumber, pageSize,sortBy,sortDir);

        return new ResponseEntity<>(postDtoList, HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
        PostDto post = postService.getPostById(postId);

        return  new ResponseEntity<PostDto>(post,HttpStatus.OK);
    }


    @DeleteMapping("/posts/{postId}")
    public ApiResponse deletePost( @PathVariable    Integer postId){


        postService.deletePost(postId);

        return  new ApiResponse("Post is SuccessFully Deleted",Boolean.TRUE);
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost( @RequestBody PostDto postDto,@PathVariable    Integer postId){

       PostDto dto =  postService.updatePost(postDto,postId);

       return  new ResponseEntity<PostDto>(dto,HttpStatus.OK);
    }

    @GetMapping(value = "/posts/search/{keywords}")
    public  ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keywords") String keywords){
        List<PostDto> result = postService.searchPosts(keywords);
        return  new ResponseEntity<>(result,HttpStatus.OK);
    }

    @PostMapping("/post/image/upload/{postId}")
    public  ResponseEntity<PostDto> uploadPostImage(@RequestParam("image")MultipartFile image, @PathVariable("postId") Integer postId) throws IOException {

            String fileName =  fileService.uploadImage(path,image);
            PostDto dto = postService.getPostById(postId);
            dto.setImageName(fileName);
         PostDto updatePostDto=    postService.updatePost(dto,postId);

         return new ResponseEntity<>(updatePostDto,HttpStatus.OK);
    }

    @GetMapping(value = "/post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable("imageName") String imageName,
            HttpServletResponse response
    ) throws IOException {

        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream())   ;

    }
}

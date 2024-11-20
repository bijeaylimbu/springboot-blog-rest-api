package com.springboot.blog.controller;

import com.springboot.blog.payload.BlogDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.payload.RestResponseDto;
import com.springboot.blog.service.BlogService;
import com.springboot.blog.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

import static com.springboot.blog.utils.AppConstants.POST_CREATED_SUCCESS_MESSAGE;
import static com.springboot.blog.utils.AppConstants.POST_DELETE_SUCCESS_MESSAGE;

@RestController
@RequestMapping("/api/v1/posts" )
@Tag(
        name = "CRUD REST APIs for Post Resource"
)
public class BlogController {

    private BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @Operation(
            summary = "Create Post REST API",
            description = "Create Post REST API is used to save post into database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    // create blog post rest api
    @PostMapping
    public ResponseEntity<BlogDto> createPost(@Valid @RequestBody BlogDto postDto) {
        blogService.createPost(postDto);
        return RestResponseDto.successModel(POST_CREATED_SUCCESS_MESSAGE);
    }

    @Operation(
            summary = "Get All Posts REST API",
            description = "Get All Posts REST API is used to fetch all the posts from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    // get all posts rest api
    @GetMapping
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        return blogService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
    }

    @Operation(
            summary = "Get Post By Id REST API",
            description = "Get Post By Id REST API is used to get single post from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    // get post by id
    @GetMapping("/{id}" )
    public ResponseEntity<BlogDto> getPostById(@PathVariable(name = "id" ) long id) {
        return ResponseEntity.ok(blogService.getPostById(id));
    }

    @Operation(
            summary = "update Post REST API",
            description = "Update Post REST API is used to update a particular post in the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    // update post by id rest api
    @PutMapping("/{id}" )
    public ResponseEntity<BlogDto> updatePost(@Valid @RequestBody BlogDto postDto, @PathVariable(name = "id" ) long id) {

        BlogDto postResponse = blogService.updatePost(postDto, id);

        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete Post REST API",
            description = "Delete Post REST API is used to delete a particular post from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    // delete post rest api
    @DeleteMapping("/{id}" )
    public ResponseEntity<String> deletePost(@PathVariable(name = "id" ) long id) {

        blogService.deletePostById(id);

        return RestResponseDto.successModel(POST_DELETE_SUCCESS_MESSAGE);
    }

    // Build Get Posts by Category REST API
    // http://localhost:8080/api/posts/category/3
    @GetMapping("/category/{id}" )
    public ResponseEntity<List<BlogDto>> getPostsByCategory(@PathVariable("id" ) Long categoryId) {
        List<BlogDto> postDtos = blogService.getPostsByCategory(categoryId);
        return ResponseEntity.ok(postDtos);
    }
    @Operation(
            summary = "Search Post REST API",
            description = "Search Post REST API is used to search a particular post from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @GetMapping("/blog/search" )
    public ResponseEntity<List<BlogDto>> searchBlog(
            @RequestParam(required = false) String blogTitle) {
        List<BlogDto> blogs = blogService.searchBlog(blogTitle);
        return ResponseEntity.ok(blogs);

    }
}

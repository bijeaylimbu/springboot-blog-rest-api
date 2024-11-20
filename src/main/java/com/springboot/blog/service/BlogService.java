package com.springboot.blog.service;

import com.springboot.blog.payload.BlogDto;
import com.springboot.blog.payload.PostResponse;

import java.util.List;

public interface BlogService {
    BlogDto createPost(BlogDto postDto);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    BlogDto getPostById(long id);

    BlogDto updatePost(BlogDto postDto, long id);

    void deletePostById(long id);

    List<BlogDto> getPostsByCategory(Long categoryId);
    List<BlogDto> searchBlog(String blogTitle);
}

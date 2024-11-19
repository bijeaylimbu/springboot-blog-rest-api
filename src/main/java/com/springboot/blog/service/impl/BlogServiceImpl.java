package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Category;
import com.springboot.blog.entity.Blog;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.BlogDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.repository.BlogRepository;
import com.springboot.blog.service.BlogService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlogServiceImpl implements BlogService {

    private BlogRepository postRepository;

    private ModelMapper mapper;

    private CategoryRepository categoryRepository;

    public BlogServiceImpl(BlogRepository postRepository, ModelMapper mapper,
                           CategoryRepository categoryRepository) {
          this.postRepository = postRepository;
          this.mapper = mapper;
          this.categoryRepository = categoryRepository;
    }

    @Override
    public BlogDto createPost(BlogDto postDto) {

        Category category = categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));

        // convert DTO to entity
        Blog post = mapToEntity(postDto);
        post.setCategory(category);
        Blog newPost = postRepository.save(post);

        // convert entity to DTO
        BlogDto postResponse = mapToDTO(newPost);
        return postResponse;
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Blog> posts = postRepository.findAll(pageable);

        // get content for page object
        List<Blog> listOfPosts = posts.getContent();

        List<BlogDto> content= listOfPosts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    @Override
    public BlogDto getPostById(long id) {
        Blog post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return mapToDTO(post);
    }

    @Override
    public BlogDto updatePost(BlogDto postDto, long id) {
        // get post by id from the database
        Blog post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        Category category = categoryRepository.findById(postDto.getCategoryId())
                        .orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setCategory(category);
        Blog updatedPost = postRepository.save(post);
        return mapToDTO(updatedPost);
    }

    @Override
    public void deletePostById(long id) {
        // get post by id from the database
        Blog post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
    }

    @Override
    public List<BlogDto> getPostsByCategory(Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        List<Blog> posts = postRepository.findByCategoryId(categoryId);

        return posts.stream().map((post) -> mapToDTO(post))
                .collect(Collectors.toList());
    }

    // convert Entity into DTO
    private BlogDto mapToDTO(Blog post){
        BlogDto postDto = mapper.map(post, BlogDto.class);
//        PostDto postDto = new PostDto();
//        postDto.setId(post.getId());
//        postDto.setTitle(post.getTitle());
//        postDto.setDescription(post.getDescription());
//        postDto.setContent(post.getContent());
        return postDto;
    }

    // convert DTO to entity
    private Blog mapToEntity(BlogDto postDto){
        Blog post = mapper.map(postDto, Blog.class);
//        Post post = new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        return post;
    }
}

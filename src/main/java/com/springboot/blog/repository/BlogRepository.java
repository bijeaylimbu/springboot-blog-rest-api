package com.springboot.blog.repository;

import com.springboot.blog.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import static com.springboot.blog.utils.SQLQueryConstants.SEARCH_BLOG_BY_BLOG_TITLE;

public interface BlogRepository extends JpaRepository<Blog, Long> {

    List<Blog> findByCategoryId(Long categoryId);
    @Query(value = SEARCH_BLOG_BY_BLOG_TITLE, nativeQuery = true)
    List<Blog> searchBlogByBlogTitle(@Param("blogTitle")String blogTitle);

}

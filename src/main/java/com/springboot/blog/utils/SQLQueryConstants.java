package com.springboot.blog.utils;

public class SQLQueryConstants {
    public static  final String  SEARCH_BLOG_BY_BLOG_TITLE="SELECT * FROM blogs b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :blogTitle, '%'))";

}

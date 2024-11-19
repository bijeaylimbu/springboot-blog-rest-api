package com.springboot.blog.payload;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class BlogDto {
    private long id;

    @Schema(
            description = "Blog Post Title"
    )
    // title should not be null  or empty
    // title should have at least 2 characters
    @NotEmpty
    @Size(min = 2, message = "Post title should have at least 2 characters")
    private String title;

    @Schema(
            description = "Blog Post Description"
    )

    // post content should not be null or empty
    @NotEmpty
    private String content;
    private Set<CommentDto> comments;

    @Schema(
            description = "Blog Post Category"
    )
    private Long categoryId;
}

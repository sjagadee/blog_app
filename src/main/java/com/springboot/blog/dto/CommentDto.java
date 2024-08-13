package com.springboot.blog.dto;

import lombok.Builder;

@Builder
public record CommentDto(Long id, String name, String email, String body) {
}

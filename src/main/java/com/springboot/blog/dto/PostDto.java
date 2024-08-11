package com.springboot.blog.dto;

import lombok.Builder;

@Builder
public record PostDto(Long id, String title, String description, String content) {
}

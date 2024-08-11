package com.springboot.blog.service;

import com.springboot.blog.dto.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    List<PostDto> getAllPosts(int pageNo, int pageSize);

    PostDto getPost(Long id);

    PostDto updatePost(PostDto postDto, Long id);

    void deletePostById(Long id);
}

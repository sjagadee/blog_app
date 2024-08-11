package com.springboot.blog.service.impl;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.entity.Post;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = Post.builder()
                .id(postDto.id())
                .title(postDto.title())
                .description(postDto.description())
                .content(postDto.content())
                .build();

        Post savedPost = postRepository.save(post);

        PostDto postResponse = new PostDto(savedPost.getId(), savedPost.getTitle(), savedPost.getDescription(), savedPost.getContent());
        return postResponse;
    }
}

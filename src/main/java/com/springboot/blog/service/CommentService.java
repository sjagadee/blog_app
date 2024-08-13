package com.springboot.blog.service;

import com.springboot.blog.dto.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(Long postId, CommentDto commentDto);

    List<CommentDto> getAllCommentsByPostId(Long postId);
}

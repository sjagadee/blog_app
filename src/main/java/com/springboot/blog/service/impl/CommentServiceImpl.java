package com.springboot.blog.service.impl;

import com.springboot.blog.dto.CommentDto;
import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exeception.BlogAPIException;
import com.springboot.blog.exeception.ResourceNotFoundException;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);

        // retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "id", postId.toString()));

        // set post to comment entity
        comment.setPost(post);

        // comment entity to DTO
        Comment savedComment = commentRepository.save(comment);
        CommentDto commentResponse = mapToDTO(savedComment);
        return commentResponse;
    }

    @Override
    public List<CommentDto> getAllCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());

    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        // retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "id", postId.toString()));

        // retrieve comment entity by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId.toString()));

        if(!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, STR."Comment Does not belong to Post with id: \{postId}");
        }

        return mapToDTO(comment);
    }

    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto) {

        // retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "id", postId.toString()));

        // retrieve comment entity by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId.toString()));

        if(!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, STR."Comment Does not belong to Post with id: \{postId}");
        }

        comment.setName(commentDto.name());
        comment.setBody(commentDto.body());
        comment.setEmail(commentDto.email());

        Comment udatedComment = commentRepository.save(comment);
        return mapToDTO(udatedComment);
    }

    @Override
    public void deleteCommentById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "id", postId.toString()));

        // retrieve comment entity by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId.toString()));

        if(!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, STR."Comment Does not belong to Post with id: \{postId}");
        }

        commentRepository.deleteById(commentId);
    }

    private Comment mapToEntity(CommentDto commentDto) {
        return Comment.builder()
                .name(commentDto.name())
                .email(commentDto.email())
                .body(commentDto.body())
                .build();
    }

    private CommentDto mapToDTO(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .name(comment.getName())
                .email(comment.getEmail())
                .body(comment.getBody())
                .build();
    }
}

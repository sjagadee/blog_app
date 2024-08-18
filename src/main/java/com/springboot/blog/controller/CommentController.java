package com.springboot.blog.controller;

import com.springboot.blog.dto.CommentDto;
import com.springboot.blog.dto.PostDto;
import com.springboot.blog.service.CommentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(
        name = "CRUD REST APIs for Comment Resource"
)
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(
            @PathVariable(value = "postId") Long postId,
            @Valid @RequestBody CommentDto commentDto
    ) {
        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentDto>> getAllCommentsByPostId(@PathVariable(value = "postId") Long postId) {
        return ResponseEntity.ok(commentService.getAllCommentsByPostId(postId));
    }

    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> getCommentById(
            @PathVariable(value = "postId") Long postId,
            @PathVariable(value = "id") Long commentId
    ) {
        return ResponseEntity.ok(commentService.getCommentById(postId, commentId));
    }

    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> updateComment(
            @PathVariable(value = "postId") Long postId,
            @PathVariable(value = "id") Long commentId,
            @Valid @RequestBody CommentDto commentDto
    ) {
        return ResponseEntity.ok(commentService.updateComment(postId, commentId, commentDto));
    }

    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String> deleteCommentById(
            @PathVariable(name = "id") Long postId,
            @PathVariable(value = "id") Long commentId
    ) {
        commentService.deleteCommentById(postId, commentId);
        return new ResponseEntity<>(STR."Comment with id: \{commentId} deleted successfully", HttpStatus.OK);
    }
}

package com.springboot.blog.controller;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.dto.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@Tag(
        name = "CRUD REST APIs for Post Resource"
)
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Create Post REST API",
            description = "Create Post REST API - used to create a post and save the information into the database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 Created"
    )
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get all Posts REST API",
            description = "Get all Posts REST API - used to get all the posts from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    @GetMapping
    public ResponseEntity<PostResponse> getAllPosts(
        @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
        @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
        @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
        @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        return ResponseEntity.ok(postService.getAllPosts(pageNo, pageSize, sortBy, sortDir));
    }

    @Operation(
            summary = "Get Post By Id REST API",
            description = "Get Post By Id REST API - used to get a post from the database based on provided id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable(name = "id") Long postId) {
        return ResponseEntity.ok(postService.getPost(postId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Update Post By Id REST API",
            description = "Update Post By Id REST API - used to update a post from the database for a given post id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable(name = "id") Long postId) {
        PostDto updatedPostDto = postService.updatePost(postDto, postId);
        return new ResponseEntity<>(updatedPostDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Delete Post By Id REST API",
            description = "Delete Post By Id REST API - used to delete a post from the database based on provided id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable(name = "id") Long postId) {
        postService.deletePostById(postId);
        return new ResponseEntity<>(STR."Post with id: \{postId} deleted successfully", HttpStatus.OK);
    }

    // get posts by category id
    @Operation(
            summary = "Get all Posts By categoryId REST API",
            description = "Get all Posts By categoryId API - used to get all posts from the database based on provided categoryId"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<PostDto>> getPostsByCategoryId(@PathVariable(name = "categoryId") Long categoryId) {
        return new ResponseEntity<>(postService.getPostsByCategoryId(categoryId), HttpStatus.OK);
    }

}

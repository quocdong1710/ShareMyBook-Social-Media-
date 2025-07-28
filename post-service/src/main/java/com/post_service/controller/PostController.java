package com.post_service.controller;

import org.springframework.web.bind.annotation.RestController;

import com.post_service.dto.request.PostRequest;
import com.post_service.dto.response.APIResponse;
import com.post_service.dto.response.PageResponse;
import com.post_service.dto.response.PostResponse;
import com.post_service.service.PostService;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PostController {
    PostService postService;

    @PostMapping("/create")
    APIResponse<PostResponse> createPost(@RequestBody PostRequest request) {
        return APIResponse.<PostResponse>builder()
                .result(postService.createPost(request))
                .build();
    }

    @GetMapping("/my-post")
    APIResponse<PageResponse<PostResponse>> getMyPost(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        return APIResponse.<PageResponse<PostResponse>>builder()
                .result(postService.getMyPost(page, size))
                .build();
    }
}

package com.example.demo.controller;


import com.example.demo.dto.PostRequestDto;
import com.example.demo.dto.PostResponseDto;
import com.example.demo.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    //게시글 등록
    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@AuthenticationPrincipal UserDetails userDetails,
                                                      @RequestBody PostRequestDto dto){
        return ResponseEntity.ok(postService.createPost(userDetails.getUsername(),dto));
    }

    //게시글 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long id){
        return ResponseEntity.ok(postService.getPost(id));
    }

    //게시글 전체 조회
    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getAllPosts(){
        return ResponseEntity.ok(postService.getAllPosts());
    }

    //게시글 수정
    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable Long id,
                                                      @AuthenticationPrincipal UserDetails userDetails,
                                                      @RequestBody PostRequestDto dto){
        return ResponseEntity.ok(postService.updatePost(id,userDetails.getUsername(),dto));
    }
    //게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id,@AuthenticationPrincipal UserDetails userDetails) {
        postService.deletePost(id,userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
}

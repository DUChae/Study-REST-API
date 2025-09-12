package com.example.demo.controller;

import com.example.demo.dto.CommentRequestDto;
import com.example.demo.dto.CommentResponseDto;
import com.example.demo.dto.CommentUpdateRequestDto;
import com.example.demo.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 등록
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentResponseDto> addComment(
            @PathVariable Long postId,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody CommentRequestDto dto) {
        CommentResponseDto commentDto = commentService.addComment(postId, userDetails.getUsername(), dto);
        return ResponseEntity.ok(commentDto);
    }

    // 댓글 조회
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentResponseDto>> getComments(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getComments(postId));
    }

    // 댓글 수정
    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody CommentUpdateRequestDto dto) {
        return ResponseEntity.ok(commentService.updateComment(commentId,userDetails.getUsername() ,dto));
    }

    // 댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId,
                                              @AuthenticationPrincipal UserDetails userDetails) {
        commentService.deleteComment(commentId,userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
}
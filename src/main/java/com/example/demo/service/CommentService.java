package com.example.demo.service;

import com.example.demo.domain.Comment;
import com.example.demo.domain.Post;
import com.example.demo.domain.User;
import com.example.demo.dto.CommentRequestDto;
import com.example.demo.dto.CommentResponseDto;
import com.example.demo.dto.CommentUpdateRequestDto; // <-- 이 부분을 import 해주세요
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // 댓글 등록
    @Transactional
    public CommentResponseDto addComment(Long postId, String username, CommentRequestDto dto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Comment comment = Comment.builder()
                .content(dto.getContent())
                .post(post)
                .user(user)
                .build();

        Comment savedComment = commentRepository.save(comment);
        return CommentResponseDto.fromEntity(savedComment);
    }

    // 댓글 조회
    public List<CommentResponseDto> getComments(Long postId) {
        return commentRepository.findByPostId(postId).stream()
                .map(CommentResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    // 댓글 수정
    @Transactional
    public CommentResponseDto updateComment(Long commentId,String username, CommentUpdateRequestDto dto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));
        if(!comment.getUser().getUsername().equals(username)){
            throw new IllegalArgumentException("본인의 댓글만 수정할 수 있습니다.");
        }
        comment.setContent(dto.getContent());
        // @Transactional에 의해 변경 내용이 자동으로 DB에 반영됩니다.
        return CommentResponseDto.fromEntity(comment);
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(Long commentId,String username) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));
        if(!comment.getUser().getUsername().equals(username)){
            throw new IllegalArgumentException("본인의 댓글만 삭제할 수 있습니다.");
        }
        commentRepository.deleteById(commentId);
    }
}
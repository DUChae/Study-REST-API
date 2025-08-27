package com.example.demo.service;


import com.example.demo.domain.Post;
import com.example.demo.domain.User;
import com.example.demo.dto.PostRequestDto;
import com.example.demo.dto.PostResponseDto;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    //게시글 등록
    @Transactional
    public PostResponseDto createPost(Long userId, PostRequestDto dto){
        User user=userRepository.findById(userId)
                .orElseThrow(()->new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        Post post=Post.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .author(user)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Post saved=postRepository.save(post);
        return PostResponseDto.from(saved);
    }


    //게시글 조회
    @Transactional(readOnly = true)
    public PostResponseDto getPost(Long id){
        Post post=postRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        return PostResponseDto.from(post);
    }



}

package com.example.demo.repository;

import com.example.demo.domain.Like;
import com.example.demo.domain.Post;
import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByUserAndPost(User user, Post post);   // 특정 유저가 특정 게시글에 좋아요 눌렀는지 확인
    void deleteByUserAndPost(User user, Post post);      // 특정 유저의 좋아요 취소
    long countByPost(Post post);                         // 게시글 좋아요 수
}
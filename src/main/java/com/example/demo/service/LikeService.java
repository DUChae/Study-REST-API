package com.example.demo.service;


import com.example.demo.domain.Like;
import com.example.demo.domain.Post;
import com.example.demo.domain.User;
import com.example.demo.repository.LikeRepository;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;

    @Transactional
    public void likePost(Long postId,String username){
        User user=findUserByUsername(username);
        Post post=findPostById(postId);
        if(likeRepository.existsByUserAndPost(user,post)){
            throw new IllegalArgumentException("이미 좋아요를 눌렀습니다.");
        }
        Like like=Like.builder()
                .user(user)
                .post(post)
                .build();
        likeRepository.save(like);
    }

    @Transactional
    public void unlikePost(Long postId,String username){
        User user=findUserByUsername(username);
        Post post=findPostById(postId);
        likeRepository.deleteByUserAndPost(user,post);
    }

    public long countLikes(Long postId){
        Post post=findPostById(postId);
        return likeRepository.countByPost(post);
    }




    //중복 코드 제거 위한 private helper method
    private User findUserByUsername(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(()->new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }
    private Post findPostById(Long postId){
        return postRepository.findById(postId)
                .orElseThrow(()->new IllegalArgumentException("게시글을 찾을 수 없습니다."));
    }

}

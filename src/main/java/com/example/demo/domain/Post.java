package com.example.demo.domain;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable=false, length=100)
    private String title;

    @Column(nullable=false, columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createdAt;

    // 연관 관계


    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy="post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments=new ArrayList<>();
    @OneToMany(mappedBy="post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes=new ArrayList<>();

}

package com.example.demo.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//게시글 등록 및 수정
public class PostRequestDto {
    private String title;
    private String content;
    private Long userId;
}

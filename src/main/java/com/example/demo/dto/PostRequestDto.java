package com.example.demo.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//게시글 등록 및 수정
public class PostRequestDto {
    private String title;
    private String content;
}

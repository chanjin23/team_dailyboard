package com.team5.first_project.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class MemberResponseDto {

    private Long id;
    private String name;
    private String email;

}

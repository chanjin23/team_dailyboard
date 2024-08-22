package com.team5.first_project.member.dto;

import com.team5.first_project.member.entity.Member;
import lombok.Getter;

@Getter

public class MemberPostDto {
    private String name;
    private String email;
    private String password;

    // Getters and Setters

    public Member toEntity() {
        return new Member(name, email, password);
    }
}

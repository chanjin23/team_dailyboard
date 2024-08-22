package com.team5.first_project.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberLogInRequestDto {

    @Email
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
}

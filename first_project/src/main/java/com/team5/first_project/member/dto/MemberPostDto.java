package com.team5.first_project.member.dto;

import com.team5.first_project.member.entity.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberPostDto {

    @NotBlank(message = "이름을 입력해주세요")
    private String name;

    @NotBlank(message = "게시판에서 사용할 이름을 입력해주세요.")
    private String nickName;

    @Email
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

}

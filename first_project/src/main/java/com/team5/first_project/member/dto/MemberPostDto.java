package com.team5.first_project.member.dto;

import com.team5.first_project.member.entity.Member;
import com.team5.first_project.member.entity.MemberRoleEnum;
import jakarta.validation.constraints.*;
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

    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    @Size(min = 8, max = 16, message = "비밀번호는 8자 이상 16자 이하이어야 합니다.")
    @Pattern(regexp = "^(?=.*[!@#$%^&*(),.?\":{}|<>]).+$", message = "비밀번호에는 특수문자가 포함되어야 합니다.")
    private String password;

    @NotNull(message = "회원 유형은 필수 선택항목입니다.")
    private MemberRoleEnum role;

    private String adminCode;
}

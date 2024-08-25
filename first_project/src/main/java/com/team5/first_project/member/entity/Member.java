package com.team5.first_project.member.entity;

import com.team5.first_project.member.dto.MemberLogInRequestDto;
import com.team5.first_project.member.dto.MemberPostDto;
import com.team5.first_project.timestamp.Timestamp;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Member extends Timestamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "nickname", nullable = false)
    private String nickName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    @Column(nullable = true)
    @Enumerated(value = EnumType.STRING)
    private MemberRoleEnum role;

    public Member(MemberPostDto memberPostDto){
        this.name = memberPostDto.getName();
        this.nickName = memberPostDto.getNickName();
        this.email = memberPostDto.getEmail();
        this.password = memberPostDto.getPassword();
        this.role = memberPostDto.getRole();
    }

    public Member(MemberPostDto memberPostDto, Long id) {
        this.id = id;
        this.name = memberPostDto.getName();
        this.nickName = memberPostDto.getNickName();
        this.email = memberPostDto.getEmail();
        this.password = memberPostDto.getPassword();
        this.role = memberPostDto.getRole();
    }

//    public Member(MemberLogInRequestDto memberLogInRequestDto){
//        this.email = memberLogInRequestDto.getEmail();
//        this.password = memberLogInRequestDto.getPassword();
//    }

    public void toEntity(MemberPostDto memberPostDto){
        this.name = memberPostDto.getName();
        this.nickName = memberPostDto.getNickName();
        this.email = memberPostDto.getEmail();
        this.password = memberPostDto.getPassword();
        this.role = memberPostDto.getRole();
    }

}

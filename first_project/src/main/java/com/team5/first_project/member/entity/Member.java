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

    @Column(name = "nickname", nullable = false, unique = true)
    private String nickName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    //    @Column(nullable = false)
    //    @Enumerated(value = EnumType.STRING)
    //    private MemberRoleEnum role;

    public Member(MemberPostDto memberPostDto){
        this.name = memberPostDto.getName();
        this.nickName = memberPostDto.getNickName();
        this.email = memberPostDto.getEmail();
        this.password = memberPostDto.getPassword();
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
    }

}

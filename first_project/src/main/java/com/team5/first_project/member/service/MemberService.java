package com.team5.first_project.member.service;

import com.team5.first_project.member.dto.MemberLogInRequestDto;
import com.team5.first_project.member.dto.MemberPostDto;
import com.team5.first_project.member.entity.Member;
import com.team5.first_project.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
// import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    //private final PasswordEncoder passwordEncoder; // 비밀번호 암호화를 위한 PasswordEncoder

    //모든 회원 조회
    public List< Member> findAllMembers(){
        return memberRepository.findAll();}

    // ID로 회원 조회
    public Member getMemberById(Long id){
        return memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다"));
        // RuntimeException인지 확인 필요
    }

    // 회원가입
    @Transactional
    public void signUp(MemberPostDto memberPostDto) {
        //member.setPassword(passwordEncoder.encode(member.getPassword()));
        Member member = new Member(memberPostDto);
        memberRepository.save(member);
    }

    // 회원가입 시 동일한 닉네임이나 이메일을 사용했는지 검증
    public boolean isNickNameUnique(String nickName){
        return memberRepository.existsByNickName(nickName);
    }

    public boolean isEmailUnique(String email){
        return memberRepository.existsByEmail(email);
    }

    // 로그인
    public Member logIn(MemberLogInRequestDto memberLogInRequestDto){
        return memberRepository.findByEmail(memberLogInRequestDto.getEmail())
                .filter((m)->m.getPassword().equals(memberLogInRequestDto.getPassword()))
                .orElse(null);
    }

    // 회원 정보 수정
    @Transactional
    public void updateMember(long id, MemberPostDto memberPostDto) {

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        // 조회한 회원 객체의 필드를 업데이트
        member.toEntity(memberPostDto);
    }

    // 회원 탈퇴
    @Transactional
    public void deleteMember(Member member) {
        // 회원이 존재하지 않는 경우 예외를 던질 수도 있지만, 여기서는 무시합니다.
            memberRepository.deleteById(member.getId());
    }
}


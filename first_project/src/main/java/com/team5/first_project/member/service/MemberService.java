package com.team5.first_project.member.service;

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
    //ID로 회원 조회
    public Member getMemberById(Long id){
        return (Member) memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다"));
    }
    //새 회원 생성
    @Transactional
    public Member createMember(Member member) {
        //member.setPassword(passwordEncoder.encode(member.getPassword()));
        return memberRepository.save(member);
    }
    //회원 정보 업데이트
    @Transactional
    public Member updateMember(Member member){
        getMemberById(member.getId());
        return memberRepository.save(member);
    }
}


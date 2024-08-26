package com.team5.first_project.member.service;

import com.team5.first_project.member.dto.MemberLogInRequestDto;
import com.team5.first_project.member.dto.MemberPostDto;
import com.team5.first_project.member.entity.Member;
import com.team5.first_project.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;


    //모든 회원 조회
    // 기능을 사용한다면 isDelete가 false인 것만 조회하도록 수정 필요
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
        return memberRepository.existsByNickNameAndIsDeletedFalse(nickName);
    }

    public boolean isEmailUnique(String email){
        return memberRepository.existsByEmailAndIsDeletedFalse(email);
    }

    // 로그인
    public Optional<Member> logIn(MemberLogInRequestDto memberLogInRequestDto){
        return memberRepository.findByEmailAndIsDeletedFalse(memberLogInRequestDto.getEmail())
                .filter((m)->m.getPassword().equals(memberLogInRequestDto.getPassword()));
    }

    // 회원 정보 수정
    @Transactional
    public void updateMember(long id, MemberPostDto memberPostDto) {

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        // 조회한 회원 객체의 필드를 업데이트
        member.toEntity(memberPostDto);
    }

    @Transactional
    public void softDeleteMember(Long id) {
        Optional<Member> memberOptional = memberRepository.findById(id);
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            member.setDeleted(true);
        } else {
            throw new IllegalArgumentException("Member not found with id: " + id);
        }
    }

    public boolean isAdminCode(MemberPostDto memberPostDto) {
        //관리자 코드 임시설정
        String memberRole = memberPostDto.getRole().getRoleName();
        String adminCode = memberPostDto.getAdminCode();
        String answerAdminCode = "관리자";

        if (memberRole.equals("Admin") && adminCode.equals(answerAdminCode)) {
            return false;
        }

        if (memberRole.equals("User")) {
            return false;
        }

        return true;
    }
}


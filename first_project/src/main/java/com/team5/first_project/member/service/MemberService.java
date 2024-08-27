package com.team5.first_project.member.service;

import com.team5.first_project.exception.NotFoundByMemberIdException;
import com.team5.first_project.member.dto.MemberLogInRequestDto;
import com.team5.first_project.member.dto.MemberPostDto;
import com.team5.first_project.member.entity.Member;
import com.team5.first_project.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private static final int PAGE_SIZE = 10;

    //모든 회원 조회
    // 기능을 사용한다면 isDelete가 false인 것만 조회하도록 수정 필요
    @Transactional
    public Page< Member> findAllMembers(Pageable pageable){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.asc("id"));
        pageable = PageRequest.of(pageable.getPageNumber(), PAGE_SIZE, Sort.by(sorts));

        return memberRepository.findAll(pageable);
    }

    //회원 이름이나 닉네임으로 조회
    @Transactional
    public Page<Member> findKeyword(String name, String nickName, Pageable pageable) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.asc("id"));
        pageable = PageRequest.of(pageable.getPageNumber(), PAGE_SIZE, Sort.by(sorts));

        return memberRepository.findByNameContainingOrNickNameContaining(name, nickName, pageable);
    }

    // ID로 회원 조회
    public Member getMemberById(Long id){
        return memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundByMemberIdException(id));
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
                .orElseThrow(() -> new NotFoundByMemberIdException(id));

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
            throw new NotFoundByMemberIdException(id);
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

    public boolean isValidDeleteMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundByMemberIdException(id));
        boolean deleted = member.isDeleted();
        if (deleted) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isValidLengthPassword(MemberPostDto memberPostDto) {
        // 비밀번호 길이와 특수문자 포함 여부를 수동으로 검증
        if (memberPostDto.getPassword().length() < 8 || memberPostDto.getPassword().length() > 16) {
            return true;
        }
        return false;
    }

    public boolean isValidGoodPassword(MemberPostDto memberPostDto) {
        if (!memberPostDto.getPassword().matches("^(?=.*[!@#$%^&*(),.?\":{}|<>~]).+$")) {
            return true;
        }
        return false;
    }
}


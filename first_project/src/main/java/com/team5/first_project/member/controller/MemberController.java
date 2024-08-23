package com.team5.first_project.member.controller;

import com.team5.first_project.member.dto.MemberLogInRequestDto;
import com.team5.first_project.member.dto.MemberPostDto;
import com.team5.first_project.member.dto.MemberResponseDto;
import com.team5.first_project.member.entity.Member;
import com.team5.first_project.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    //모든 회원 조회
    @GetMapping
    public String findAllMembers(Model model) {
        List<Member> members = memberService.findAllMembers();
        List<MemberResponseDto> memberResponseDtos = members.stream()
                .map(member -> new MemberResponseDto(member.getNickName(), member.getEmail()))
                .toList();

        model.addAttribute("members", memberResponseDtos);
        return "member/list"; // 회원 목록을 보여줄 뷰의 이름
    }

    //특정 ID를 가진 회원 조회
    @GetMapping("/{id}")
    public String getMember(@PathVariable("id") Long id, Model model) {

        Member member = memberService.getMemberById(id);
        MemberResponseDto memberResponseDto = new MemberResponseDto(member.getNickName(),  member.getEmail());
        model.addAttribute("member", memberResponseDto);
        return "member/memberDetail";
    }

    // 회원가입
    @GetMapping("/signUp")
    public String signUpPage(Model model) {
        model.addAttribute("member", new MemberPostDto());
        return "member/signUp";
    }

    @PostMapping("/signUp")
    public String signUpRequest(@Valid @ModelAttribute MemberPostDto memberPostDto,
                                Model model) {
        boolean isNickNameUnique = memberService.isNickNameUnique(memberPostDto.getNickName());
        boolean isEmailUnique = memberService.isEmailUnique(memberPostDto.getEmail());
        if (!isNickNameUnique && !isEmailUnique) {
            memberService.signUp(memberPostDto);
            return "redirect:/members/logIn";
        } else {
            model.addAttribute("member", memberPostDto);
            if (isNickNameUnique) {
                model.addAttribute("nickNameError", "이미 사용중인 닉네임입니다.");
            }
            if (isEmailUnique) {
                model.addAttribute("emailError", "이미 사용중인 이메일입니다.");
            }
            return "member/signUp";
        }
    }

    // 로그인
    @GetMapping("/logIn")
    public String logInPage() {
        return "member/logIn";
    }

    @PostMapping("/logIn")
    public String logInRequest(@Valid @ModelAttribute MemberLogInRequestDto memberLogInRequestDto,
                               HttpSession session, Model model) {
        Member member = memberService.logIn(memberLogInRequestDto);
        if (member != null){
            session.setAttribute("member", member);
            return "redirect:/boards";
        } else {
            model.addAttribute("error", "이메일 또는 비밀번호가 일치하지 않습니다.");
            return "member/logIn";
        }
    }

    // 해당 회원의 기존정보가져오기
    @GetMapping("/edit/{id}")
    public String editMemberPage(@PathVariable("id") Long id, Model model){
        Member member = memberService.getMemberById(id);
        model.addAttribute("member", member);
        return "member/editMember";
    }

    // 회원정보 수정
    @PostMapping("/edit/{id}")
    public String updateMember(@PathVariable long id,
                               @Valid @ModelAttribute MemberPostDto memberPostDto,
                               Model model) {
        memberService.updateMember(id, memberPostDto);
        return "redirect:/members";
        // 어디로 리다이렉트할지 미정
    }

    // 회원 탈퇴
    // Resy Api 방식으로 할지 미정
    @PostMapping("/{id}")
    public String deleteMember(@PathVariable("id") Long id){
        memberService.deleteMember(id);
        return "redirect:/members/logIn";
    }

}


package com.team5.first_project.member.controller;

import com.team5.first_project.board.service.BoardService;
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
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final BoardService boardService;

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
        boolean isAdminCode = memberService.isAdminCode(memberPostDto);
        if (!isNickNameUnique && !isEmailUnique && !isAdminCode) {
            memberService.signUp(memberPostDto);
            return "redirect:/boards";
        } else {
            model.addAttribute("member", memberPostDto);
            if (isNickNameUnique) {
                model.addAttribute("nickNameError", "이미 사용중인 닉네임입니다.");
            }
            if (isEmailUnique) {
                model.addAttribute("emailError", "이미 사용중인 이메일입니다.");
            }
            if (isAdminCode) {
                model.addAttribute("adminCodeError", "관리자 코드를 잘못입력하였습니다.");
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
        Optional<Member> optionalMember = memberService.logIn(memberLogInRequestDto);
        if (optionalMember.isPresent()){
            Member member = optionalMember.get();
            session.setAttribute("member", member);
            return "redirect:/boards";
        } else {
            model.addAttribute("error", "이메일 또는 비밀번호가 일치하지 않습니다.");
            model.addAttribute("loginError", true); // 자바스크립트로 모달 표시를 위한 플래그
            model.addAttribute("boards", boardService.getAllBoards());
            model.addAttribute("logInStatus", 1);
            return "board/boards";
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
    public String updateMember(@PathVariable("id") long id,
                               @Valid @ModelAttribute MemberPostDto memberPostDto,
                               HttpSession session,
                               Model model) {
        boolean isAdminCode = memberService.isAdminCode(memberPostDto);
        if (!isAdminCode) {
            memberService.updateMember(id, memberPostDto);
            session.invalidate();
            return "redirect:/boards";
        } else {
            Member member = new Member(memberPostDto, id);
            model.addAttribute("member", member);
            if (isAdminCode) {
                model.addAttribute("adminCodeError", "관리자 코드를 잘못입력하였습니다.");
            }
            return "member/editMember";
        }
        // 개인 정보 수정 후 로그아웃하여 게시판으로 이동
    }

    @GetMapping("/delete")
    public String deleteMemberPage() {
        return "member/deleteMember";
    }

    // 회원 탈퇴
    @PostMapping("/delete")
    public String deleteMember(HttpSession session, Model model){
        Member member = (Member) session.getAttribute("member");
        memberService.softDeleteMember(member.getId());
        session.invalidate();
//        model.addAttribute("member", member);
        return "redirect:/boards"; //게시판으로 이동
    }

}


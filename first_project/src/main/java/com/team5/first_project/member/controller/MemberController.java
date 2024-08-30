package com.team5.first_project.member.controller;

import com.team5.first_project.board.entity.Board;
import com.team5.first_project.board.service.BoardService;
import com.team5.first_project.member.dto.MemberLogInRequestDto;
import com.team5.first_project.member.dto.MemberPasswordDto;
import com.team5.first_project.member.dto.MemberPostDto;
import com.team5.first_project.member.entity.Member;
import com.team5.first_project.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    // 회원가입
    @GetMapping("/signUp")
    public String signUpPage(Model model) {
        model.addAttribute("member", new MemberPostDto());
        return "member/signUp";
    }

    @PostMapping("/signUp")
    public String signUpRequest(@ModelAttribute MemberPostDto memberPostDto, Model model) {
        try {
            // 비밀번호 길이와 특수문자 포함 여부를 수동으로 검증
            if (memberService.isValidLengthPassword(memberPostDto)) {
                throw new IllegalArgumentException("비밀번호는 8자 이상 16자 이하이어야 합니다.");
            }
            if (memberService.isValidGoodPassword(memberPostDto)) {
                throw new IllegalArgumentException("비밀번호에는 특수문자가 포함되어야 합니다.");
            }

            boolean isNickNameUnique = memberService.isNickNameUnique(memberPostDto.getNickName());
            boolean isEmailUnique = memberService.isEmailUnique(memberPostDto.getEmail());
            boolean isAdminCode = memberService.isAdminCode(memberPostDto);

            if (!isNickNameUnique && !isEmailUnique && !isAdminCode) {
                memberService.signUp(memberPostDto);
                return "redirect:/boards";
            } else {
                if (isNickNameUnique) {
                    model.addAttribute("nickNameError", "이미 사용중인 닉네임입니다.");
                }
                if (isEmailUnique) {
                    model.addAttribute("emailError", "이미 사용중인 이메일입니다.");
                }
                if (isAdminCode) {
                    model.addAttribute("adminCodeError", "관리자 코드를 잘못입력하였습니다.");
                }
                model.addAttribute("member", memberPostDto);
                return "member/signUp";
            }
        } catch (IllegalArgumentException ex) {
            model.addAttribute("passwordError", ex.getMessage());
            model.addAttribute("member", memberPostDto);
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
        if (optionalMember.isPresent()) {
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

    //모든 회원 조회
    @GetMapping
    public String findAllMembers(@RequestParam(value = "keyword", defaultValue = "") String keyword,
                                 Pageable pageable,
                                 Model model) {
        List<Board> boards = boardService.getAllBoards();

        Page<Member> filterMembers = null;
        if (keyword.isEmpty()) {
            filterMembers = memberService.findAllMembers(pageable);
        } else {
            filterMembers = memberService.findKeyword(keyword, keyword, pageable);
            model.addAttribute("keyword", keyword);
        }

        model.addAttribute("members", filterMembers);
        model.addAttribute("boards", boards);
        return "member/list";
    }

    //특정 ID를 가진 회원 조회
    @GetMapping("/{memberId}")
    public String getMember(@PathVariable("memberId") Long id, Model model) {

        Member member = memberService.getMemberById(id);
        model.addAttribute("member", member);
        return "member/memberDetail";
    }

    //비밀번호 찾기
    @GetMapping("/findPassword")
    public String getPageFindPassword() {
        return "member/findPassword";
    }

    @PostMapping("/findPassword")
    public String findPassword(@RequestParam("name") String name,
                             @RequestParam("email") String email,
                             Model model) {
        boolean isValidInfo = memberService.isValidInfo(name, email);
        if (isValidInfo) {
            MemberPasswordDto password = memberService.findPassword(name, email);
            model.addAttribute("password", password);
        } else {
            model.addAttribute("error", "이름과 이메일의 정보가 일치하지 않습니다.");
        }
        return "member/findPassword";
    }

    // 해당 회원의 기존정보가져오기
    @GetMapping("/edit/{id}")
    public String editMemberPage(@PathVariable("id") Long id, Model model) {
        Member member = memberService.getMemberById(id);
        model.addAttribute("member", member);
        return "member/editMember";
    }

    // 회원정보 수정
    @PostMapping("/edit/{id}")
    public String updateMember(@PathVariable("id") long id,
                               @ModelAttribute MemberPostDto memberPostDto,
                               HttpSession session,
                               Model model) {
        boolean isValidLengthPassword = memberService.isValidLengthPassword(memberPostDto);
        boolean isValidGoodPassword = memberService.isValidGoodPassword(memberPostDto);
        boolean isAdminCode = memberService.isAdminCode(memberPostDto);
        if (!isAdminCode && !isValidLengthPassword && !isValidGoodPassword) {
            memberService.updateMember(id, memberPostDto);
            session.invalidate();
            return "redirect:/boards";
        } else {
            Member member = new Member(memberPostDto, id);
            model.addAttribute("member", member);
            if (isAdminCode) {
                model.addAttribute("adminCodeError", "관리자 코드를 잘못입력하였습니다.");
            }
            // if - else if 구문
            if (isValidLengthPassword) {
                model.addAttribute("passwordError", "비밀번호는 8자 이상 16자 이하이어야 합니다.");
            }
            else if (isValidGoodPassword) {
                model.addAttribute("passwordError", "비밀번호에는 특수문자가 포함되어야 합니다.");
            }
            return "member/editMember";
        }
    }

    // 회원 탈퇴
    @GetMapping("/delete")
    public String deleteMemberPage() {
        return "member/deleteMember";
    }

    @PostMapping("/delete")
    public String deleteMember(HttpSession session, Model model) {
        Member member = (Member) session.getAttribute("member");
        memberService.softDeleteMember(member.getId());

        boolean isValidDeleteMember = memberService.isValidDeleteMember(member.getId());  //값이 삭제되면 true

        if (isValidDeleteMember) {
            model.addAttribute("deleteMember", true);
            session.invalidate();
        } else {
            model.addAttribute("deleteMember", false);
        }
        return "redirect:/boards";
    }

}


package com.team5.first_project.member.controller;

import com.team5.first_project.member.dto.MemberPostDto;
import com.team5.first_project.member.dto.MemberResponseDto;
import com.team5.first_project.member.entity.Member;
import com.team5.first_project.member.service.MemberService;
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
                .map(member -> new MemberResponseDto(member.getId(), member.getName(), member.getEmail()))
                .toList();

        model.addAttribute("members", memberResponseDtos);
        return "member/list"; // 회원 목록을 보여줄 뷰의 이름
    }

    //특정 ID를 가진 회원 조회
    @GetMapping("/{id}")
    public String getMember(@PathVariable("id") Long id, Model model) {

        Member member = memberService.getMemberById(id);
        MemberResponseDto memberResponseDto = new MemberResponseDto(member.getId(), member.getName(),  member.getEmail());
        model.addAttribute("member", memberResponseDto);
        return "member/memberDetail";
    }

    //새로운 회원 생성
    @GetMapping("/members/create")
    public String createMemberPage(Model model) {
        model.addAttribute("memberPostDto", new MemberPostDto());
        return "createMember";
    }

    @PostMapping("/members/create")
    public String createMember(@ModelAttribute MemberPostDto memberPostDto, Model model) {
        Member member = memberPostDto.toEntity();
        Member newMember = memberService.createMember(member);
        return "redirect:/members";
    }

}


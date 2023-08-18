package com.goodee.mvcboard.controller;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.goodee.mvcboard.service.MemberService;
import com.goodee.mvcboard.vo.Member;

@Controller
public class MemberController {
	@Autowired
	private MemberService memberService;
	
	
	@GetMapping("/member/login")
	public String login() {
		return "/member/login";
	}
	
	@PostMapping("/member/login")
	public String login(HttpSession session,
						@RequestParam(name = "memberId") String memberId,
						@RequestParam(name = "memberPw") String memberPw){
		// 로그인정보값 저장
		Member loginMember = new Member();
		loginMember.setMemberId(memberId);
		loginMember.setMemberPw(memberPw);
		
		// service(memberId, memberPw) -> mapper -> 로그인 성공 유무 반환
		Member resultMember = memberService.loginId(loginMember);
		// 로그인 성공시
		if(resultMember != null) {
			session.setAttribute("loginId", memberId);
			System.out.println(memberId + "<- loginId");
			System.out.println(memberPw + "<- loginPw");
			return "redirect:/home";
		}
		
		return "redirect:/member/login";
		
	}
	
	// 로그아웃
	@GetMapping("/member/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return"redirect:/member/login";
	}
}

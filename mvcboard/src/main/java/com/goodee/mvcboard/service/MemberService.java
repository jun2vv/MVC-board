package com.goodee.mvcboard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goodee.mvcboard.mapper.MemberMapper;
import com.goodee.mvcboard.vo.Member;

@Service
public class MemberService {
	@Autowired
	private MemberMapper memberMapper;
	
	// 로그인
	public Member loginId(Member member) {
		Member resultMemberId = memberMapper.loginMember(member);
		
		return resultMemberId;
	}
}

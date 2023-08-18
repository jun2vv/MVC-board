package com.goodee.mvcboard.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.goodee.mvcboard.vo.Member;

@Mapper
public interface MemberMapper {
	
	// 로그인 
	Member loginMember(Member member);
}

package com.mini_ssafy_heaven.service;

import org.springframework.stereotype.Service;

import com.mini_ssafy_heaven.dao.MemberDao;
import com.mini_ssafy_heaven.dto.request.CreateMemberRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

	private final MemberDao memberDao;
	
	// 아이디 존재여부
	@Override
	public boolean existsId(CreateMemberRequest request) {
		
		boolean result = false;
		result = memberDao.existsById(request.username());		
		if(result) {
			return true;
		}
		return false;
	}

	// 회원 추가
	@Override
	public boolean addMember(CreateMemberRequest request) {
		boolean check = existsId(request);
		if(check) {
			int result = memberDao.addMember(request)
		}
		return false;
	}

}

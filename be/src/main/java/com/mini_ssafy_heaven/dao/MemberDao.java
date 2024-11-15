package com.mini_ssafy_heaven.dao;

import org.apache.ibatis.annotations.Param;

import com.mini_ssafy_heaven.domain.Member;

public interface MemberDao {

  boolean existsById(String username);
  int addMember(@Param("member") Member member);
}

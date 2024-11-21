package com.mini_ssafy_heaven.dao;

import com.mini_ssafy_heaven.domain.Member;
import org.apache.ibatis.annotations.Param;

public interface MemberDao {

  boolean existsById(Long id);

  boolean existsByUserName(String username);

  int addMember(@Param("member") Member member);

  String getPassWord(String username);
}

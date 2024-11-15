package com.mini_ssafy_heaven.domain;

import lombok.Getter;

@Getter
public class Member {
  
  private Long id;
  private String username;
  private String password;
  private String nickname;
  private String role;
  private int score;
  
  public Member(
    String username, 
    String password, 
    String nickname,
    String role,
    int score) {
	  this.username = username;
	  this.password = password;
	  this.nickname = nickname;
	  this.role = role;
	  this.score = score;
  }
}

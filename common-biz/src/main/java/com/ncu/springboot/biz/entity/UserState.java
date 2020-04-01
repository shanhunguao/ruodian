package com.ncu.springboot.biz.entity;

public interface UserState {
	
	int USER_DISABLED = 0;  //用户账号不可用
	int USER_ENABLED = 1;   //用户账号可用
	int USER_LOCKED = 2;    //用户账号被锁定
	
}

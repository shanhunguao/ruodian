package com.ncu.springboot.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ncu.springboot.biz.entity.Role;
import com.ncu.springboot.biz.entity.User;

public class CustomUserDetails implements UserDetails {

	private Integer id;
	private String usercode;
	private String password;
	private List<Role> roles;
	private Set<SimpleGrantedAuthority> authorities = new HashSet<SimpleGrantedAuthority>();

	public CustomUserDetails(User user) {
		id = user.getId();
		usercode = user.getUsercode();
		password = user.getPassword();
		if (user.getRoles() != null) {
			roles = user.getRoles();
			for (Role role : user.getRoles()) {
				authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
			}
		}
	}

	@Override
	public Collection<SimpleGrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	public void setAuthorities(Set<SimpleGrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public String getPassword() { // 最重点Ⅰ
		return this.password;
	}

	@Override
	public String getUsername() { // 最重点Ⅱ
		return this.usercode;
	}

	public void setUsername(String usercode) {
		this.usercode = usercode;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	// 账号是否过期
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	// 账号是否锁定
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	// 账号凭证是否未过期
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}

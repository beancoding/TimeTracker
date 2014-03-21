package com.dmcliver.timetracker.domain;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class SysUserDetails implements UserDetails {

	private static final long serialVersionUID = -4865939957180938327L;
	private String password = "password1";

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return Arrays.asList(new SimpleGrantedAuthority("su"));
	}

	@Override
	public String getPassword() {

		return password;
	}

	@Override
	public String getUsername() {

		return "user1";
	}

	@Override
	public boolean isAccountNonExpired() {

		return true;
	}

	@Override
	public boolean isAccountNonLocked() {

		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {

		return true;
	}

	@Override
	public boolean isEnabled() {

		return true;
	}

	public void setPassword(String encodedPassword) {

		password  = encodedPassword;
	}
}

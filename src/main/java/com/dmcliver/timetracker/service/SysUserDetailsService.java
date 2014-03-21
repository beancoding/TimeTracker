package com.dmcliver.timetracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dmcliver.timetracker.domain.SysUserDetails;

@Service
public class SysUserDetailsService implements UserDetailsService {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username)	throws UsernameNotFoundException {

		SysUserDetails webUserDetails = new SysUserDetails();
		String password = webUserDetails.getPassword();
		String encodedPassword = passwordEncoder.encode(password);
		webUserDetails.setPassword(encodedPassword);
		return webUserDetails;
	}
}

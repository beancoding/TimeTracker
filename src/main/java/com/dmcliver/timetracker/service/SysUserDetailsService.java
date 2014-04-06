package com.dmcliver.timetracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dmcliver.timetracker.datalayer.SysUserDAO;
import com.dmcliver.timetracker.domain.SysUser;
import com.dmcliver.timetracker.domain.SysUserDetails;

@Service
public class SysUserDetailsService implements UserDetailsService {
	
	@Autowired
	private SysUserDAO sysUserDAO;
	
	@Override
	public UserDetails loadUserByUsername(String username)	throws UsernameNotFoundException {

		SysUser user = sysUserDAO.findByUsername(username);
		
		if(user == null)
			throw new UsernameNotFoundException("No user");
		
		SysUserDetails sysUserDetails = new SysUserDetails(user.getPassword(), user.getUserName());
		return sysUserDetails;
	}
}

package com.dmcliver.timetracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dmcliver.timetracker.datalayer.SysUserDAO;
import com.dmcliver.timetracker.domain.SysUser;
import com.dmcliver.timetracker.model.UserModel;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private SysUserDAO sysUserDAO;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void save(UserModel model) {
		
		String password = passwordEncoder.encode(model.getPassword());
		SysUser user = new SysUser(model.getName(), password);
		sysUserDAO.save(user);
	}
}

package com.dmcliver.timetracker.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dmcliver.timetracker.datalayer.SysUserDAO;
import com.dmcliver.timetracker.domain.SysUser;

@Component
@ManagedBean
@RequestScoped
public class HomeControllerBean {

	@Autowired
	private SysUserDAO sysUserDAO;
	
	public String getGreetings(){
		
		sysUserDAO.findAll();
		return sysUserDAO == null ? "Crap" : "Sweet";
	}
	
	public List<SysUser> getUsers(){
		return sysUserDAO.findAll();
	}
}

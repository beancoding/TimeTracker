package com.dmcliver.timetracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dmcliver.timetracker.datalayer.SysUserDAO;

@Controller
public class AccountController {

	@Autowired
	private SysUserDAO sysUserDAO;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(){
		
		sysUserDAO.findAll();
		return "login";
	}
	
	@RequestMapping(value = "/naughty", method = RequestMethod.GET)
	public String viewRegister(){
		
		return "register";
	}
	
	@RequestMapping(value = "/badlogin", method = RequestMethod.GET)
	public String viewBadLogin(Model model){
		
		model.addAttribute("error", true);
		return "login";
	}
}

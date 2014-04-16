package com.dmcliver.timetracker.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dmcliver.timetracker.datalayer.SysUserDAO;
import com.dmcliver.timetracker.model.UserModel;
import com.dmcliver.timetracker.service.UserService;

@Controller
public class AccountController {

	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
	
	@Autowired
	private SysUserDAO sysUserDAO;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(){

		return "login";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String viewRegister(Model model){
		
		model.addAttribute("User", new UserModel());
		return "register";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(@Valid @ModelAttribute("User") UserModel model, BindingResult result){
		
		if(result.hasErrors())
			return "register";
		
		if(!model.getConfirmPassword().equals(model.getPassword())){
			
			result.reject("BadPassword", "*The passwords must match");
			return "register";
		}
			
		try{
			userService.save(model);
		}
		catch(DataIntegrityViolationException ex){
			
			result.reject("DupPassword", "*The user name already exists please pick another");
			logger.info("User constraint violation " + model.getName());
			return "register";
		}
		catch(Exception ex){
			
			result.reject("DupPassword", "*Could not add user due to a " + ex.getClass().getName() + " error");
			logger.error("", ex);
			return "register";
		}
		
		return "redirect:/";
	}
	
	@RequestMapping(value = "/badlogin", method = RequestMethod.GET)
	public String viewBadLogin(Model model){
		
		model.addAttribute("error", true);
		return "login";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request){
		
		request.getSession(false).invalidate();
		return "/";
	}
}

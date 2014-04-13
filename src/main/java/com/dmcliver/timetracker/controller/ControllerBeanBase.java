package com.dmcliver.timetracker.controller;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class ControllerBeanBase {

	protected String getUsername() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String name = authentication.getName();
		return name;
	}

	public void addError(String key, String message) {
		FacesContext.getCurrentInstance().addMessage(key, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, ""));
	}
}


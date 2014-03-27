package com.dmcliver.timetracker.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.dmcliver.timetracker.datalayer.NoteDAO;
import com.dmcliver.timetracker.domain.Note;

@Component
@ManagedBean
@RequestScoped
public class HomeControllerBean {

	private NoteDAO noteDAO;	
	
	@Autowired
	public HomeControllerBean(NoteDAO noteDAO) {
		
		this.noteDAO = noteDAO;
	}

	public List<Note> getNotes(){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String name = authentication.getName();
		return noteDAO.findByUser(name);
	}
}

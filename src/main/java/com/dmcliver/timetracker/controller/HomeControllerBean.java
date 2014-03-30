package com.dmcliver.timetracker.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.dmcliver.timetracker.datalayer.NoteDAO;
import com.dmcliver.timetracker.datalayer.SysUserDAO;
import com.dmcliver.timetracker.domain.Note;
import com.dmcliver.timetracker.domain.SysUser;

@Component
@ManagedBean
@RequestScoped
public class HomeControllerBean {

	private NoteDAO noteDAO;	
	private String noteContent;
	private SysUserDAO sysUserDAO;
	private List<Note> notes;
	
	@Autowired
	public HomeControllerBean(NoteDAO noteDAO, SysUserDAO sysUserDAO) {
		
		this.noteDAO = noteDAO;
		this.sysUserDAO = sysUserDAO;
	}

	public String getNoteContent() {
		return noteContent;
	}
	public void setNoteContent(String noteContent) {
		this.noteContent = noteContent;
	}
	
	public List<Note> getNotes(){
		
		if(notes == null)			
			updateNotes();
		return notes;
	}

	private void updateNotes() {
		
		String name = getUsername();
		notes = noteDAO.findByUser(name);
	}

	private String getUsername() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String name = authentication.getName();
		return name;
	}
	
	public String addNote(){
		
		Note n = new Note(noteContent);
		SysUser user = sysUserDAO.findByUsername(getUsername());
		n.setUser(user);
		noteDAO.save(n);
		updateNotes();
		return "this";
	}
	
	public String deleteNote(Note n){
		
		noteDAO.delete(n);
		updateNotes();
		return "this";
	}
}

package com.dmcliver.timetracker.controller;

import java.util.List;
import java.util.UUID;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import static com.dmcliver.timetracker.TimeTrackerConstants.*;

import com.dmcliver.timetracker.datalayer.JobDAO;
import com.dmcliver.timetracker.datalayer.NoteDAO;
import com.dmcliver.timetracker.datalayer.SysUserDAO;
import com.dmcliver.timetracker.domain.Job;
import com.dmcliver.timetracker.domain.Note;
import com.dmcliver.timetracker.domain.SysUser;
import com.dmcliver.timetracker.domain.UserJobAssignment;
import com.dmcliver.timetracker.domain.UserJobAssignmentPK;

@Component
@ManagedBean
@RequestScoped
public class HomeControllerBean {

	private NoteDAO noteDAO;	
	private SysUserDAO sysUserDAO;
	private JobDAO jobDAO;
	
	private List<Note> notes;
	private List<Job> jobs;
	private String noteContent;
	private String jobName;
	private double estimate;
	
	@Autowired
	public HomeControllerBean(NoteDAO noteDAO, SysUserDAO sysUserDAO, JobDAO jobDAO) {
		
		this.noteDAO = noteDAO;
		this.sysUserDAO = sysUserDAO;
		this.jobDAO = jobDAO;
	}

	/* Properties */
	public String getNoteContent() {
		return noteContent;
	}
	public void setNoteContent(String noteContent) {
		this.noteContent = noteContent;
	}
	
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	
	public double getEstimate() {
		return estimate;
	}
	public void setEstimate(double estimate) {
		this.estimate = estimate;
	}

	/* Data table retrievals */
	public List<Note> getNotes(){
		
		if(notes == null){
			String name = getUsername();
			notes = noteDAO.findByUser(name);
		}
		return notes;
	}

	public List<Job> getJobs() {
		
		if(jobs == null)
			jobs = jobDAO.findAllForUser(getUsername());
		
		return jobs;
	}
	
	/* Data manipulation */
	public String addNote(){
		
		Note n = new Note(noteContent);
		SysUser user = sysUserDAO.findByUsername(getUsername());
		n.setUser(user);
		noteDAO.save(n);
		notes.add(n);
		noteContent = emptyString;
		return "this";
	}
	
	public String deleteNote(Note n){
		
		noteDAO.delete(n);
		notes.remove(n);
		return "this";
	}

	public String addJob(){
		
		UUID jobId = UUID.randomUUID();
		Job job = new Job(jobName, estimate, jobId);
		jobDAO.save(job);
		jobs.add(job);
		
		String username = getUsername();
		SysUser user = sysUserDAO.findByUsername(username);
		
		UserJobAssignment userJobAssignment = new UserJobAssignment(new UserJobAssignmentPK(username, jobId), user, job);
		jobDAO.save(userJobAssignment);
		
		jobName = emptyString;
		estimate = 0;
		
		return "this";
	}
	
	/* Helper methods */
	private String getUsername() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String name = authentication.getName();
		return name;
	}
}

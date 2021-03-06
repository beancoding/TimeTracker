package com.dmcliver.timetracker.controller;

import java.util.List;
import java.util.UUID;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.springframework.beans.factory.annotation.Autowired;
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
public class HomeControllerBean extends ControllerBeanBase {

	private NoteDAO noteDAO;	
	private SysUserDAO sysUserDAO;
	private JobDAO jobDAO;
	
	private List<Note> notes;
	private List<Job> jobs;
	private List<Job> filteredData;
	private String noteContent;
	private String jobName;
	private double estimate;
	
	@Autowired
	public HomeControllerBean(NoteDAO noteDAO, SysUserDAO sysUserDAO, JobDAO jobDAO) {
		
		this.noteDAO = noteDAO;
		this.sysUserDAO = sysUserDAO;
		this.jobDAO = jobDAO;
		jobs = null;
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
			String name = super.getUsername();
			notes = noteDAO.findByUser(name);
		}
		return notes;
	}

	public List<Job> getJobs() {
		
		if(jobs == null)
			refreshJobs();
		
		return jobs;
	}

	private void refreshJobs() {
		jobs = jobDAO.findAllForUser(super.getUsername());
	}
	
	/* Data manipulation */
	public String addJob(){
		
		refreshJobs();
		if(checkIfAssignedJobForUserExists())
			return "this";
		
		UUID jobId = UUID.randomUUID();
		
		Job job = materializeJob(jobId);
		assignJobToUser(jobId, job);
		
		clearFields();
		
		return "this";
	}
	
	public String addNote(){
		
		Note n = new Note(noteContent);
		SysUser user = sysUserDAO.findByUsername(super.getUsername());
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

	/* Data manipulation helper methods */
	private boolean checkIfAssignedJobForUserExists() {
		
		for (Job j : jobs) {
			
			if(j.getJobName().toLowerCase().equals(jobName.toLowerCase())){
				
				super.addError("UniqueJobnameViolation", "There is already a job with this name assigned to you");
				return true;
			}
		}
		return false;
	}
	
	private Job materializeJob(UUID jobId) {
		
		Job job = jobDAO.findByName(jobName);
		if(job == null){
			
			job = new Job(jobName, estimate, jobId);
			jobDAO.save(job);
		}
		
		jobs.add(job);
		return job;
	}
	
	private void assignJobToUser(UUID jobId, Job job) {
		
		String username = super.getUsername();
		SysUser user = sysUserDAO.findByUsername(username);
		
		UserJobAssignment userJobAssignment = new UserJobAssignment(new UserJobAssignmentPK(username, jobId), user, job);
		jobDAO.save(userJobAssignment);
	}
	
	private void clearFields() {
		
		jobName = emptyString;
		estimate = 0;
	}

	public List<Job> getFilteredData() {
		return filteredData;
	}
	public void setFilteredData(List<Job> filteredData) {
		this.filteredData = filteredData;
	}
	
	public boolean isJobFinished(Job j){
		
		Job job = jobDAO.findByName(j.getJobName());
		return job.isFinished();
	}
}

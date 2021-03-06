package com.dmcliver.timetracker.controller;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.dmcliver.timetracker.TimeTrackerConstants.emptyString;

import com.dmcliver.timetracker.datalayer.JobDAO;
import com.dmcliver.timetracker.datalayer.NoteDAO;
import com.dmcliver.timetracker.datalayer.SysUserDAO;
import com.dmcliver.timetracker.datalayer.TimeEntryDAO;
import com.dmcliver.timetracker.datalayer.UserJobAssignmentDAO;
import com.dmcliver.timetracker.domain.Job;
import com.dmcliver.timetracker.domain.Note;
import com.dmcliver.timetracker.domain.NoteComment;
import com.dmcliver.timetracker.domain.SysUser;
import com.dmcliver.timetracker.domain.TimeDifferential;
import com.dmcliver.timetracker.domain.TimeEntry;
import com.dmcliver.timetracker.service.TimeService;

@Component
@ManagedBean
@RequestScoped
public class JobTimeEntryControllerBean extends ControllerBeanBase{

	private JobDAO jobDAO;
	private TimeEntryDAO timeEntryDAO;
	private UserJobAssignmentDAO userJobAssignmentDAO;
	private NoteDAO noteDAO;
	private SysUserDAO sysUserDAO;
	
	private boolean inProgress = false;
	private Job job;
	private String noteText;
	private String commentText;
	private List<NoteComment> noteComments;
	private boolean finished;
	private double adjustedTime;
	private TimeService timeService;
	
	@Autowired
	public JobTimeEntryControllerBean(JobDAO jobDAO, TimeEntryDAO timeEntryDAO,	UserJobAssignmentDAO userJobAssignmentDAO, NoteDAO noteDAO, SysUserDAO sysUserDAO, TimeService timeService) {
		
		this.jobDAO = jobDAO;
		this.timeEntryDAO = timeEntryDAO;
		this.userJobAssignmentDAO = userJobAssignmentDAO;
		this.noteDAO = noteDAO;
		this.sysUserDAO = sysUserDAO;
		this.timeService = timeService;
	}
	
	public boolean isFinished() {
		
		if(job == null) refreshJob();
		finished = job.isFinished();
		return finished;
	}
	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public double getAdjustedTime() {
		return adjustedTime;
	}

	public void setAdjustedTime(double adjustedTime) {
		this.adjustedTime = adjustedTime;
	}
	
	public boolean isInProgress() {
		
		TimeEntry lastEntry = this.timeEntryDAO.findLastEntryInProgressForUser(getUsername(), job.getJobId());
		return lastEntry != null;
	}
	
	public String getNoteText() {
		return noteText;
	}
	public void setNoteText(String noteText) {
		this.noteText = noteText;
	}
	
	public String getCommentText() {
		return commentText;
	}
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}
	
	public Job getJob() {

		noteComments = null;
		refreshJob();
		return job;
	}

	private void refreshJob(){
		
		String jobId = getJobId();
		if(jobDAO != null && jobId != null && !emptyString.equals(jobId))
			job = jobDAO.findByName(jobId);
	}
	
	private String getJobId() {
		
		return FacesContext.getCurrentInstance()
						   .getExternalContext()
						   .getRequestParameterMap()
						   .get("jobName");
	}
	
	public String updateTime(){
		
		if(!inProgress){
			
			TimeEntry entry = new TimeEntry();
			if(job == null) refreshJob();
			timeEntryDAO.save(entry, super.getUsername(), job.getJobId());
			inProgress = true;
		}
		else{
			
			TimeEntry entry = timeEntryDAO.findLastEntryForUser(super.getUsername());
			entry.setEndTime(Calendar.getInstance());
			timeEntryDAO.update(entry);
			inProgress = false;
		}
			
		return "this";
	}
	
	public BigDecimal getTimeElapsed(){
		
		if(job == null) refreshJob();
		List<TimeDifferential> totalTime = timeEntryDAO.findTotalTimeForUsersJob(getUsername(), job.getJobId());
		
		BigDecimal total = new BigDecimal(0);
		for (TimeDifferential timeDifferential : totalTime) {
			total = total.add(timeDifferential.getDiff());
		}		
		return total;
	}
	
	public List<NoteComment> getComments(){
	
		if(job == null) refreshJob();
		if(noteComments == null)
			noteComments = userJobAssignmentDAO.findAllAssociatedComments(getUsername(), job.getJobId(), this.finished);
		return noteComments;
	}
	
	public String addNote(){
		
		Note note = new Note(noteText);
		
		if(job == null) refreshJob();
		note.setJob(job);
		
		SysUser user = sysUserDAO.findByUsername(super.getUsername());
		note.setUser(user);
		
		noteDAO.save(note);
		
		noteComments.add(new NoteComment(noteText));
		noteText = "";
		return "this";
	}
	
	public String addComment(){
		
		if(job == null) refreshJob();
		
		TimeEntry entry = timeEntryDAO.findLastEntryForUser(super.getUsername(), job.getJobId());
		entry.setEntryComments(commentText);
		timeEntryDAO.update(entry);
		
		createAndUpdateNoteComments(entry);
		
		return "this";
	}

	public String finishJob(){
		
		job.setFinished(true);
		jobDAO.updateJobFinished(job);
		return "this";
	}
	
	private void createAndUpdateNoteComments(TimeEntry entry) {
		
		NoteComment noteComment = new NoteComment(commentText, entry.getStartTime(), entry.getEndTime());
		noteComments.add(noteComment);
		commentText = "";
	}
	
	public String adjustTime(){
		
		if(this.job == null) refreshJob();
		
		TimeEntry timeEntry = timeEntryDAO.findLastEntryForUser(super.getUsername(), job.getJobId());
		setTime(timeEntry);
		timeEntryDAO.update(timeEntry);
		
		adjustedTime = 0;
		return "this";
	}

	private void setTime(TimeEntry timeEntry) {
		
		Calendar endTime = timeEntry.getEndTime();
		timeService.addTime(endTime, adjustedTime);
		timeEntry.setEndTime(endTime);
	}
}

package com.dmcliver.timetracker.controller;

import java.util.Calendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.dmcliver.timetracker.TimeTrackerConstants.emptyString;

import com.dmcliver.timetracker.datalayer.JobDAO;
import com.dmcliver.timetracker.datalayer.TimeEntryDAO;
import com.dmcliver.timetracker.domain.Job;
import com.dmcliver.timetracker.domain.TimeDifferential;
import com.dmcliver.timetracker.domain.TimeEntry;

@Component
@ManagedBean
@RequestScoped
public class JobTimeEntryControllerBean extends ControllerBeanBase{

	@Autowired
	private JobDAO jobDAO;
	
	@Autowired
	private TimeEntryDAO timeEntryDAO;
	
	private boolean inProgress = false;
	private Job job;
	
	public Job getJob() {

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
	
	public boolean isInProgress() {
		return inProgress;
	}
	
	public String updateTime(){
		
		if(!inProgress){
			
			TimeEntry entry = new TimeEntry();
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
	
	public double getTimeElapsed(){
		
		if(job == null) refreshJob();
		List<TimeDifferential> totalTime = timeEntryDAO.findTotalTimeForUsersJob(getUsername(), job.getJobId());
		
		double total = 0;
		for (TimeDifferential timeDifferential : totalTime) {
			total += timeDifferential.getDiff();
		}		
		return total;
	}
}

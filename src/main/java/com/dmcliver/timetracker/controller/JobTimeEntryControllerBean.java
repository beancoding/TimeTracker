package com.dmcliver.timetracker.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dmcliver.timetracker.datalayer.JobDAO;
import com.dmcliver.timetracker.domain.Job;

@Component
@ManagedBean
@RequestScoped
public class JobTimeEntryControllerBean {

	@Autowired
	private JobDAO jobDAO;
	
	private boolean inProgress = false;
	private Job job;
	
	public Job getJob() {

		refreshJob();
		return job;
	}

	private void refreshJob(){
		
		String jobId = getJobId();
		if(jobDAO != null)
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
}

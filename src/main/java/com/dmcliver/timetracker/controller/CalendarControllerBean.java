package com.dmcliver.timetracker.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dmcliver.timetracker.datalayer.UserJobAssignmentDAO;
import com.dmcliver.timetracker.domain.TimeDifferential;

@Component
@ManagedBean
@RequestScoped
public class CalendarControllerBean extends ControllerBeanBase{

	private Date startDate = new Date();
	private Date stopDate = new Date();
	private List<TimeDifferential> filteredData;
	
	@Autowired
	private UserJobAssignmentDAO dao;
	private List<TimeDifferential> reportData;
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public Date getStopDate() {
		return stopDate;
	}
	public void setStopDate(Date stopDate) {
		this.stopDate = stopDate;
	}
	
	public String viewReport(){
		
		Calendar startCal = Calendar.getInstance();
		startCal.setTime(startDate);
		
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(stopDate);
		endCal.add(Calendar.DAY_OF_MONTH, 1);
		
		reportData = dao.findAllEntriesForRange(super.getUsername(), startCal, endCal);
		return "";
	}
	
	public List<TimeDifferential> getDateAndTimeForJob(){
		
		if(reportData == null)
			new ArrayList<TimeDifferential>();
		return reportData;
	}
	
	public List<TimeDifferential> getFilteredData() {
		return filteredData;
	}
	public void setFilteredData(List<TimeDifferential> filteredData) {
		this.filteredData = filteredData;
	}
}

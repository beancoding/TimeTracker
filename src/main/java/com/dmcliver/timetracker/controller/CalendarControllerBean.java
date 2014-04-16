package com.dmcliver.timetracker.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dmcliver.timetracker.datalayer.UserJobAssignmentDAO;
import com.dmcliver.timetracker.domain.ReportSummary;
import com.dmcliver.timetracker.domain.TimeDifferential;

@Component
@ManagedBean
@RequestScoped
public class CalendarControllerBean extends ControllerBeanBase{
	
	@Autowired
	private UserJobAssignmentDAO dao;

	private Date startDate = new Date();
	private Date stopDate = new Date();
	private List<ReportSummary> filteredData;
	private List<ReportSummary> reportData;
	
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
	
	public List<ReportSummary> getFilteredData() {
		return filteredData;
	}
	public void setFilteredData(List<ReportSummary> filteredData) {
		this.filteredData = filteredData;
	}
	
	public List<ReportSummary> getDateAndTimeForJob(){
		
		if(reportData == null)
			new ArrayList<ReportSummary>();
		return reportData;
	}
	
	public String viewReport(){
		
		Calendar startCal = setStartCalendarDate();
		Calendar endCal = setEndCalendarDate();
		
		List<TimeDifferential> foundReportData = dao.findAllEntriesForRange(super.getUsername(), startCal, endCal);
		
		aggregateData(foundReportData);
		
		return "";
	}
	
	private Calendar setStartCalendarDate() {
		
		Calendar startCal = Calendar.getInstance();
		startCal.setTime(startDate);
		return startCal;
	}
	
	private Calendar setEndCalendarDate() {
		
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(stopDate);
		endCal.add(Calendar.DAY_OF_MONTH, 1);
		return endCal;
	}
	
	private void aggregateData(List<TimeDifferential> foundReportData) {
		
		Map<String, ReportSummary> reportDataAggregate = new HashMap<String, ReportSummary>();
		
		for (TimeDifferential timeDiff : foundReportData) {
			
			if(reportDataAggregate.containsKey(timeDiff.toString()))
				reportDataAggregate.get(timeDiff.toString()).add(timeDiff.getDiff());
			else
				reportDataAggregate.put(timeDiff.toString(), new ReportSummary(timeDiff.getJob(),timeDiff.getDay(),timeDiff.getDiff()));
		}
		
		reportData = new ArrayList<ReportSummary>(reportDataAggregate.values());
	}
}

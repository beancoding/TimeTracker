package com.dmcliver.timetracker.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

import static javax.persistence.GenerationType.SEQUENCE;

import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "TimeEntry")
public class TimeEntry {

	private int TimeEntryId;
	private Calendar startTime;
	private Calendar endTime;
	private String entryComments;
	private UserJobAssignment userJobAssignment;
	
	@Id
	@Column(name = "TimeEntryId")
	@GeneratedValue(generator = "TimeEntryIdGenerator", strategy = SEQUENCE)
	@SequenceGenerator(name = "TimeEntryIdGenerator", allocationSize = 1, sequenceName = "TimeEntryGenerator")
	public int getTimeEntryId() {
		return TimeEntryId;
	}
	public void setTimeEntryId(int timeEntryId) {
		TimeEntryId = timeEntryId;
	}
	
	@Column(name = "StartTime", nullable = false)
	public Calendar getStartTime() {
		return startTime;
	}
	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}
	
	@Column(name = "EndTime")
	public Calendar getEndTime() {
		return endTime;
	}
	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}
	
	@Column(name = "EntryComments")
	public String getEntryComments() {
		return entryComments;
	}
	public void setEntryComments(String entryComments) {
		this.entryComments = entryComments;
	}
	
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "SysUserId", referencedColumnName = "SysUserId", nullable = false),
		@JoinColumn(name = "JobId", referencedColumnName = "JobId", nullable = false),
	})
	public UserJobAssignment getUserJobAssignment() {
		return userJobAssignment;
	}
	public void setUserJobAssignment(UserJobAssignment userJobAssignment) {
		this.userJobAssignment = userJobAssignment;
	}
}

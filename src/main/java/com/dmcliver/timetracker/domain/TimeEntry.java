package com.dmcliver.timetracker.domain;

import java.util.Calendar;

import javax.persistence.*;

@Entity
@Table(name = "TimeEntry")
public class TimeEntry {

	private TimeEntryPK timeEntryPK;
	private SysUser sysUser;
	private Job job;
	private Calendar startTime;
	private Calendar endTime;
	private String entryComments;
	
	public TimeEntry(TimeEntryPK timeEntryPK, SysUser sysUser, Job job) {
		
		this.timeEntryPK = timeEntryPK;
		this.sysUser = sysUser;
		this.job = job;
	}
	
	public TimeEntry() {}
	
	@Id
	public TimeEntryPK getTimeEntryPK() {
		return timeEntryPK;
	}
	public void setTimeEntryPK(TimeEntryPK timeEntryPK) {
		this.timeEntryPK = timeEntryPK;
	}
	
	@ManyToOne
	@JoinColumn(name = "SysUserId", nullable = false, insertable = false, updatable = false)
	public SysUser getSysUser() {
		return sysUser;
	}
	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}
	
	@ManyToOne
	@JoinColumn(name = "JobId", nullable = false, insertable = false, updatable = false)
	public Job getJob() {
		return job;
	}
	public void setJob(Job job) {
		this.job = job;
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
}

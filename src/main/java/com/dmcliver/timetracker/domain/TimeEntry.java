package com.dmcliver.timetracker.domain;

import javax.persistence.*;

@Entity
@Table(name = "TimeEntry")
public class TimeEntry {

	private TimeEntryPK timeEntryPK;
	private SysUser sysUser;
	private Job job;

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
}

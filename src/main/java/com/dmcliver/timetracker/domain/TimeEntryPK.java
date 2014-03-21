package com.dmcliver.timetracker.domain;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TimeEntryPK implements Serializable{

	private static final long serialVersionUID = -5584144769207079839L;

	private String userName;
	private UUID jobId;
	
	public TimeEntryPK(String userName, UUID jobId) {
		this.userName = userName;
		this.jobId = jobId;
	}
	
	public TimeEntryPK() {}
	
	@Column(name = "SysUserId")
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Column(name = "JobId")
	public UUID getJobId() {
		return jobId;
	}
	public void setJobId(UUID jobId) {
		this.jobId = jobId;
	}
}

package com.dmcliver.timetracker.domain;

import javax.persistence.*;

@Entity
@Table(name = "UserJobAssignment")
public class UserJobAssignment {

	private UserJobAssignmentPK userJobAssignmentPK;
	private SysUser sysUser;
	private Job job;
	
	public UserJobAssignment(UserJobAssignmentPK userJobAssignmentPK, SysUser sysUser, Job job) {
		
		this.userJobAssignmentPK = userJobAssignmentPK;
		this.sysUser = sysUser;
		this.job = job;
	}
	
	public UserJobAssignment() {}
	
	@Id
	public UserJobAssignmentPK getUserJobAssignmentPK() {
		return userJobAssignmentPK;
	}
	public void setUserJobAssignmentPK(UserJobAssignmentPK userJobAssignmentPK) {
		this.userJobAssignmentPK = userJobAssignmentPK;
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

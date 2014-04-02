package com.dmcliver.timetracker.domain;

import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "JobTask")
public class Job {

	private UUID jobId;
	private String jobName;
	private boolean finished;
	private double estimate;
	
	public Job() {}
	
	public Job(String jobName, double estimate, UUID jobId) {
		
		this.jobName = jobName;
		this.estimate = estimate;
		this.jobId = jobId;
	}
	
	@Id
	@Column(name = "JobId")
	public UUID getJobId() {
		return jobId;
	}
	public void setJobId(UUID jobId) {
		this.jobId = jobId;
	}
	
	@Column(name = "JobName", nullable = false)
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String name) {
		this.jobName = name;
	}
	
	@Column(name = "Finished")
	public boolean isFinished() {
		return finished;
	}
	public void setFinished(boolean finished) {
		this.finished = finished;
	}
	
	@Column(name = "Estimate")
	public double getEstimate() {
		return estimate;
	}
	public void setEstimate(double estimate) {
		this.estimate = estimate;
	}
}

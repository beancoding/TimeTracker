package com.dmcliver.timetracker.datalayer;

import java.util.List;

import com.dmcliver.timetracker.domain.Job;
import com.dmcliver.timetracker.domain.UserJobAssignment;

public interface JobDAO {

	public abstract List<Job> findAllForUser(String userName);
	public abstract void save(Job job);
	public abstract void save(UserJobAssignment userJobAssignment);
	public abstract Job findByName(String jobName);
}
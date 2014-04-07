package com.dmcliver.timetracker.datalayer;

import java.util.List;
import java.util.UUID;

import com.dmcliver.timetracker.domain.TimeDifferential;
import com.dmcliver.timetracker.domain.TimeEntry;

public interface TimeEntryDAO {

	public abstract void save(TimeEntry entry, String username, UUID uuid);
	public abstract TimeEntry findLastEntryForUser(String user);
	public abstract void update(TimeEntry entry);
	public abstract List<TimeDifferential> findTotalTimeForUsersJob(String userName, UUID jobId);
	public abstract TimeEntry findLastEntryForUser(String username, UUID jobId);
}
package com.dmcliver.timetracker.datalayer;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import com.dmcliver.timetracker.domain.NoteComment;
import com.dmcliver.timetracker.domain.TimeDifferential;

public interface UserJobAssignmentDAO {

	public abstract List<NoteComment> findAllAssociatedComments(String userName, UUID jobId, boolean jobFinished);
	public abstract List<TimeDifferential> findAllEntriesForRange(String username, Calendar startCal, Calendar endCal);
}
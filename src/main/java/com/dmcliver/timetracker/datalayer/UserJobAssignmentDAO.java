package com.dmcliver.timetracker.datalayer;

import java.util.List;
import java.util.UUID;

import com.dmcliver.timetracker.domain.NoteComment;

public interface UserJobAssignmentDAO {

	public abstract List<NoteComment> findAllAssociatedComments(
			String userName, UUID jobId);

}
package com.dmcliver.timetracker.datalayer;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.dmcliver.timetracker.domain.Job;
import com.dmcliver.timetracker.domain.Note;
import com.dmcliver.timetracker.domain.NoteComment;
import com.dmcliver.timetracker.domain.SysUser;
import com.dmcliver.timetracker.domain.TimeEntry;
import com.dmcliver.timetracker.domain.UserJobAssignment;
import com.dmcliver.timetracker.domain.UserJobAssignmentPK;

@Repository
public class UserJobAssignmentDAOImpl implements UserJobAssignmentDAO {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<NoteComment> findAllAssociatedComments(String userName, UUID jobId){
		
		UserJobAssignment uja = entityManager.find(UserJobAssignment.class, new UserJobAssignmentPK(userName, jobId));
		
		List<NoteComment> noteComments = findAllComments(uja);
		List<NoteComment> notes = findAllNotes(userName, jobId);
		
		noteComments.addAll(notes);
		
		return noteComments;
	}

	private List<NoteComment> findAllNotes(String userName, UUID jobId) {
		
		CriteriaBuilder criteria = entityManager.getCriteriaBuilder();
		CriteriaQuery<NoteComment> query = criteria.createQuery(NoteComment.class);
		
		Root<Note> n = query.from(Note.class);
		Join<Note, SysUser> u = n.join("user");
		Join<Note, Job> j = n.join("job");
		
		query.multiselect(n.get("content"))
			 .where(
				 criteria.and(
					 criteria.equal(j.get("jobId"), jobId),
					 criteria.equal(u.get("userName"), userName),
					 criteria.notEqual(n.get("content"), criteria.nullLiteral(String.class))
				 )
			 );
		
		List<NoteComment> resultList = entityManager.createQuery(query).getResultList();
		return resultList;
	}

	private List<NoteComment> findAllComments(UserJobAssignment uja) {
		
		CriteriaBuilder criteria = entityManager.getCriteriaBuilder();
		CriteriaQuery<NoteComment> query = criteria.createQuery(NoteComment.class);
		
		Root<TimeEntry> te = query.from(TimeEntry.class);
		query.multiselect(te.get("entryComments"), te.get("startTime"), te.get("endTime"))
			 .where(
				 criteria.and(
					 criteria.notEqual(te.get("entryComments"), criteria.nullLiteral(String.class)),
					 criteria.equal(te.get("userJobAssignment"), uja)
				 )
			 );
		
		List<NoteComment> resultList = entityManager.createQuery(query).getResultList();
		return resultList;
	}
}

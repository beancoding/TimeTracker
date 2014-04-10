package com.dmcliver.timetracker.datalayer;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.hibernate.ejb.criteria.OrderImpl;
import org.springframework.stereotype.Repository;

import com.dmcliver.timetracker.domain.Job;
import com.dmcliver.timetracker.domain.Note;
import com.dmcliver.timetracker.domain.NoteComment;
import com.dmcliver.timetracker.domain.SysUser;
import com.dmcliver.timetracker.domain.TimeDifferential;
import com.dmcliver.timetracker.domain.TimeEntry;
import com.dmcliver.timetracker.domain.UserJobAssignment;
import com.dmcliver.timetracker.domain.UserJobAssignmentPK;
import static com.dmcliver.timetracker.QueryUtils.PropertyUtil.*;

@Repository
public class UserJobAssignmentDAOImpl implements UserJobAssignmentDAO {

	@PersistenceContext
	private EntityManager entityManager;
	
	private UserJobAssignment ujaProx = from(UserJobAssignment.class);
	private TimeEntry teProx = from(TimeEntry.class);
	private Job jProx = from(Job.class);
	private SysUser uProx = from(SysUser.class);
	
	@Override
	public List<NoteComment> findAllAssociatedComments(String userName, UUID jobId, boolean jobFinished){
		
		UserJobAssignment uja = entityManager.find(UserJobAssignment.class, new UserJobAssignmentPK(userName, jobId));
		
		List<NoteComment> noteComments = findAllComments(uja);
		
		if(!jobFinished){
			List<NoteComment> notes = findAllNotes(userName, jobId);
			noteComments.addAll(notes);
		}
		
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

	@Override
	public List<TimeDifferential> findAllEntriesForRange(String username, Calendar startCal, Calendar endCal) {
		
		CriteriaBuilder criteria = entityManager.getCriteriaBuilder();
		CriteriaQuery<TimeDifferential> query = criteria.createQuery(TimeDifferential.class);
		
		Root<TimeEntry> te = query.from(TimeEntry.class);
		
		
		
		Join<TimeEntry, UserJobAssignment> uja = te.join(toStr(teProx.getUserJobAssignment()));
		Join<UserJobAssignment, SysUser> u = uja.join(toStr(ujaProx.getSysUser()));
		Join<UserJobAssignment, Job> j = uja.join(toStr(ujaProx.getJob()));
		
		query.multiselect(
				
				j.get(toStr(jProx.getJobName())), 
				te.get(toStr(teProx.getStartTime())), 
				te.get(toStr(teProx.getEndTime()))
			 )
			 .where(
				 criteria.and(
					
					 criteria.equal(u.get(toStr(uProx.getUserName())), username),
					 criteria.notEqual(j.get(toStr(jProx.isFinished())), true),
					 criteria.notEqual(te.get(toStr(teProx.getEndTime())), criteria.nullLiteral(Calendar.class))
				 )
			 )
			 .orderBy(
		
				 new OrderImpl(te.get(toStr(teProx.getStartTime())), false), 
				 new OrderImpl(j.get("jobName"), true)
			 );
		
		return entityManager.createQuery(query).getResultList();
	}
}

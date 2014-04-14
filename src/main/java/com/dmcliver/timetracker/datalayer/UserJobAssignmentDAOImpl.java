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
	
	private UserJobAssignment uja = from(UserJobAssignment.class);
	private TimeEntry te = from(TimeEntry.class);
	private Job j = from(Job.class);
	private SysUser u = from(SysUser.class);
	
	@Override
	public List<NoteComment> findAllAssociatedComments(String userName, UUID jobId, boolean jobFinished){
		
		UserJobAssignment ujaJPQ = entityManager.find(UserJobAssignment.class, new UserJobAssignmentPK(userName, jobId));
		
		List<NoteComment> noteComments = findAllComments(ujaJPQ);
		
		if(!jobFinished){
			List<NoteComment> notes = findAllNotes(userName, jobId);
			noteComments.addAll(notes);
		}
		
		return noteComments;
	}

	private List<NoteComment> findAllNotes(String userName, UUID jobId) {
		
		CriteriaBuilder criteria = entityManager.getCriteriaBuilder();
		CriteriaQuery<NoteComment> query = criteria.createQuery(NoteComment.class);
		
		Root<Note> nJPQ = query.from(Note.class);
		Join<Note, SysUser> uJPQ = nJPQ.join("user");
		Join<Note, Job> jobJPQ = nJPQ.join("job");
		
		query.multiselect(nJPQ.get("content"))
			 .where(
				 criteria.and(
					 criteria.equal(jobJPQ.get("jobId"), jobId),
					 criteria.equal(uJPQ.get("userName"), userName),
					 criteria.notEqual(nJPQ.get("content"), criteria.nullLiteral(String.class))
				 )
			 );
		
		List<NoteComment> resultList = entityManager.createQuery(query).getResultList();
		return resultList;
	}

	private List<NoteComment> findAllComments(UserJobAssignment ujaJPQ) {
		
		CriteriaBuilder criteria = entityManager.getCriteriaBuilder();
		CriteriaQuery<NoteComment> query = criteria.createQuery(NoteComment.class);
		
		Root<TimeEntry> teJPQ = query.from(TimeEntry.class);
		query.multiselect(teJPQ.get("entryComments"), teJPQ.get("startTime"), teJPQ.get("endTime"))
			 .where(
				 criteria.and(
					 criteria.notEqual(teJPQ.get("entryComments"), criteria.nullLiteral(String.class)),
					 criteria.equal(teJPQ.get("userJobAssignment"), ujaJPQ)
				 )
			 );
		
		List<NoteComment> resultList = entityManager.createQuery(query).getResultList();
		return resultList;
	}

	@Override
	public List<TimeDifferential> findAllEntriesForRange(String username, Calendar startCal, Calendar endCal) {
		
		CriteriaBuilder criteria = entityManager.getCriteriaBuilder();
		CriteriaQuery<TimeDifferential> query = criteria.createQuery(TimeDifferential.class);
		
		Root<TimeEntry> teJPQ = query.from(TimeEntry.class);
		
		Join<TimeEntry, UserJobAssignment> ujaJPQ = teJPQ.join(toStr(te.getUserJobAssignment()));
		Join<UserJobAssignment, SysUser> uJPQ = ujaJPQ.join(toStr(uja.getSysUser()));
		Join<UserJobAssignment, Job> jobJPQ = ujaJPQ.join(toStr(uja.getJob()));
		
		query.multiselect(
				
				jobJPQ.get(toStr(j.getJobName())), 
				teJPQ.get(toStr(te.getStartTime())), 
				teJPQ.get(toStr(te.getEndTime()))
			 )
			 .where(
				 criteria.and(
					
					 criteria.equal(uJPQ.get(toStr(u.getUserName())), username),
					 criteria.notEqual(teJPQ.get(toStr(te.getEndTime())), criteria.nullLiteral(Calendar.class)),
					 criteria.greaterThanOrEqualTo(teJPQ.<Calendar>get(toStr(te.getStartTime())), startCal),
					 criteria.lessThan(teJPQ.<Calendar>get(toStr(te.getEndTime())), endCal)
				 )
			 )
			 .orderBy(
		
				 new OrderImpl(teJPQ.get(toStr(te.getStartTime())), false), 
				 new OrderImpl(jobJPQ.get(toStr(j.getJobName())), true)
			 );
		
		return entityManager.createQuery(query).getResultList();
	}
}

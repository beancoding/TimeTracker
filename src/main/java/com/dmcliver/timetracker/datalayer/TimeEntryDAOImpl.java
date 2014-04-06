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

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dmcliver.timetracker.domain.Job;
import com.dmcliver.timetracker.domain.SysUser;
import com.dmcliver.timetracker.domain.TimeDifferential;
import com.dmcliver.timetracker.domain.TimeEntry;
import com.dmcliver.timetracker.domain.UserJobAssignment;
import com.dmcliver.timetracker.domain.UserJobAssignmentPK;

@Repository
public class TimeEntryDAOImpl implements TimeEntryDAO {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	@Transactional
	public void save(TimeEntry entry, String username, UUID jobId) {
		
		UserJobAssignment userJobAssignment = entityManager.find(UserJobAssignment.class, new UserJobAssignmentPK(username, jobId));
		entry.setUserJobAssignment(userJobAssignment);
		entry.setStartTime(Calendar.getInstance());
		entityManager.persist(entry);
	}

	@Override
	public TimeEntry findLastEntryForUser(String user) {
		
		CriteriaBuilder criteria = entityManager.getCriteriaBuilder();
		CriteriaQuery<TimeEntry> query = criteria.createQuery(TimeEntry.class);
		
		Root<TimeEntry> te = query.from(TimeEntry.class);
		Join<TimeEntry, UserJobAssignment> uja = te.join("userJobAssignment");
		Join<UserJobAssignment, SysUser> u = uja.join("sysUser");
		
		query.select(te).where(
			
				criteria.and(
				criteria.equal(te.get("endTime"), criteria.nullLiteral(Calendar.class)), 
				criteria.equal(u.get("userName"), user)
			)
		);
		
		List<TimeEntry> resultList = entityManager.createQuery(query)
												  .setMaxResults(1)
											  	  .getResultList();
		
		return resultList.size() > 0 ? resultList.get(0) : null;
	}

	@Override
	@Transactional
	public void update(TimeEntry entry) {
		
		entityManager.merge(entry);
	}
	
	@Override
	public List<TimeDifferential> findTotalTimeForUsersJob(String userName, UUID jobId){
		
		CriteriaBuilder criteria = entityManager.getCriteriaBuilder();
		CriteriaQuery<TimeDifferential> query = criteria.createQuery(TimeDifferential.class);
		
		Root<TimeEntry> te = query.from(TimeEntry.class);
		
		Join<TimeEntry, UserJobAssignment> uja = te.join("userJobAssignment");
		Join<UserJobAssignment, SysUser> u = uja.join("sysUser");
		Join<UserJobAssignment, Job> j = uja.join("job");
		
		query.multiselect(te.get("startTime"), te.get("endTime")).where(
			
			criteria.and(
				criteria.and(
						
					criteria.equal(u.get("userName"), userName),
					criteria.equal(j.get("jobId"), jobId)
				),
				criteria.notEqual(te.get("endTime"), criteria.nullLiteral(Calendar.class)) 
			)
		);
		
		return entityManager.createQuery(query).getResultList();
	}
}

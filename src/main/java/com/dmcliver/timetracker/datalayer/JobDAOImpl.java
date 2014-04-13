package com.dmcliver.timetracker.datalayer;

import java.util.List;
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
import com.dmcliver.timetracker.domain.UserJobAssignment;

@Repository
public class JobDAOImpl implements JobDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public Job findByName(String jobName) {

		CriteriaBuilder criteria = entityManager.getCriteriaBuilder();
		CriteriaQuery<Job> query = criteria.createQuery(Job.class);
		
		Root<Job> j = query.from(Job.class);
		query.select(j).where(criteria.equal(criteria.lower(j.<String>get("jobName")), jobName.toLowerCase()));
		
		List<Job> result = entityManager.createQuery(query)
							   .setMaxResults(1)
							   .getResultList();
							   
		return result.size() > 0 ? result.get(0) : null;
	}
	
	@Override
	public List<Job> findAllForUser(String userName){
		
		CriteriaBuilder criteria = entityManager.getCriteriaBuilder();
		CriteriaQuery<Job> query = criteria.createQuery(Job.class);
		
		Root<UserJobAssignment> jobAss = query.from(UserJobAssignment.class);
		
		Join<UserJobAssignment, Job> j = jobAss.join("job");
		Join<UserJobAssignment, SysUser> u = jobAss.join("sysUser");
		
		query.select(j).where(criteria.equal(u.get("userName"), userName));
		
		return entityManager.createQuery(query).getResultList();
	}
	
	@Override
	@Transactional
	public void save(Job job) {
		
		entityManager.persist(job);
	}

	@Override
	@Transactional
	public void save(UserJobAssignment userJobAssignment) {
		
		entityManager.persist(userJobAssignment);
	}

	@Transactional
	public void updateJobFinished(Job job) {
		
		Job j = entityManager.find(Job.class, job.getJobId());
		j.setFinished(job.isFinished());
		entityManager.merge(j);
	}
}

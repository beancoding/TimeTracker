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
import org.springframework.transaction.annotation.Transactional;

import com.dmcliver.timetracker.domain.Job;
import com.dmcliver.timetracker.domain.SysUser;
import com.dmcliver.timetracker.domain.UserJobAssignment;

@Repository
public class JobDAOImpl implements JobDAO {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Job> findAllForUser(String userName){
		
		CriteriaBuilder criteria = entityManager.getCriteriaBuilder();
		CriteriaQuery<Job> query = criteria.createQuery(Job.class);
		
		Root<UserJobAssignment> uja = query.from(UserJobAssignment.class);
		
		Join<UserJobAssignment, Job> j = uja.join("job");
		Join<UserJobAssignment, SysUser> u = uja.join("sysUser");
		
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
}

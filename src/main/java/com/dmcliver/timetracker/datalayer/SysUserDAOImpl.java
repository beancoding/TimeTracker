package com.dmcliver.timetracker.datalayer;

import java.util.List;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dmcliver.timetracker.domain.SysUser;

@Repository
public class SysUserDAOImpl implements SysUserDAO {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<SysUser> findAll(){
		
		CriteriaBuilder criteria = entityManager.getCriteriaBuilder();
		CriteriaQuery<SysUser> query = criteria.createQuery(SysUser.class);
		
		query.select(query.from(SysUser.class));
		
		return entityManager.createQuery(query).getResultList();
	}

	@Override
	@Transactional
	public void save(SysUser user) {
		entityManager.persist(user);
	}

	@Override
	public SysUser findByUsername(String username) {
		
		CriteriaBuilder criteria = entityManager.getCriteriaBuilder();
		CriteriaQuery<SysUser> query = criteria.createQuery(SysUser.class);
		
		Root<SysUser> u = query.from(SysUser.class);
		query.select(u).where(criteria.equal(u.get("userName"), username));
		
		List<SysUser> resultList = entityManager.createQuery(query).getResultList();
		
		return resultList.size() > 0 ? resultList.get(0) : null;
	}
}

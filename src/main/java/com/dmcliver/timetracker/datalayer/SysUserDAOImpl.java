package com.dmcliver.timetracker.datalayer;

import java.util.List;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.stereotype.Repository;

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
}

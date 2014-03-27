package com.dmcliver.timetracker.datalayer;

import java.util.List;
import java.util.UUID;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dmcliver.timetracker.domain.Job;
import com.dmcliver.timetracker.domain.Note;
import com.dmcliver.timetracker.domain.SysUser;

@Repository
public class NoteDAOImpl implements NoteDAO {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	@Transactional
	public void save(Note note){
		entityManager.persist(note);
	}

	@Override
	public List<Note> findByUser(String name) {

		CriteriaBuilder criteria = entityManager.getCriteriaBuilder();
		CriteriaQuery<Note> query = criteria.createQuery(Note.class);
		
		Root<Note> n = query.from(Note.class);
		Join<Note, SysUser> u = n.join("user");
		
		query.select(n).where(
		
			criteria.and(
			
				criteria.equal(u.get("userName"), name), 
				criteria.equal(n.get("job"), criteria.nullLiteral(Job.class))
			)
		);
		
		return entityManager.createQuery(query).getResultList();
	}
}

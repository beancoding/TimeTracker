package com.dmcliver.timetracker.datalayer;

import javax.persistence.*;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dmcliver.timetracker.domain.Note;

@Repository
public class NoteDAOImpl implements NoteDAO {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	@Transactional
	public void save(Note note){
		entityManager.persist(note);
	}
}

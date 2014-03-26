package com.dmcliver.timetracker.datalayer;

import org.springframework.transaction.annotation.Transactional;

import com.dmcliver.timetracker.domain.Note;

public interface NoteDAO {

	public abstract void save(Note note);

}
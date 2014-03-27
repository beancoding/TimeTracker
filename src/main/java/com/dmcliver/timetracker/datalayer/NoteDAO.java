package com.dmcliver.timetracker.datalayer;

import java.util.List;

import com.dmcliver.timetracker.domain.Note;

public interface NoteDAO {

	void save(Note note);
	List<Note> findByUser(String name);
}
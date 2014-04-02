package com.dmcliver.timetracker.domain;

import javax.persistence.*;

@Entity
@Table(name = "Note")
public class Note {

	private int noteId;
	private String content;
	private SysUser user;
	private Job job;
	
	public Note(String content) {
		this.content = content;
	}
	
	public Note() {}
	
	@Id
	@Column(name = "NoteId")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NoteGenerator")
	@SequenceGenerator(name = "NoteGenerator", sequenceName = "NoteGenerator", allocationSize = 1)
	public int getNoteId() {
		return noteId;
	}
	public void setNoteId(int noteId) {
		this.noteId = noteId;
	}
	
	@Column(name = "Content", nullable = false, length = 1200)
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@ManyToOne
	@JoinColumn(name = "SysUserId", nullable = false)
	public SysUser getUser() {
		return user;
	}
	public void setUser(SysUser user) {
		this.user = user;
	}
	
	@ManyToOne
	@JoinColumn(name = "JobId")
	public Job getJob() {
		return job;
	}
	public void setJob(Job job) {
		this.job = job;
	}
}

package com.dmcliver.timetracker.domain;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NoteComment {

	private String content;
	private Calendar start;
	private Calendar end;
	private SimpleDateFormat formatter;

	public NoteComment(String content, Calendar start, Calendar end) {
		
		this.content = content;
		this.start = start;
		this.end = end;
		formatter = new SimpleDateFormat("yyyy/MM/dd hh:mm a");
	}

	public NoteComment(String content) {
		
		this(content, null, null);
	}
	
	public String getContent() {
		return content;
	}
	
	public String getStart(){

		return formatDate(start);
	}
	
	public String getEnd(){
		
		return formatDate(end);
	}

	private String formatDate(Calendar date) {
		
		return date == null ? 
			   "" : 
			   formatter.format(date.getTime());
	}
}

package com.dmcliver.timetracker.domain;

import java.util.Calendar;

public class TimeDifferential {

	private Calendar start;
	private Calendar end;
	private final long hourDiv = 1000 * 60 * 60;
	
	public TimeDifferential(Calendar start, Calendar end) {
		
		this.start = start;
		this.end = end;
	}
	
	public double getDiff(){
		
		long endTime = end.getTimeInMillis();
		long startTime = start.getTimeInMillis();
		
		long diff = endTime - startTime;
		
		long hours= diff / hourDiv;
		
		double minutes = calculateMinutes(diff, hours);
		
		return hours + minutes;
	}

	private double calculateMinutes(long diff, long hours) {
		
		double minutes = diff - hours * hourDiv;
		minutes = minutes / hourDiv;
		minutes = Math.round(minutes * 10) / 10.0;
		return minutes;
	}
}

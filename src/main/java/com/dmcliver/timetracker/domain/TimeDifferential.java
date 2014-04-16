package com.dmcliver.timetracker.domain;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeDifferential {

	private String job = "";
	private Calendar start;
	private Calendar end;
	private final long hourDiv = 1000 * 60 * 60;
	
	public TimeDifferential(Calendar start, Calendar end) {
		
		this.start = start;
		this.end = end;
	}

	public TimeDifferential(String job, Calendar start, Calendar end) {
		
		this.job = job;
		this.start = start;
		this.end = end;
	}
	
	public String getJob(){
		return job;
	}
	
	public String getDay(){
		return new SimpleDateFormat("dd/MM/yy").format(start.getTime());
	}
	
	public BigDecimal getDiff(){
		
		long endTime = end.getTimeInMillis();
		long startTime = start.getTimeInMillis();
		
		long diff = endTime - startTime;
		
		long hours = diff / hourDiv;
		
		BigDecimal minutes = calculateMinutes(diff, hours);
		
		return toDec(hours).add(minutes);
	}

	private BigDecimal calculateMinutes(long diff, long hours) {
		
		BigDecimal minutes = toDec(diff - hours * hourDiv);
		minutes = minutes.divide(toDec(hourDiv), BigDecimal.ROUND_HALF_UP);
		minutes = toDec(Math.round(minutes.doubleValue() * 10)).divide(toDec(10.0), BigDecimal.ROUND_HALF_UP);
		return minutes;
	}
	
	private BigDecimal toDec(double d){
		
		return new BigDecimal(d).setScale(2);
	}
	
	public String toString(){
		
		return job + ":" + getDay();
	}
}

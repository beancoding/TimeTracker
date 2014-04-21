package com.dmcliver.timetracker.service;

import java.util.Calendar;

import junit.framework.Assert;

import org.junit.Test;

public class TimeServiceTest {

	@Test
	public void addTime_WithPositiveValue_AddsTime(){
		
		TimeService t = new TimeServiceImpl();
		Calendar dateTime = buildDateTime();
		t.addTime(dateTime, 1.2);
		
		int hour = dateTime.get(Calendar.HOUR_OF_DAY);
		int minute = dateTime.get(Calendar.MINUTE);
		
		Assert.assertEquals(16, hour);
		Assert.assertEquals(33, minute);
	}

	@Test
	public void addTime_WithNegativeValue_SubtractsTime(){
		
		TimeService t = new TimeServiceImpl();
		Calendar dateTime = buildDateTime();
		t.addTime(dateTime, -1.2);
		
		int hour = dateTime.get(Calendar.HOUR_OF_DAY);
		int minute = dateTime.get(Calendar.MINUTE);
		
		Assert.assertEquals(14, hour);
		Assert.assertEquals(9, minute);
	}
	
	@Test
	public void addTime_WithValue_RoundsCorrectly(){
		
		TimeService t = new TimeServiceImpl();
		Calendar dateTime = buildDateTime();
		t.addTime(dateTime, 1.257248);
		
		int hour = dateTime.get(Calendar.HOUR_OF_DAY);
		int minute = dateTime.get(Calendar.MINUTE);
		
		Assert.assertEquals(16, hour);
		Assert.assertEquals(39, minute);
	}
	
	private Calendar buildDateTime() {
		
		Calendar dateTime = Calendar.getInstance();
		dateTime.set(2012, 11, 21, 15, 21, 33);
		return dateTime;
	}
}

package com.dmcliver.timetracker.service;

import java.util.Calendar;

import org.springframework.stereotype.Service;

@Service
public class TimeServiceImpl implements TimeService {

	@Override
	public void addTime(Calendar endTime, double adjustedTime) {

		int hours = (int)adjustedTime;
		double minutes = adjustedTime - hours;
		minutes = minutes * 10;
		long roundedMinutes = Math.round(minutes);
		roundedMinutes = roundedMinutes * 6;
		endTime.add(Calendar.HOUR_OF_DAY, hours);
		endTime.add(Calendar.MINUTE, (int)roundedMinutes);
	}
}

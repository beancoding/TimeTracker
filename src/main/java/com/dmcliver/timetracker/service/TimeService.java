package com.dmcliver.timetracker.service;

import java.util.Calendar;

public interface TimeService {

	void addTime(Calendar endTime, double adjustedTime);
}

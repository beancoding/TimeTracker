package com.dmcliver.timetracker.domain;

import java.math.BigDecimal;

public class ReportSummary {

	private BigDecimal total;
	private String job;
	private String day;
	
	public ReportSummary(String job, String day, BigDecimal diff) {
		
		this.job = job;
		this.day = day;
		total = diff;
	}

	public void add(BigDecimal diff) {
		total = total.add(diff);
	}

	public BigDecimal getTotal() {
		return total;
	}

	public String getJob() {
		return job;
	}

	public String getDay() {
		return day;
	}
}

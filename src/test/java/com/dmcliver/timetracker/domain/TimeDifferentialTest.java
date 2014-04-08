package com.dmcliver.timetracker.domain;

import java.math.BigDecimal;
import java.util.Calendar;

import org.junit.Assert;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.*;

@RunWith(Theories.class)
public class TimeDifferentialTest {
	
	@DataPoints
	public static Diff[] allData(){

		return new Diff[]{
				
			new Diff(2, 1.0),
			new Diff(3, 1.1),
			new Diff(6, 1.1),
			new Diff(8, 1.1),
			new Diff(9, 1.2)
		};
	}
	
	@Theory
	public void getDiff(Diff diff){
		
		Calendar now = Calendar.getInstance();
		
		Calendar future = Calendar.getInstance();
		future.add(Calendar.HOUR, 1);
		future.add(Calendar.MINUTE, diff.getMinuteOffset());
		
		TimeDifferential timeDiff = new TimeDifferential(now, future);
		BigDecimal result = timeDiff.getDiff();
		
		Assert.assertThat(result, is(diff.getExpectedDiff()));
	}
}

class Diff{
	
	private int minuteOffset;
	private double expectedDiff;

	public Diff(int minuteOffset, double expectedDiff) {
		this.minuteOffset = minuteOffset;
		this.expectedDiff = expectedDiff;
	}

	public int getMinuteOffset() {
		return minuteOffset;
	}
	public BigDecimal getExpectedDiff() {
		return new BigDecimal(expectedDiff).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
}
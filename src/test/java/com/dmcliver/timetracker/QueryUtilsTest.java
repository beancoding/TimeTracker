package com.dmcliver.timetracker;

import org.junit.Assert;
import org.junit.Test;

import static com.dmcliver.timetracker.QueryUtils.PropertyUtil.*;
import static org.hamcrest.CoreMatchers.*;

public class QueryUtilsTest {

	private TestEntity1 teProx1 = from(TestEntity1.class);
	private TestEntity2 teProx2 = from(TestEntity2.class);
	
	@Test
	public void propToStr_WithGetMethod_ReturnsCorrespondingProperty(){
		
		String prop = toStr(teProx1.getCharacter());
		Assert.assertThat(prop, is("character"));
		
		prop = toStr(teProx2.getMyCharacter());
		Assert.assertThat(prop, is("myCharacter"));
		
		prop = toStr(teProx2.isFinished());
		Assert.assertThat(prop, is("finished"));
	}
}

class TestEntity1{
	
	public String getCharacter(){
		
		return "";
	}
}

class TestEntity2{
	
	public String getMyCharacter(){
		
		return "";
	}
	
	public String isFinished(){
		return "True Bro!!!";
	}
}

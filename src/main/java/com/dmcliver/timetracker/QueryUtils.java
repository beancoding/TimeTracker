package com.dmcliver.timetracker;

import java.lang.reflect.Method;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class QueryUtils {

	public static class PropertyUtil{
	
		private static String methName;
		
		@SuppressWarnings("unchecked")
		public static <T> T from(Class<T> classType){
		
			Object proxied= Enhancer.create(classType, new MethodInterceptor(){
	
				@Override
				public Object intercept(Object obj, Method meth, Object[] args, MethodProxy proxy) throws Throwable {
					
					methName = meth.getName();
					return null;
				}			
			});
			
			try{			
				return (T)proxied;
			}
			catch(Exception ex){
				return null;
			}
		}
		
		/**
		 * Converts a property to a string. <br/>
		 * Usage:
		 * {@code String propName = toStr(from(Person.class).getName());} <br/>
		 * The result would be that propName would have the value "name"
		 */
		public static String toStr(Object getMethod){
			
			String potentialUpper = methName.substring(2, 3);
			boolean match = potentialUpper.matches("^[A-Z]{1,1}$");
			char replacement = match ? methName.charAt(2) : methName.charAt(3);
			methName = match ? methName.substring(2) : methName.substring(3);
			
			return methName.replaceFirst("[A-Z]", String.valueOf(replacement).toLowerCase());
		}
	}
}

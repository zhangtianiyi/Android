package com.example.littled.utils;

import java.util.HashMap;

public class VarAll {
	public static  String monthmenu;
	public static String yearmenu;
	public static int nowyear;
	public static int nowmonth;
	public static int nowday;
	public static int nowweek;
	public static String today;
	public static String todaydetail;
	public static int todayofmonth;
	public static  String[] pointinit = {"SUN","MON","TUE","WED","THR","FRI","SAT"};
	public static  String[] monthstring = {"JANUARY","FEBRUARY","MARCH","APRIL","MAY",
			"JUNE","JULY","AUGUST","SEPTEMBER","OCTOBOR","NOVEMBER","DECEMBER"};
	public static  HashMap<String, Integer> monthtoint = new HashMap<String,Integer>();
	public static HashMap<String, String> weekday = new HashMap<String, String>();
	static {
		 	monthtoint.put("JANUARY",new Integer(1));
	    	monthtoint.put("FEBRUARY",new Integer(2));
	    	monthtoint.put("MARCH",new Integer(3));
	    	monthtoint.put("APRIL",new Integer(4));
	    	monthtoint.put("MAY",new Integer(5));
	    	monthtoint.put("JUNE",new Integer(6));
	    	monthtoint.put("JULY",new Integer(7));
	    	monthtoint.put("AUGUST",new Integer(8));
	    	monthtoint.put("SEPTEMBER",new Integer(9));
	    	monthtoint.put("OCTOBER",new Integer(10));
	    	monthtoint.put("NOVEMBER",new Integer(11));
	    	monthtoint.put("DECEMBER",new Integer(12));
	    	
	    	weekday.put("MON","MONDAY");
	    	weekday.put("TUE", "TUESDAY");
	    	weekday.put("WED", "WEDNESDAY");
	    	weekday.put("THR", "THRUTHDAY");
	    	weekday.put("FRI", "FRIDAY");
	    	weekday.put("SAT", "SATURDAY");
	    	weekday.put("SUN", "SUNDAY");
	}
}

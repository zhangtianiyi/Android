package com.example.littled.domain;

import java.io.Serializable;

public class Data implements Serializable{
	private String day;
	private String week;
	private static final long serialVersionUID = -3450064362986273896L; 

	public Data(String day,String week)
	{
		this.day = day;
		this.week = week;
	}

	public String getDay()
	{
		return day;
	}
	
	public String getWeek()
	{
		return week;
	}
	
	public void setDay(String day)
	{
		this.day = day;
	}
	
	public void setWeek(String week)
	{
		this.week = week;
	}
}

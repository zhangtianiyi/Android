package com.example.littled.domain;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


public class Day extends Data implements Serializable{
	private String day;
	private String week;
	private String detail;
	

	public Day(String week,String day,String detail)
	{
		super(day,week);
		this.detail = detail;
	}
	
	public String getDetail()
	{
		return detail;
	}
	
	public void setDetail(String detail)
	{
		this.detail = detail;
	}
}
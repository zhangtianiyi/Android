package com.example.littled.domain;

import java.io.Serializable;


public class Point extends Data implements Serializable{
	private int imageId;
	private String day;
	private String week;

	public Point(int imageId, String week,String day)
	{
		super(day,week);
		this.imageId = imageId;
	}
		/*
	public Point(int imageId)
	{
		this.imageId = imageId;
	}*/
	public int getImageId()
	{
		return imageId;
	}
	
	public void setImageId(int ImageId)
	{
		this.imageId =ImageId;
	}
}

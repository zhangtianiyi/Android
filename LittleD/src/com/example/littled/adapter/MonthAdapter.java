package com.example.littled.adapter;

import java.util.List;

import com.example.littled.R;
import com.example.littled.R.id;
import com.example.littled.domain.Data;
import com.example.littled.domain.Day;
import com.example.littled.domain.Point;
import com.example.littled.utils.VarAll;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MonthAdapter extends ArrayAdapter{
	private int resourceId;
	public MonthAdapter(Context context,int textViewResourceId,List<Data> objects)
	{
		super(context,textViewResourceId,objects);
		resourceId = textViewResourceId;
	}
	
	@Override
	public View getView(int position,View convertView,ViewGroup parent)
	{
		View view = LayoutInflater.from(getContext()).inflate(resourceId,null);
		TextView monthdetail = (TextView)view.findViewById(R.id.detailitem);
		TextView monthweek = (TextView)view.findViewById(R.id.weekitem);
		TextView monthday = (TextView)view.findViewById(R.id.dayitem);
		if (getItem(position)instanceof Day){
			Day day = (Day) getItem(position);
			 String year = "" + VarAll.nowyear;
             String month = VarAll.monthstring[VarAll.nowmonth-1];
             int ag = Integer.parseInt(day.getDay());
             String sag = "" + ag;
             String san = "" + VarAll.nowday;
			if(day.getWeek().equals("SUN"))
			{
				monthweek.setTextColor(Color.rgb(255, 0, 0));
			}
			else if(VarAll.yearmenu.equals(year) && VarAll.monthmenu.equals(month)){
            	if(sag.trim().equals(san)){
            		monthweek.setTextColor(Color.rgb(249, 204, 226));
            	}
			}
			monthdetail.setText(day.getDetail());
			monthweek.setText(day.getWeek());
			monthday.setText(day.getDay());
		}
		else{
			Point point = (Point)getItem(position);
			if(point.getWeek().equals("SUN"))
			{
				monthweek.setTextColor(Color.rgb(255, 0, 0));
			}
			monthdetail.setText(null);
			monthweek.setText(point.getWeek());
			monthday.setText(point.getDay());
		}
			return view;
	}
}

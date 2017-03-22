package com.example.littled;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import com.example.littled.R;
import com.example.littled.R.id;
import com.example.littled.R.layout;
import com.example.littled.adapter.DayApater;
import com.example.littled.domain.Data;
import com.example.littled.domain.Day;
import com.example.littled.domain.Point;
import com.example.littled.utils.ListAll;
import com.example.littled.utils.VarAll;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface.OnClickListener;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ShowActivity extends Activity {
	
	private EditText tv5;
	private TextView tv1;
	private ArrayList<Data> data;
	private String w;
	private String da;
	private String m;
	private String y;
	private String d;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_show);
		Intent intent = getIntent();
		
		w = intent.getStringExtra("week");
		tv1 = (TextView)findViewById(R.id.show_week);
		handle();
		tv1.setText(w);
		
		m = intent.getStringExtra("month");
		da = intent.getStringExtra("day");
		y = intent.getStringExtra("year");
		TextView tv2 = (TextView)findViewById(R.id.show_month);
		TextView tv3 = (TextView)findViewById(R.id.show_day);
		TextView tv4 = (TextView)findViewById(R.id.show_year);
		String s = y+VarAll.monthtoint.get(m)+da;
		if(VarAll.today.equals(s)){          //如果是当天
			tv2.setTextColor(Color.rgb(249, 204, 226));
			tv3.setTextColor(Color.rgb(249, 204, 226));
			tv4.setTextColor(Color.rgb(249, 204, 226));
		}
		tv2.setText(m);
		tv3.setText(da);
		tv4.setText(y);
		
		d = intent.getStringExtra("detail");		
		tv5=(EditText)findViewById(R.id.text1); 
		tv5.setText(d);	
		
		data = new ArrayList<Data>();
		data = load("" + Integer.parseInt(y) + VarAll.monthtoint.get(m));  //要改数据,先取出数据所在列表
		
		
		
		Button btn1 =(Button)findViewById(R.id.backmain);
		btn1.setOnClickListener(new View.OnClickListener()
        {
        	@Override
        	public void onClick(View v)
        	{
        		String inputText = tv5.getText().toString();
        		for(int i=0;i<data.size();i++)
       		 	{
        			if(data.get(i).getDay().equals(da))
      				 {
        				((Day)data.get(i)).setDetail(inputText);               //show的一定是day
      				 }
       		 	}
        		save(data, "" + Integer.parseInt(y) + VarAll.monthtoint.get(m));
				ListAll.temp = data;
        		finish();
        	}
        });
		Button btn2 =(Button)findViewById(R.id.clearitem);
		btn2.setOnClickListener(new View.OnClickListener()
	    {
	    	@Override
	    	public void onClick(View v)
	    	{
	    		tv5.setText(null);	
	    	}
	    });
		
	}
	
	private void handle()
	{
		if(w.equals("MON"))
		{
			w = "MONDAY";
			tv1.setText(w);
		}
		else if(w.equals("TUE"))
		{
			w = "TUESDAY";
			tv1.setText(w);
		}
		else if(w.equals("WED"))
		{
			w = "WEDNESDAY";
			tv1.setText(w);
		}
		else if(w.equals("THR"))
		{
			w = "THRUTHDAY";
			tv1.setText(w);
		}
		else if(w.equals("FRI"))
		{
			w = "FRIDAY";
			tv1.setText(w);
		}
		else if(w.equals("SAT"))
		{
			w = "SATURDAY";
			tv1.setText(w);
		}
		else if(w.equals("SUN"))
		{
			w = "SUNDAY";
			tv1.setTextColor(Color.rgb(255, 0, 0));
			tv1.setText(w);
		}
	}
	
	public void save(ArrayList list, String filename) { //存列表
		FileOutputStream out = null;
		try {
			out = openFileOutput(filename, Context.MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(out);
			oos.writeObject(list);

			oos.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList load(String filename) // 取列表
	{
		ArrayList list = new ArrayList();
		FileInputStream in = null;
		try {
			in = openFileInput(filename);
			ObjectInputStream ois = new ObjectInputStream(in);
			list = (ArrayList) ois.readObject();
			ois.close();
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return list;
	}
}

package com.example.littled;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.example.littled.R;
import com.example.littled.R.id;
import com.example.littled.R.layout;
import com.example.littled.domain.Data;
import com.example.littled.domain.Day;
import com.example.littled.domain.Point;
import com.example.littled.utils.ListAll;
import com.example.littled.utils.VarAll;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class EditActivity extends Activity {
	private EditText edit;
	private String da;
	private String w;
	private String w2;
	private String date;
	private String m;
	private String y;
	private ArrayList<Data> data;
	boolean istoday = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_edit);

		edit = (EditText) findViewById(R.id.edit_text);
		Intent intent = getIntent();

		ImageView tvv = (ImageView) findViewById(R.id.idclock);// 点击最左下方的时间
		tvv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int index = edit.getSelectionStart();
				Editable editable = edit.getText();
				Calendar cal = Calendar.getInstance();
				int hour = cal.get(Calendar.HOUR_OF_DAY);// 小时
				int minute = cal.get(Calendar.MINUTE);// 分
				if (hour >= 0 && hour <= 12)
					date = "am";
				else
					date = "pm";
				editable.insert(index, hour + ":" + minute + " " + date);
			}
		});

		w = intent.getStringExtra("week2");
		TextView tv1 = (TextView) findViewById(R.id.show_week2);

		w2 = VarAll.weekday.get(w);
		if(w2.equals("SUNDAY"))
			tv1.setTextColor(Color.rgb(255, 0, 0));

		tv1.setText(w2);

		m = intent.getStringExtra("month2");
		da = intent.getStringExtra("day2");
		y = intent.getStringExtra("year2");
		TextView tv2 = (TextView) findViewById(R.id.show_month2);
		TextView tv3 = (TextView) findViewById(R.id.show_day2);
		TextView tv4 = (TextView) findViewById(R.id.show_year2);
		
		String s = y+VarAll.monthtoint.get(m)+da;
		if(VarAll.today.equals(s)){          //如果是当天
			tv2.setTextColor(Color.rgb(249, 204, 226));
			tv3.setTextColor(Color.rgb(249, 204, 226));
			tv4.setTextColor(Color.rgb(249, 204, 226));
			istoday = true;
//			if(VarAll.todaydetail != null)
//				edit.setText(VarAll.todaydetail.toCharArray(),0,VarAll.todaydetail.length());
		}
		tv2.setText(m);
		tv3.setText(da);
		tv4.setText(y);

		data = new ArrayList<Data>();
		data = load("" + Integer.parseInt(y) + VarAll.monthtoint.get(m));

		if(istoday && data.get(VarAll.todayofmonth-1) instanceof Day){
			Day d = (Day)data.get(VarAll.todayofmonth-1);
			//data.set(VarAll.todayofmonth-1, d);
			edit.setText(d.getDetail());
		}
		
		ImageView iv = (ImageView) findViewById(R.id.iddone);
		iv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String inputText = edit.getText().toString();
				if(istoday){
					VarAll.todaydetail = inputText;	
					VarAll.yearmenu = "" + VarAll.nowyear;
					VarAll.monthmenu = VarAll.monthstring[VarAll.nowmonth-1]; 
				}
				for (int i = 0; i < data.size(); i++) {
					 if (data.get(i) instanceof Point) {
						Point point = (Point) data.get(i);
						if (point.getDay().equals(da)) {
							Day day = new Day(w, da, inputText);
							data.set(i, day);
						}
					}
					 else if(data.get(i) instanceof Day){
						 Day day = (Day) data.get(i);
							if (day.getDay().equals(da)) {
								Day tday = new Day(w, da, inputText);
								data.set(i, tday);
							}
					 }
				}

				save(data, "" + Integer.parseInt(y) + VarAll.monthtoint.get(m));
				ListAll.temp = data;
				finish();
			}
		});
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

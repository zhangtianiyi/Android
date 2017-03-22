package com.example.littled;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.example.littled.R;
import com.example.littled.R.id;
import com.example.littled.R.layout;
import com.example.littled.adapter.DayApater;
import com.example.littled.adapter.MonthAdapter;
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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

public class MonthActivity extends Activity {
	
	private ArrayList<Data> monthlist = new ArrayList<Data>();
	private TextView tvmonth;
	private TextView tvyear;
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_month);
        
        final LinearLayout monthLayout = (LinearLayout)findViewById(R.id.monthlinear); //14......
        final LinearLayout bottomLayout = (LinearLayout)findViewById(R.id.bottom_);
        final LinearLayout chooseLayout = (LinearLayout)findViewById(R.id.bottom_choosemonth);
        final View mview = View.inflate(this,R.layout.choosemonth,null);
        tvmonth = (TextView)findViewById(R.id.text_view1_month);
        tvyear = (TextView)findViewById(R.id.text_view2_year);
        
        final Button btn1 = new Button(this);
        btn1.setText("1");
        btn1.setText("Button1");
        
        ImageView ivadd = (ImageView)findViewById(R.id.imge_view6_);  //点击加号
        ivadd.setOnClickListener(new View.OnClickListener()    //启动当日日记编辑活动
        {
        	@Override
        	public void onClick(View v)
        	{
	             String sweek = VarAll.pointinit[VarAll.nowweek-1];
	             String mm = VarAll.monthstring[VarAll.nowmonth-1];
	             Intent intent = new Intent(MonthActivity.this, EditActivity.class);
	             intent.putExtra("week2",sweek);
	             intent.putExtra("day2", "" + VarAll.nowday);
	             intent.putExtra("add","add"); 
	             intent.putExtra("year2","" + VarAll.nowyear);
	             intent.putExtra("month2", "" + mm);
	             startActivity(intent);
        	}
        });
        
        tvmonth.setOnClickListener(new View.OnClickListener() // 点击下栏月份，调用月份点击处理事件
		{
			@Override
			public void onClick(View v) {
				showPopupMenu1(tvmonth);
			}
		});

		tvyear.setOnClickListener(new View.OnClickListener() // 点击下栏年份，调用年份点击处理事件
		{
			@Override
			public void onClick(View v) {
				showPopupMenu2(tvyear);
			}
		}); 
        
       //下栏年份，月份信息的取得
        Intent intent = getIntent();
        String m = intent.getStringExtra("month3");
		TextView tv1_ = (TextView)findViewById(R.id.text_view1_month);
		tv1_.setText(m);

		String y = intent.getStringExtra("year3");		
		TextView tv2_ = (TextView)findViewById(R.id.text_view2_year);
		tv2_.setText(y);
        
        //以下是读取并设置listview各个item的数据
		int m2 = VarAll.monthtoint.get(m);
        monthlist = load(y+m2);
        //initDay();
        MonthAdapter adapter = new MonthAdapter(MonthActivity.this,R.layout.month_item,monthlist);
        ListView listView = (ListView)findViewById(R.id.list_view_);
        listView.setAdapter(adapter);
        
        ImageView iv =(ImageView)findViewById(R.id.image_view8_);
        iv.setOnClickListener(new View.OnClickListener()
        {
        	@Override
        	public void onClick(View v)
        	{
        		ListAll.temp = monthlist;
        		finish();
        	}
        });
    }
	
	private void showPopupMenu1(View view) { // 月份菜单点击
		PopupMenu popupMenu = new PopupMenu(this, view);
		popupMenu.getMenuInflater().inflate(R.menu.text1, popupMenu.getMenu());
		popupMenu
				.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
					@Override
					public boolean onMenuItemClick(MenuItem item) {
						VarAll.monthmenu = (String) item.getTitle();
						tvmonth.setText(VarAll.monthmenu);
						
						String filename = "/data/data/com.example.littled/files/" + Integer.parseInt(VarAll.yearmenu)
								+ VarAll.monthtoint.get(VarAll.monthmenu);
						File f = new File(filename);
						ArrayList<Data> data = new ArrayList<Data>();
						if (f.exists()) { // 如果文件存在，下载list，并显示
							data = load("" + Integer.parseInt(VarAll.yearmenu) + VarAll.monthtoint.get(VarAll.monthmenu));
							ListAll.temp = data;           //为了返回的时候显示相应的年月的
							monthlist = data;
							MonthAdapter adapter = new MonthAdapter(MonthActivity.this,R.layout.month_item,data);
						    ListView listView = (ListView)findViewById(R.id.list_view_);
						    listView.setAdapter(adapter);
						} 
						else if (!f.exists()) { // 如果文件不存在initby
							initAndShowDateByYearMonth(Integer.parseInt(VarAll.yearmenu),VarAll.monthtoint.get(VarAll.monthmenu));
						}
						return false;
					}
				});
		popupMenu.show();
	}
	
	private void initAndShowDateByYearMonth(int year, int month) // 初始化并且显示
	{
		ArrayList<Data> data = new ArrayList<Data>();
		initDate(year, month, data);
		ListAll.temp = data;           //为了返回的时候显示相应的年月的
		MonthAdapter adapter = new MonthAdapter(MonthActivity.this,R.layout.month_item,data);
	    ListView listView = (ListView)findViewById(R.id.list_view_);
	    listView.setAdapter(adapter);
	}

	private void initNowDate(int year, int month, int day, ArrayList<Data> data){
		System.out.println("initby:" + year + month);
		int maxday; // 指定月份的天数
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month -1 );
		cal.set(Calendar.DATE, 1);
		cal.roll(Calendar.DATE, -1);
		maxday = cal.get(Calendar.DATE); // 获取指定月份的天数
		cal.set(Calendar.DAY_OF_MONTH, 1);// 从一号开始

		for (int i = 0; i < maxday; i++, cal.add(Calendar.DATE, 1)) {
			int dayofmonth = cal.get(Calendar.DAY_OF_MONTH);
			int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
			Point point;
			if(dayofmonth == day){
				System.out.println("diaoyong"+day);
				point = new Point(R.drawable.pinkpoint, VarAll.pointinit[dayofweek - 1], ""+dayofmonth);
			}
			else if(dayofweek == 1){
				point = new Point(R.drawable.redpoint, VarAll.pointinit[dayofweek - 1], ""+dayofmonth);
			}
			else{
				point = new Point(R.drawable.blackpoint, VarAll.pointinit[dayofweek - 1], ""+dayofmonth);
			}
			data.add(point);
		}
	}
	
	private void initDate(int year, int month, ArrayList<Data> data) {
		int maxday; // 指定月份的天数
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month -1 );
		cal.set(Calendar.DATE, 1);
		cal.roll(Calendar.DATE, -1);
		maxday = cal.get(Calendar.DATE); // 获取指定月份的天数
		cal.set(Calendar.DAY_OF_MONTH, 1);// 从一号开始

		for (int i = 0; i < maxday; i++, cal.add(Calendar.DATE, 1)) {
			int dayofmonth = cal.get(Calendar.DAY_OF_MONTH);
			int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
			Point point;
			if(dayofweek == 1)
				point = new Point(R.drawable.redpoint, VarAll.pointinit[dayofweek - 1], ""+dayofmonth);
			else{
				point = new Point(R.drawable.blackpoint, VarAll.pointinit[dayofweek - 1], ""+dayofmonth);
			}
			data.add(point);
		}
	}

	private void showPopupMenu2(View view) { // 年份菜单点击
		PopupMenu popupMenu = new PopupMenu(this, view);
		popupMenu.getMenuInflater().inflate(R.menu.text2, popupMenu.getMenu());
		popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
					@Override
					public boolean onMenuItemClick(MenuItem item) {
						VarAll.yearmenu = (String) item.getTitle();
						tvyear.setText(VarAll.yearmenu);
						String filename = "/data/data/com.example.littled/files/"+ Integer.parseInt(VarAll.yearmenu)
								+ VarAll.monthtoint.get(VarAll.monthmenu);
						File f = new File(filename);
						ArrayList<Data> data = new ArrayList<Data>();
						if (f.exists()) {
							data = load("" + Integer.parseInt(VarAll.yearmenu)+ VarAll.monthtoint.get(VarAll.monthmenu));
							ListAll.temp = data;
							monthlist = data;
							 MonthAdapter adapter = new MonthAdapter(MonthActivity.this,R.layout.month_item,data);
						     ListView listView = (ListView)findViewById(R.id.list_view_);
						     listView.setAdapter(adapter);
						} 
//						else if (!f.exists()) {
//							System.out.println("文件bu存在"
//									+ Integer.parseInt(VarAll.yearmenu)
//									+ VarAll.monthtoint.get(VarAll.monthmenu));
//							initAndShowDateByYearMonth(Integer.parseInt(VarAll.yearmenu),
//									VarAll.monthtoint.get(VarAll.monthmenu));
//						}
						return false;
					}
				});
		popupMenu.show();
	}
	
	public void save(ArrayList list, String filename)// 存数据
	{
		FileOutputStream out = null;
		try {
			//System.out.println("f_save:" + filename);
			out = openFileOutput(filename, Context.MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(out);
			oos.writeObject(list);

			oos.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList load(String filename) // 取数据
	{
		ArrayList list = new ArrayList();
		FileInputStream in = null;
		try {
			//System.out.println("f_load:" + filename);
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

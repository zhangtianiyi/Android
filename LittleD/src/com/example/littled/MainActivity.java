package com.example.littled;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.example.littled.R;
import com.example.littled.R.drawable;
import com.example.littled.R.id;
import com.example.littled.R.layout;
import com.example.littled.R.menu;
import com.example.littled.adapter.DayApater;
import com.example.littled.domain.Data;
import com.example.littled.domain.Day;
import com.example.littled.domain.Point;
import com.example.littled.utils.ListAll;
import com.example.littled.utils.VarAll;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {

	static int flag = 0;
	private TextView tv1;
	private TextView tv2;
	private ImageView iv1;
	private ListView listView;
	private View layout;
	private AlertDialog ad;
	private ArrayList<Data> longclick;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		Calendar now = Calendar.getInstance();
		VarAll.nowyear = now.get(Calendar.YEAR);     //ȡ�õ�ǰ����
		VarAll.nowmonth = now.get(Calendar.MONTH)+1;
		VarAll.nowday = now.get(Calendar.DAY_OF_MONTH);
		VarAll.nowweek = now.get(Calendar.DAY_OF_WEEK);
		   
		VarAll.today = ""+VarAll.nowyear+VarAll.nowmonth+VarAll.nowday;
		VarAll.todayofmonth = now.get(Calendar.DAY_OF_MONTH);
		VarAll.yearmenu = "" + VarAll.nowyear;
		VarAll.monthmenu = VarAll.monthstring[VarAll.nowmonth-1]; 

		if (flag == 0) { //ֻ�ڻ������ʱ��ִ��һ��
			initDays();  // ��ʼ������list���������ݵ�20158�ļ�
			flag++;
		}
		String filename = "/data/data/com.example.littled/files/" + VarAll.nowyear + VarAll.nowmonth;
		File f = new File(filename);
		ArrayList<Data> list = new ArrayList<Data>();
		System.out.println(""+VarAll.nowyear+VarAll.nowmonth);
		System.out.println(VarAll.yearmenu + VarAll.monthmenu);
		if (f.exists()) {      //�ļ����ڣ������ز���ʾ
			list = load("" + VarAll.nowyear + VarAll.nowmonth);
		} else if (!f.exists()) {
			//initDate(nowyear, nowmonth,list);  // �����ļ�201611�������list������ʾ
			initNowDate(VarAll.nowyear, VarAll.nowmonth, VarAll.nowday, list);
		}
		System.out.println("nowday :"+VarAll.nowday);
		list = load("" + VarAll.nowyear + VarAll.nowmonth);
		ListAll.temp = list;                    // Ϊ�˴���ʱ��ĳ�ʼ��
		DayApater adapter = new DayApater(MainActivity.this, list);
		ListView listView = (ListView) findViewById(R.id.list_view);
		listView.setAdapter(adapter);

		tv1 = (TextView) findViewById(R.id.text_view1); // ��ʼ������̬�趨��
		tv2 = (TextView) findViewById(R.id.text_view2);
		tv1.setText(VarAll.monthmenu);
		tv2.setText(VarAll.yearmenu);

		tv1.setOnClickListener(new View.OnClickListener() // ��������·ݣ������·ݵ�������¼�
		{
			@Override
			public void onClick(View v) {
				showPopupMenu1(tv1);
			}
		});

		tv2.setOnClickListener(new View.OnClickListener() // ���������ݣ�������ݵ�������¼�
		{
			@Override
			public void onClick(View v) {
				showPopupMenu2(tv2);
			}
		});

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String filename = "/data/data/com.example.littled/files/"
						+ Integer.parseInt(VarAll.yearmenu)
						+ VarAll.monthtoint.get(VarAll.monthmenu);
				File f = new File(filename);
				ArrayList<Data> data = new ArrayList<Data>();
				if (f.exists()) { // ��ʵһ���Ǵ��ڵ�
					data = load("" + Integer.parseInt(VarAll.yearmenu) + VarAll.monthtoint.get(VarAll.monthmenu));
				}
				Data ob = data.get(position);

				if (ob instanceof Day) // ����Ѿ��༭�õģ������������
				{
					Day d = (Day) ob;
					String sdetail = d.getDetail();
					String sday = d.getDay();
					String sweek = d.getWeek();
					Intent intent = new Intent(MainActivity.this,
							ShowActivity.class);
					intent.putExtra("detail", sdetail);
					intent.putExtra("week", sweek);
					intent.putExtra("day", sday);
					intent.putExtra("year", VarAll.yearmenu);
					intent.putExtra("month", VarAll.monthmenu);
					startActivity(intent);
				} else if (ob instanceof Point) // ���СԲ�㣬����༭����
				{
					Point p = (Point) ob;
					String sday = p.getDay();
					String sweek = p.getWeek();
					Intent intent = new Intent(MainActivity.this,
							EditActivity.class);
					intent.putExtra("week2", sweek);
					intent.putExtra("day2", sday);
					intent.putExtra("year2", VarAll.yearmenu);
					intent.putExtra("month2", VarAll.monthmenu);
					startActivity(intent);
				}

			}
		});
		
		
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				String filename = "/data/data/com.example.littled/files/" + Integer.parseInt(VarAll.yearmenu) + VarAll.monthtoint.get(VarAll.monthmenu);
				File f = new File(filename);
				longclick = new ArrayList();
				final int pos = position;
				if (f.exists()) { // ��ʵһ���Ǵ��ڵ�
					longclick = load("" + Integer.parseInt(VarAll.yearmenu) + VarAll.monthtoint.get(VarAll.monthmenu));
				}
				final Data ob = longclick.get(position);
				if (ob instanceof Day) // day -> point
				{
					 LayoutInflater inflater = getLayoutInflater();
		    		 layout = inflater.inflate(R.layout.dialog_delete,null);
		    		 ad = new AlertDialog.Builder(MainActivity.this).setView(layout).show();
		    		   //�������
		    		 Button button_ok = (Button)layout.findViewById(R.id.dialog_ok);
		    		 button_ok.setOnClickListener(new OnClickListener()
		    		   {
		    		   	   @Override
		    		   	   public void onClick(View v)
		    		   	   {
		    		   		Day d = (Day) ob;
							Point point = new Point(R.drawable.blackpoint,d.getWeek(), d.getDay());
							longclick.set(pos, point);
							save(longclick,""+ Integer.parseInt(VarAll.yearmenu)+ VarAll.monthtoint.get(VarAll.monthmenu));
							ListAll.temp = longclick;
							DayApater adapter = new DayApater(MainActivity.this, longclick);
							ListView listView = (ListView) findViewById(R.id.list_view);
							listView.setAdapter(adapter);
		    		    	ad.dismiss(); 
		    		       }
		    		   });
		    		  
		    		   //������
		    		  Button button_no = (Button)layout.findViewById(R.id.dialog_close);
		    		  button_no.setOnClickListener(new OnClickListener()
		    		   {
		    		   	   @Override
		    		   	   public void onClick(View v)
		    		   	   {
		    		   		   ad.dismiss();  
		    		       }
		    		   });
				}
				return true;
			}
			
		});
		
		
		 ImageView ivadd = (ImageView)findViewById(R.id.image_view6);
	        ivadd.setOnClickListener(new View.OnClickListener()    //���������ռǱ༭�
	        {
	        	@Override
	        	public void onClick(View v)
	        	{
		             String sweek = VarAll.pointinit[VarAll.nowweek-1];
		             String mm = VarAll.monthstring[VarAll.nowmonth-1];
		             Intent intent = new Intent(MainActivity.this, EditActivity.class);
		             intent.putExtra("week2",sweek);
		             intent.putExtra("day2", "" + VarAll.nowday);
		             intent.putExtra("add","add"); 
		             intent.putExtra("year2","" + VarAll.nowyear);
		             intent.putExtra("month2", "" + mm);
		             startActivity(intent);
	        	}
	        });
	        
	        iv1 = (ImageView)findViewById(R.id.image_view8);
				 iv1.setOnClickListener(new View.OnClickListener()    //���������ռ�����Ļ
			        {
			        	@Override
			        	public void onClick(View v)
			        	{
			        		Intent intent = new Intent(MainActivity.this,MonthActivity.class);
			                intent.putExtra("year3",VarAll.yearmenu);
			                intent.putExtra("month3", VarAll.monthmenu);
			                save(ListAll.temp, VarAll.yearmenu + VarAll.monthmenu); 
			                startActivity(intent);
			        	}
			        });
	}
	
	private void initAndShowDateByYearMonth(int year, int month) // ��ʼ��������ʾ
	{
		ArrayList<Data> data = new ArrayList<Data>();
		initDate(year, month, data);
		DayApater adapter = new DayApater(MainActivity.this, data);
		ListView listView = (ListView) findViewById(R.id.list_view);
		listView.setAdapter(adapter);
	}

	private void initNowDate(int year, int month, int day, ArrayList<Data> data){
		System.out.println("initby:" + year + month);
		int maxday; // ָ���·ݵ�����
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month -1 );
		cal.set(Calendar.DATE, 1);
		cal.roll(Calendar.DATE, -1);
		maxday = cal.get(Calendar.DATE); // ��ȡָ���·ݵ�����
		cal.set(Calendar.DAY_OF_MONTH, 1);// ��һ�ſ�ʼ

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
		String arg = "" + year + month; // �찡����Ҫ year+month+""
		save(data, arg); // ÿ��һ��list ������ʼ������ʮ��������ļ�
		String filename = "/data/data/com.example.littled/files/"
				+ Integer.parseInt(VarAll.yearmenu) + VarAll.monthtoint.get(VarAll.monthmenu);
		File f = new File(filename);
	}
	
	private void initDate(int year, int month, ArrayList<Data> data) {
		System.out.println("initby:" + year + month);
		int maxday; // ָ���·ݵ�����
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month -1 );
		cal.set(Calendar.DATE, 1);
		cal.roll(Calendar.DATE, -1);
		maxday = cal.get(Calendar.DATE); // ��ȡָ���·ݵ�����
		cal.set(Calendar.DAY_OF_MONTH, 1);// ��һ�ſ�ʼ

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
		String arg = "" + year + month; // �찡����Ҫ year+month+""
		save(data, arg); // ÿ��һ��list ������ʼ������ʮ��������ļ�
		String filename = "/data/data/com.example.littled/files/"
				+ Integer.parseInt(VarAll.yearmenu) + VarAll.monthtoint.get(VarAll.monthmenu);
		File f = new File(filename);
	}

	// ���� ��� �·�
	// ��ȡ�� ���ڼ� �ڼ��ţ�һ�����µ��������ڣ�
	// ѭ�� ��ѭ�������ɱ����Ľ����ͳ�ʼ����

	private void initDays() {
		// initDateByYearMonth(2015,9);
		ArrayList<Data> list = new ArrayList<Data>();
		Day day1 = new Day("SAT", "1", "��ȥ��������...");
		list.add(day1);
		Day d1 = new Day("Saturday", "1", "��ȥ��������...");
		ListAll.daylist.add(d1);
		Day day2 = new Day("SUN", "2", "�����һ�κ��Ƕ���������档");
		list.add(day2);
		Day d2 = new Day("Sunday", "2", "�����һ�κ��Ƕ���������档");
		ListAll.daylist.add(d2);
		Day day3 = new Day("MON", "3", "·���ߵ�������������β��");
		list.add(day3);
		Day d3 = new Day("Monday", "3", "·���ߵ�������������β��");
		ListAll.daylist.add(d3);
		Day day4 = new Day("TUE", "4", "����ÿ�춼�ܳԵ����ŷ��ˣ��ÿ��İ�");
		list.add(day4);
		Day d4 = new Day("Tuesday", "4", "����ÿ�춼�ܳԵ����ŷ��ˣ��ÿ��İ�");
		ListAll.daylist.add(d4);
		Point point = new Point(R.drawable.blackpoint, "WED", "5");
		list.add(point);
		Point point2 = new Point(R.drawable.blackpoint, "THR", "6");
		list.add(point2);
		Day day5 = new Day("FRI", "7", "�㣬�ڿ��ȣ�֥ʿ��⣬��ë�򣬳ɹ�");
		list.add(day5);
		Day d5 = new Day("Friday", "7", "�㣬�ڿ��ȣ�֥ʿ��⣬��ë�򣬳ɹ�");
		ListAll.daylist.add(d5);
		Day day6 = new Day("SAT", "8", "DGA 7 �");
		list.add(day6);
		Day d6 = new Day("Saturday", "8", "DGA 7 �");
		ListAll.daylist.add(d6);
		save(list, "20158");
	}

	// �����˵�
	private void showPopupMenu1(View view) { // �·ݲ˵����
		PopupMenu popupMenu = new PopupMenu(this, view);
		popupMenu.getMenuInflater().inflate(R.menu.text1, popupMenu.getMenu());
		popupMenu
				.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
					@Override
					public boolean onMenuItemClick(MenuItem item) {
						System.out.println("month:" + VarAll.monthmenu);
						VarAll.monthmenu = (String) item.getTitle();
						System.out.println("month:" + VarAll.monthmenu);
						tv1.setText(VarAll.monthmenu);
						String filename = "/data/data/com.example.littled/files/"
								+ Integer.parseInt(VarAll.yearmenu)
								+ VarAll.monthtoint.get(VarAll.monthmenu);
						// File f=new
						// File("/data/data/com.example.littled/files/20158");
						File f = new File(filename);
						ArrayList<Data> data = new ArrayList<Data>();
						if (f.exists()) { // ����ļ����ڣ�����list������ʾ
//							System.out.println("�ļ�����"
//									+ Integer.parseInt(yearmenu)
//									+ VarAll.monthtoint.get(monthmenu));
							data = load("" + Integer.parseInt(VarAll.yearmenu)
									+ VarAll.monthtoint.get(VarAll.monthmenu));
							DayApater adapter = new DayApater(MainActivity.this, data);
							ListView listView = (ListView) findViewById(R.id.list_view);
							listView.setAdapter(adapter);
						} else if (!f.exists()) { // ����ļ�������initby
							System.out.println("�ļ�bu����"
									+ Integer.parseInt(VarAll.yearmenu)
									+ VarAll.monthtoint.get(VarAll.monthmenu));
							initAndShowDateByYearMonth(Integer.parseInt(VarAll.yearmenu),
									VarAll.monthtoint.get(VarAll.monthmenu));
						}
						return false;
					}
				});
		popupMenu.show();
	}

	private void showPopupMenu2(View view) { // ��ݲ˵����
		PopupMenu popupMenu = new PopupMenu(this, view);
		popupMenu.getMenuInflater().inflate(R.menu.text2, popupMenu.getMenu());
		popupMenu
				.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
					@Override
					public boolean onMenuItemClick(MenuItem item) {
						System.out.println("year:" + VarAll.yearmenu);
						VarAll.yearmenu = (String) item.getTitle();
						tv2.setText(VarAll.yearmenu);
						String filename = "/data/data/com.example.littled/files/"
								+ Integer.parseInt(VarAll.yearmenu)
								+ VarAll.monthtoint.get(VarAll.monthmenu);

						File f = new File(filename);
						ArrayList<Data> data = new ArrayList<Data>();
						if (f.exists()) {
//							System.out.println("�ļ�����"
//									+ Integer.parseInt(yearmenu)
//									+ VarAll.monthtoint.get(monthmenu));
							data = load("" + Integer.parseInt(VarAll.yearmenu)
									+ VarAll.monthtoint.get(VarAll.monthmenu));
							ListAll.temp = data;
							DayApater adapter = new DayApater(
									MainActivity.this, ListAll.temp);
							ListView listView = (ListView) findViewById(R.id.list_view);
							listView.setAdapter(adapter);
						} else if (!f.exists()) {
							System.out.println("�ļ�bu����"
									+ Integer.parseInt(VarAll.yearmenu)
									+ VarAll.monthtoint.get(VarAll.monthmenu));
							initAndShowDateByYearMonth(Integer.parseInt(VarAll.yearmenu),
									VarAll.monthtoint.get(VarAll.monthmenu));
						}
						return false;
					}
				});
		popupMenu.show();
	}

	public void save(ArrayList list, String filename)// ������
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

	public ArrayList load(String filename) // ȡ����
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

	protected void onStart() {
//		System.out.println("start" + monthmenu);
//		System.out.println(ListAll.temp.size());
		super.onStart();
		
		 String year = "" + VarAll.nowyear;
         String month = VarAll.monthstring[VarAll.nowmonth-1];
         
         if(ListAll.temp.get(VarAll.nowday) instanceof Point){
        	 Point point = (Point) ListAll.temp.get(VarAll.nowday);
        	 
         }
    
		if(VarAll.yearmenu.equals(year) && VarAll.monthmenu.equals(month)){
			System.out.println("%%");
			if(ListAll.temp.get(VarAll.nowday-1) instanceof Point){
				System.out.println("%%%%");
	        	Point point = (Point) ListAll.temp.get(VarAll.nowday-1);
				int ag = Integer.parseInt(point.getDay())-1;
		         String sag = "" + ag;
		         String san = "" + VarAll.nowday;
		         String sann = "" + (Integer.parseInt(san)-1);
		         System.out.println(sann);    //1
		         System.out.println(sag);    //2
				if(sag.trim().equals(sann)){
					System.out.println("%%%%%%");
					Calendar cal = Calendar.getInstance();
					int dayofmonth = cal.get(Calendar.DAY_OF_MONTH);
					int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
					Point newpoint= new Point(R.drawable.pinkpoint, VarAll.pointinit[dayofweek - 1], ""+dayofmonth);
					ListAll.temp.set(VarAll.nowday-1, newpoint);
				}
			}
		}
//		tv1.setText(VarAll.monthmenu);
//		tv2.setText(VarAll.yearmenu);
		
		
		DayApater adapter = new DayApater(MainActivity.this, ListAll.temp);
		ListView listView = (ListView) findViewById(R.id.list_view);
		listView.setAdapter(adapter);
	}
}

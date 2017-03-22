package com.example.mapmessage;

import java.util.ArrayList;
import java.util.List;

import com.example.mapmessage.FriendListActivity.FriendsAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class EnemyListActivity extends Activity {
	
	private Button button_r;
	private Button button_f;
	private Button button1;
	private Button button2;
	private Button button3;
	private Button button4;
	private Button button5;
	private AlertDialog ad;
	private View layout;
	private ListView listView;
	private EnemyAdapter adapter;
	static int flag = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.enemies_list);
		
		if(flag == 0)
		{
			init();
			flag++;
		}
	    adapter = new EnemyAdapter(EnemyListActivity.this,ListAll.enemylist);
	    listView = (ListView)findViewById(R.id.lvw_enemies_list);
	    listView.setAdapter(adapter);
		
	    button1 = (Button)findViewById(R.id.btn_enemies_list_add);
	    button1.setOnClickListener(new OnClickListener()
	       {
	    	   @Override
	    	   public void onClick(View v)
	    	   {
	    		   LayoutInflater inflater = getLayoutInflater();
	    		   //View layout = inflater.inflate(R.layout.dialog_add_friend,(ViewGroup) findViewById(R.id.dialog));
	    		   layout = inflater.inflate(R.layout.dialog_add_enemy,null);
	    		   ad = new AlertDialog.Builder(EnemyListActivity.this).setView(layout).show();
	    		   //点击勾勾
	    		   /* */
	    		   button2 = (Button)layout.findViewById(R.id.btn_dialog_ok_e);
	    		   button2.setOnClickListener(new OnClickListener()
	    		   {
	    		   	   @Override
	    		   	   public void onClick(View v)
	    		   	   {
	    		    		String name = ((EditText)layout.findViewById(R.id.txt_enemy_name)).getText().toString();
	    		    		String num = ((EditText)layout.findViewById(R.id.txt_enemy_number)).getText().toString();
	    		    		Enemy ene = new Enemy(name,num);
	    		    		ListAll.enemylist.add(ene);
	    		    		ad.dismiss(); 
	    		       }
	    		   });
	    		  
	    		   //点击叉叉
	    		   button3 = (Button)layout.findViewById(R.id.btn_dialog_close_e);
	    		   button3.setOnClickListener(new OnClickListener()
	    		   {
	    		   	   @Override
	    		   	   public void onClick(View v)
	    		   	   {
	    		   		   ad.dismiss();  
	    		       }
	    		   });
	    	   }
	       });
	    
		  button_r = (Button)findViewById(R.id.btn_enemies_list_radar);
	       button_r.setOnClickListener(new OnClickListener()
	       {
	    	   @Override
	    	   public void onClick(View v)
	    	   {
	    		   //Intent intent = new Intent(EnemyListActivity.this,MainActivity.class);
	    		   //startActivity(intent);
	    		   finish();
	    	   }
	       });
	       button_f = (Button)findViewById(R.id.btn_enemies_list_friends);
	       button_f.setOnClickListener(new OnClickListener()
	       {
	    	   @Override
	    	   public void onClick(View v)
	    	   {
	    		   Intent intent = new Intent(EnemyListActivity.this,FriendListActivity.class);
	    		   startActivity(intent);
	    	   }
	       });
	}
	private void init()
	{
		Enemy ene1 = new Enemy("qiuxuan","1582057866");
		ListAll.enemylist.add(ene1);
		Enemy ene2 = new Enemy("dengsheng","13169642628");
		ListAll.enemylist.add(ene2);
		Enemy ene3 = new Enemy("zhangtianyi","15820575684");
		ListAll.enemylist.add(ene3);
	}
	
	public class EnemyAdapter extends BaseAdapter {
	    private int resourceId;
		private LayoutInflater mInflater;
		private Context context; 
		private List<Enemy> data = new ArrayList<Enemy>(); 
		public EnemyAdapter(Context context,ArrayList<Enemy> data)
		{
			this.context = context; 
			this.data = data;  
			this.mInflater = LayoutInflater.from(context);
		}
		@Override
		public int getCount() {
			return ListAll.enemylist.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}
		@Override
		public View getView(final int position, View convertView, ViewGroup parent)
		{
			 ViewHolder holder = null;
				if (convertView == null) {
					
					holder=new ViewHolder();  
					
					convertView = mInflater.inflate(R.layout.enemies_list_item, null);
					holder.info = (TextView)convertView.findViewById(R.id.name_cell_e);
					holder.viewBtn = (Button)convertView.findViewById(R.id.delete_button_cell);
					convertView.setTag(holder);				
				}else {				
					holder = (ViewHolder)convertView.getTag();
				}		
				
				holder.info.setText((String)ListAll.enemylist.get(position).getName());
				holder.viewBtn.setTag(position);
				//给Button添加单击事件  添加Button之后ListView将失去焦点  需要的直接把Button的焦点去掉
				
				holder.viewBtn.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						//showInfo(position);	
						 LayoutInflater inflater = getLayoutInflater();
			    		 layout = inflater.inflate(R.layout.dialog_delete,null);
			    		 ad = new AlertDialog.Builder(EnemyListActivity.this).setView(layout).show();
			    		 //点击勾勾，代表确认删除
			    		 button4 = (Button)layout.findViewById(R.id.btn_dialog_ok2);
			    		   button4.setOnClickListener(new OnClickListener()
			    		   {
			    		   	   @Override
			    		   	   public void onClick(View v)
			    		   	   {
			    		   		  // listView.setAdapter(adapter);
			    		   		   ListAll.enemylist.remove(position);
			    		   		listView.setAdapter(adapter);
			    		   		   ad.dismiss(); 
			    		   	   }
			    		   });
			    		   //点击叉叉，代表取消删除
			    		   button5 = (Button)layout.findViewById(R.id.btn_dialog_close2);
			    		   button5.setOnClickListener(new OnClickListener()
			    		   {
			    		   	   @Override
			    		   	   public void onClick(View v)
			    		   	   {
			    		   		   ad.dismiss(); 
			    		   	   }
			    		   });
					}
				});

				return convertView;
		}
	}
}

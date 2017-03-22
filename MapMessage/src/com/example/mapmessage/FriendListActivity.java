package com.example.mapmessage;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract.Data;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FriendListActivity extends Activity {
	//private List<Friends> friendslist = new ArrayList<Friends>();  
	private Button button1;
	private Button button2;
	private Button button3;
	private Button button4;
	private Button button5;
	private Button button_e;
	private Button button_r;
	private AlertDialog ad;
	private View layout;
	private ListView listView;
	private FriendsAdapter adapter;
	static int flag = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.friends_list);
		if(flag == 0)
		{
			init();
			flag++;
		}
	    adapter = new FriendsAdapter(FriendListActivity.this,ListAll.friendslist);
	    listView = (ListView)findViewById(R.id.lvw_friends_list);
	    listView.setAdapter(adapter);
	    
	    button1 = (Button)findViewById(R.id.btn_friends_list_add);
	    button1.setOnClickListener(new OnClickListener()
	       {
	    	   @Override
	    	   public void onClick(View v)
	    	   {
	    		   //Intent intent = new Intent(FriendListActivity.this,FriendAddActivity.class);
	    		   //startActivity(intent);
	    		   LayoutInflater inflater = getLayoutInflater();
	    		   //View layout = inflater.inflate(R.layout.dialog_add_friend,(ViewGroup) findViewById(R.id.dialog));
	    		   layout = inflater.inflate(R.layout.dialog_add_friend,null);
	    		   ad = new AlertDialog.Builder(FriendListActivity.this).setView(layout).show();
	    		   //点击勾勾
	    		   /* */
	    		   button2 = (Button)layout.findViewById(R.id.btn_dialog_ok);
	    		   button2.setOnClickListener(new OnClickListener()
	    		   {
	    		   	   @Override
	    		   	   public void onClick(View v)
	    		   	   {
	    		    		String name = ((EditText)layout.findViewById(R.id.txt_friend_name)).getText().toString();
	    		    		String num = ((EditText)layout.findViewById(R.id.txt_friend_number)).getText().toString();
	    		    		Friends fri = new Friends(name,num);
	    		    		ListAll.friendslist.add(fri);
	    		    		ad.dismiss(); 
	    		       }
	    		   });
	    		  
	    		   //点击叉叉
	    		   button3 = (Button)layout.findViewById(R.id.btn_dialog_close);
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
	    
	    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() 
	    {
	    	 @Override  
	    	 public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	    	 {
	    		 /*
	    		 Friends fri = (Friends)ListAll.friendslist.get(position);
	    		 LayoutInflater inflater = getLayoutInflater();
	    		 layout = inflater.inflate(R.layout.dialog_delete,null);
	    		 ad = new AlertDialog.Builder(FriendListActivity.this).setView(layout).show();	
	    		 */
	    		 //开启新的活动，显示细节
	    		 
	    	 }
	    });
	    
	    
	       button_e = (Button)findViewById(R.id.btn_friends_list_enemies);
	       button_e.setOnClickListener(new OnClickListener()
	       {
	    	   @Override
	    	   public void onClick(View v)
	    	   {
	    		   Intent intent = new Intent(FriendListActivity.this,EnemyListActivity.class);
	    		   startActivity(intent);
	    	   }
	       });
	       
	       button_r = (Button)findViewById(R.id.btn_friends_list_radar);
	       button_r.setOnClickListener(new OnClickListener()
	       {
	    	   @Override
	    	   public void onClick(View v)
	    	   {
	    		   //Intent intent = new Intent(FriendListActivity.this,MainActivity.class);
	    		   //startActivity(intent);
	    		   finish();
	    	   }
	       });
	}
	private void init()
	{
		Friends fri1 = new Friends("zhangtianyi","15820575684");
		ListAll.friendslist.add(fri1);
		Friends fri2 = new Friends("linyuxin","15820575675");
		ListAll.friendslist.add(fri2);
		//Friends fri3 = new Friends("wenshizhan","617920");
		//ListAll.friendslist.add(fri3);
	}
	
	public class FriendsAdapter extends BaseAdapter {
	    private int resourceId;
		private LayoutInflater mInflater;
		private Context context; 
		private List<Friends> data = new ArrayList<Friends>(); 
		public FriendsAdapter(Context context,ArrayList<Friends> data)
		{
			this.context = context; 
			this.data = data;  
			this.mInflater = LayoutInflater.from(context);
		}
		@Override
		public int getCount() {
			return ListAll.friendslist.size();
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
					
					convertView = mInflater.inflate(R.layout.friends_list_item, null);
					holder.info = (TextView)convertView.findViewById(R.id.name_cell);
					holder.viewBtn = (Button)convertView.findViewById(R.id.delete_button_cell);
					convertView.setTag(holder);				
				}else {				
					holder = (ViewHolder)convertView.getTag();
				}		
				
				holder.info.setText((String)ListAll.friendslist.get(position).getName());
				holder.viewBtn.setTag(position);
				//给Button添加单击事件  添加Button之后ListView将失去焦点  需要的直接把Button的焦点去掉
				
				holder.viewBtn.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						//showInfo(position);	
						 LayoutInflater inflater = getLayoutInflater();
			    		 layout = inflater.inflate(R.layout.dialog_delete,null);
			    		 ad = new AlertDialog.Builder(FriendListActivity.this).setView(layout).show();
			    		 //点击勾勾，代表确认删除
			    		 button4 = (Button)layout.findViewById(R.id.btn_dialog_ok2);
			    		   button4.setOnClickListener(new OnClickListener()
			    		   {
			    		   	   @Override
			    		   	   public void onClick(View v)
			    		   	   {
			    		   		  // listView.setAdapter(adapter);
			    		   		   ListAll.friendslist.remove(position);
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

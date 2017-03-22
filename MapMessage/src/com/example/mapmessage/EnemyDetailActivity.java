package com.example.mapmessage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class EnemyDetailActivity extends Activity {
	 @Override  
	    protected void onCreate(Bundle savedInstanceState) {  
	        super.onCreate(savedInstanceState);   
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        setContentView(R.layout.enemy_detail);  
	        
	        
	        Intent intent = getIntent();
	        String number = intent.getStringExtra("number");
	        String lantitude = intent.getStringExtra("lantitude");
	        String longtitude = intent.getStringExtra("longtitude");
	        TextView tv1 = (TextView)findViewById(R.id.txt_enemy_number);
			tv1.setText(number);
			TextView tv2 = (TextView)findViewById(R.id.txt_enemy_long_lang);
			tv2.setText(longtitude);
			TextView tv3 = (TextView)findViewById(R.id.txt_enemy_altitude);
			tv3.setText(lantitude);
	 }
}

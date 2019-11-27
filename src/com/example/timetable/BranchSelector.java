package com.example.timetable;



import java.lang.reflect.Field;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class BranchSelector extends Activity {

	
	SharedPreferences sp;
	String br_name, cls_name;
	public static final String myPrefs = "myPrefs";
	ListView branch_lv;
	Boolean go;
	int flag=1;
	ArrayAdapter<String> adapter;
	String list[] = {"Automobile", "Computer Science", "Civil", "Electrical", "EEE", "ETC", "E&I", "IT", "Mechanical"}; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_branch_selector);
		try{
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
			if(menuKeyField!=null)
			{
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
			}
		}catch(Exception e){}
		ActionBar ab = getActionBar();
		ab.setTitle("Time - Table");
		ab.setDisplayHomeAsUpEnabled(false);
		ab.setBackgroundDrawable(new ColorDrawable(Color.rgb(109, 208, 247)));
		int actionBarTitleId = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
		if(actionBarTitleId>0)
		{
			TextView title = (TextView) findViewById(actionBarTitleId);
			if(title!=null)
				title.setTextColor(Color.WHITE);
		}
		
		branch_lv = (ListView) findViewById(R.id.lvclass);
		adapter = new ArrayAdapter<String>(BranchSelector.this, android.R.layout.simple_expandable_list_item_1, list);
		branch_lv.setAdapter(adapter);
		branch_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				String ITEM = arg0.getItemAtPosition(arg2).toString();
				
				Intent i = new Intent(BranchSelector.this, ClassSelector.class);
				i.putExtra("val", arg2);
				i.putExtra("valS", ITEM);
				finish();
				startActivity(i);
				
			}
		});
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId())
		{
		case(R.id.action_settings):
			flag=0;
			Intent i1=new Intent(BranchSelector.this, Settings.class);
			startActivity(i1);
			break;
		case(R.id.about):
			flag=0;
			Intent i2 = new Intent(BranchSelector.this, AboutUs.class);
			startActivity(i2);
			break;
		case(R.id.holidaylist):
			flag=0;
			Intent i3 = new Intent(BranchSelector.this, Holiday.class);
			startActivity(i3);
			break;	
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.branch_selector, menu);
		return true;
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if(flag==1)
			finish();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(flag==0)
			flag=1;
	}

}

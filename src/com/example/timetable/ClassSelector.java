package com.example.timetable;

import java.lang.reflect.Field;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ClassSelector extends Activity {

	ListView lv;
	ArrayAdapter<String> adapter1;
	String mech_class[] = {"M1", "M2", "M3", "M4", "M5", "M6", "M7"};
	String cse_class[] = {"CS1", "CS2", "CS3", "CS4", "CS5", "CS6", "CS7", "CS8"};
	String it_class[] = {"IT1", "IT2", "IT3", "IT4", "IT5", "IT6"};
	String auto_class[] = {"M8"};
	String ee_class[] = {"EL1", "EL2", "EL3", "EL4", "EL5", "EL6"};
	String etc_class[] = {"ETC1", "ETC2", "ETC3", "ETC4", "ETC5", "ETC6", "ETC7"};
	String ei_class[] = {"E&I"};
	String eee_class[] = {"EEE1", "EEE2", "EEE3", "EEE4", "EEE5", "EEE6"};
	String civil_class[] = {"CV1", "CV2", "CV3", "CV4", "CV5", "CV6"};
	String c_list[];
	String br="";
	int flag=1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_class_selector);
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
		ab.setTitle("Select your class");
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setBackgroundDrawable(new ColorDrawable(Color.rgb(109, 208, 247)));
		int actionBarTitleId = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
		if(actionBarTitleId>0)
		{
			TextView title = (TextView) findViewById(actionBarTitleId);
			if(title!=null)
				title.setTextColor(Color.WHITE);
		}
		
		lv=(ListView) findViewById(R.id.lvclass);
		Intent i = getIntent();
		br=i.getStringExtra("valS").toString();
		int v = i.getIntExtra("val", 0);
		switch (v) {
		case 0:
			c_list = auto_class;
			break;
		case 1:
			c_list = cse_class;
			break;	
		case 2:
			c_list = civil_class;
			break;	
		case 3:
			c_list = ee_class;
			break;
		case 4:
			c_list = eee_class;
			break;
		case 5:
			c_list = etc_class;
			break;
		case 6:
			c_list = ei_class;
			break;
		case 7:
			c_list = it_class;
			break;	
		case 8:
			c_list = mech_class;
			break;	
		default:
			break;
		}
		
		adapter1 = new ArrayAdapter<String>(ClassSelector.this, android.R.layout.simple_expandable_list_item_1, c_list);
		lv.setAdapter(adapter1);
		lv.setOnItemClickListener(new OnItemClickListener() {

			

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				String item_class = arg0.getItemAtPosition(arg2).toString();
				Intent iDisplay = new Intent(ClassSelector.this, Display.class);
				iDisplay.putExtra("branch", br);
				iDisplay.putExtra("section", item_class);
				startActivity(iDisplay);
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
			Intent i1=new Intent(ClassSelector.this, Settings.class);
			startActivity(i1);
			break;
		case(R.id.about):
			flag=0;
			Intent i2 = new Intent(ClassSelector.this, AboutUs.class);
			startActivity(i2);
			break;
		case(R.id.holidaylist):
			flag=0;
			Intent i3 = new Intent(ClassSelector.this, Holiday.class);
			startActivity(i3);
			break;
		case(android.R.id.home):
			Intent i4 = new Intent(ClassSelector.this, BranchSelector.class);
			startActivity(i4);
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.class_selector, menu);
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

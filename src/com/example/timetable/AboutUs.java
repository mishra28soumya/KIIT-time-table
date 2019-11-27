package com.example.timetable;

import java.lang.reflect.Field;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.widget.TextView;

public class AboutUs extends Activity {
	int flag=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_us);
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
		ab.setBackgroundDrawable(new ColorDrawable(Color.rgb(109, 208, 247)));
		int actionBarTitleId = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
		if(actionBarTitleId>0)
		{
			TextView title = (TextView) findViewById(actionBarTitleId);
			if(title!=null)
				title.setTextColor(Color.WHITE);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.about_us, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId())
		{
		case(R.id.action_settings):
			flag=0;
			Intent i1=new Intent(AboutUs.this, Settings.class);
			startActivity(i1);
			break;
		
		case(R.id.holidaylist):
			flag=0;
		    Intent i3 = new Intent(AboutUs.this, Holiday.class);
			startActivity(i3);
			break;
		
		}
		return super.onOptionsItemSelected(item);
	}

}

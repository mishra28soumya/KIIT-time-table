package com.example.timetable;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class Holiday extends Activity {
int flag=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_holiday);
		ActionBar ab = getActionBar();
		ab.setTitle("Holiday List - 2016");
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
		getMenuInflater().inflate(R.menu.holiday, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId())
		{
		case(R.id.action_settings):
			flag=0;
			Intent i1=new Intent(Holiday.this, Settings.class);
			startActivity(i1);
			break;
		
		case(R.id.about):
			flag=0;
		    Intent i3 = new Intent(Holiday.this, AboutUs.class);
			startActivity(i3);
			break;
		
		}
		return super.onOptionsItemSelected(item);
	}

}

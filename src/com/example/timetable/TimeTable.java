package com.example.timetable;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;

public class TimeTable extends Activity {

	Thread th;
	int flag=0;
	SharedPreferences sp;
	String b_name, c_name, myPrefs = "myPrefs";
	int pos_br, pos_cls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);
        ActionBar ab = getActionBar();
        ab.hide();
        th = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(1500);
					
					sp=getSharedPreferences(myPrefs, MODE_PRIVATE);
					pos_br = sp.getInt("branch_position", 0);
					pos_cls = sp.getInt("section_position", 0);
					c_name = sp.getString("section", "");
					b_name = sp.getString("branch", "");
					if((!c_name.equals(""))&&(!b_name.equals("")))
					{
						Intent i = new Intent(TimeTable.this, Display.class);
						i.putExtra("branch", b_name);
						i.putExtra("section", c_name);
						startActivity(i);
					}
					else
					{
						Intent i = new Intent(TimeTable.this, BranchSelector.class);
						startActivity(i);
					}
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
        th.start();
        
        
    }

    @Override
    protected void onPause() {
    	// TODO Auto-generated method stub
    	super.onPause();
    	finish();
    }
   /* public void next(View v)
    {
    	
    	flag=1;
    	sp=getSharedPreferences(myPrefs, MODE_PRIVATE);
		pos_br = sp.getInt("branch_position", 0);
		pos_cls = sp.getInt("section_position", 0);
		c_name = sp.getString("section", "");
		b_name = sp.getString("branch", "");
		if((!c_name.equals(""))&&(!b_name.equals("")))
		{
			Intent i = new Intent(this, Display.class);
			i.putExtra("branch", b_name);
			i.putExtra("section", c_name);
			startActivity(i);
		}
		else
		{
			Intent i = new Intent(TimeTable.this, BranchSelector.class);
			startActivity(i);
		}
    	finish();
    } */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.time_table, menu);
        return true;
    }
    
}

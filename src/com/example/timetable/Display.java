package com.example.timetable;



import java.lang.reflect.Field;
import java.util.Calendar;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Display extends Activity {

	HorizontalScrollView hsv;
	SQLiteDatabase db;
	SharedPreferences sp;
	String myPrefs="myPrefs";
	TableLayout tl;
	TextView Cfirst, Csecond, Cthird, Cfourth, Cfifth, Csixth, Cseventh, Ceighth, Tfirst, Tsecond, Tthird, Tfourth, Tfifth, Tsixth, Tseventh, Teighth, first, second, third, fourth, fifth, sixth, seventh, eighth, sect1, disclaimer, br, cls;
	String branch, section, DAY="Monday", BR, CLS, temp, sql;
	Button mon, tue, wed, thur, fri;
	
	ImageView menuButton;
	String list[] = {"Automobile", "Computer Science", "Civil", "Electrical", "EEE", "ETC", "E&I", "IT", "Mechanical"}; 

	String b_list[] = {"Automobile", "CSE", "Civil", "Electrical", "EEE", "ETC", "E&I", "IT", "Mechanical"}; 
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
	int dayNumber;
	DrawerLayout mDrawerLayout;
	ListView leftDrawerList, rightDrawerList;
	ArrayAdapter<String> adapter_br, adapter_cls;
	LinearLayout layoutOfTable;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display);
		
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
		ab.setTitle("Time - Table");
		
		ab.setDisplayOptions(R.drawable.mb1);
		
		int actionBarTitleId = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
		if(actionBarTitleId>0)
		{
			TextView title = (TextView) findViewById(actionBarTitleId);
			if(title!=null)
				title.setTextColor(Color.WHITE);
				title.setTextSize(25);
				
		}
		
		hsv=(HorizontalScrollView) findViewById(R.id.horizontalScrollView1);
		br=(TextView) findViewById(R.id.br);
		cls=(TextView) findViewById(R.id.cls);
		
		adapter_br = new ArrayAdapter<String>(Display.this, android.R.layout.simple_expandable_list_item_1, b_list);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        leftDrawerList = (ListView) findViewById(R.id.left_drawer);
        rightDrawerList = (ListView) findViewById(R.id.right_drawer);
        leftDrawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, list ));
        
        leftDrawerList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				temp = arg0.getItemAtPosition(arg2).toString();
				switch(arg2)
				{
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
					
				}
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(Display.this, android.R.layout.simple_expandable_list_item_1, c_list);
				rightDrawerList.setAdapter(adapter);
				mDrawerLayout.closeDrawer(leftDrawerList);
				mDrawerLayout.openDrawer(rightDrawerList);
				rightDrawerList.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						branch = temp;
						section = arg0.getItemAtPosition(arg2).toString();
						print();
						mDrawerLayout.closeDrawer(rightDrawerList);
					}
				});
				
			}
		});
		
        
       
		
		Intent i = getIntent();
			branch = i.getStringExtra("branch");
			section = i.getStringExtra("section");
			
			for(int j=0; j<list.length; j++)
	        {
	        	if(list[j].equals(branch))
	        	{
	        		switch(j)
					{
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
						
					}
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(Display.this, android.R.layout.simple_expandable_list_item_1, c_list);
					rightDrawerList.setAdapter(adapter);
	        	}
	        }	
		
		
		mon=(Button) findViewById(R.id.save);
		tue=(Button) findViewById(R.id.button2);
		wed=(Button) findViewById(R.id.button3);
		thur=(Button) findViewById(R.id.button4);
		fri=(Button) findViewById(R.id.button5);
		
		tl = (TableLayout) findViewById(R.id.tableLayout1);
		tl.setBackgroundColor(Color.LTGRAY);

		Cfirst = (TextView) findViewById(R.id.Cfirst);
		Csecond = (TextView) findViewById(R.id.Csecond);
		Cthird = (TextView) findViewById(R.id.Cthird);
		Cfourth = (TextView) findViewById(R.id.Cfourth);
		Cfifth = (TextView) findViewById(R.id.Cfifth);
		Csixth = (TextView) findViewById(R.id.Csixth);
		Cseventh = (TextView) findViewById(R.id.Cseventh);
		Ceighth = (TextView) findViewById(R.id.Ceighth);
		
		Tfirst = (TextView) findViewById(R.id.Tfirst);
		Tsecond = (TextView) findViewById(R.id.Tsecond);
		Tthird = (TextView) findViewById(R.id.Tthird);
		Tfourth = (TextView) findViewById(R.id.Tfourth);
		Tfifth = (TextView) findViewById(R.id.Tfifth);
		Tsixth = (TextView) findViewById(R.id.Tsixth);
		Tseventh = (TextView) findViewById(R.id.Tseventh);
		Teighth = (TextView) findViewById(R.id.Teighth);
		
		first = (TextView) findViewById(R.id.first);		
		second = (TextView) findViewById(R.id.second);
		third = (TextView) findViewById(R.id.third);
		fourth = (TextView) findViewById(R.id.fourth);
		fifth = (TextView) findViewById(R.id.fifth);
		sixth = (TextView) findViewById(R.id.sixth);
		seventh = (TextView) findViewById(R.id.seventh);
		eighth = (TextView) findViewById(R.id.eighth);
		
		disclaimer = (TextView) findViewById(R.id.disclaimer);
	
		Calendar calendar = Calendar.getInstance();
        int currentDay = calendar.get(Calendar.DAY_OF_WEEK);
        dayNumber=currentDay;
        switch(currentDay)
        {
        case 1:
        	DAY ="Monday";
        	mon.setBackgroundResource(R.drawable.monday1);
        	hsv.scrollTo(0, 0);
        	break;
        case 2:
        	DAY ="Monday";
        	mon.setBackgroundResource(R.drawable.monday1);
        	hsv.scrollTo(0, 0);
        	break;	
        case 3:
        	DAY ="Tuesday";
        	tue.setBackgroundResource(R.drawable.tuesday1);
        	hsv.scrollTo(50, 0);
        	break;	
        case 4:
        	DAY ="Wednesday";
        	wed.setBackgroundResource(R.drawable.wednesday1);
        	hsv.scrollTo(300, 0);
        	break;	
        case 5:
        	DAY ="Thursday";
        	thur.setBackgroundResource(R.drawable.thursday1);
        	hsv.scrollTo(560, 0);
        	break;	
        case 6:
        	DAY ="Friday";
        	fri.setBackgroundResource(R.drawable.friday1);
        	hsv.scrollBy(815, 0);
        	//hsv.smoothScrollTo(815, 0);
        	break;	
        case 7:
        	DAY ="Saturday";
        	fri.setBackgroundResource(R.drawable.monday1);
        	hsv.scrollBy(815, 0);
        	//hsv.smoothScrollTo(815, 0);
        	break;
       
        
        }
		
		
		db = openOrCreateDatabase("routine1", MODE_PRIVATE, null);
		
		try {
			db.execSQL("create table Mech123(brName varchar2(50), secName varchar2(50), day varchar2(50),First varchar2(50),Second varchar2(50),Third varchar2(50),Fourth varchar2(50),Fifth varchar2(50),Sixth varchar2(50),Seventh varchar2(50),Eighth varchar2(50), CFirst varchar2(50), CSecond varchar2(50), CThird varchar2(50), CFourth varchar2(50), CFifth varchar2(50), CSixth varchar2(50),CSeventh varchar2(50), CEighth varchar2(50), TFirst varchar2(50), TSecond varchar2(50), TThird varchar2(50), TFourth varchar2(50), TFifth varchar2(50), TSixth varchar2(50), TSeventh varchar2(50), TEighth varchar2(50))");
		} catch (Exception e) {
		}
		
		sql = "insert into Mech123 values('Mechanical', 'M1', 'Monday', 'RAC', 'MCCTD', 'CADA LAB', '(M11-201', 'M12-CR2)', '*****', 'MQCR', 'OB', '201', '201', ' ', ' ', ' ', '*****', '201', '201', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', '12:00 - 1:00', 'RECESS', '2:00 - 3:00', '3:00 - 4:00')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Mechanical', 'M1', 'Tuesday', 'MQCR', 'RAC', 'MCCTD', 'DME', '*****', 'MCCTD LAB', '(M11-201', 'M12-204)', '201', '201', '201', '201', '*****', ' ', ' ', ' ', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', 'RECESS', '2:00 - 3:00', '3:00 - 4:00', '4:00 - 5:00')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Mechanical', 'M1', 'Wednesday', 'DME', 'IE&M', 'RAC', 'MCCTD', '*****', 'ICE&RAC LAB', '(M11-L001', 'M12-L102)', '201', '201', '201', '201', '*****', ' ', ' ', ' ', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', 'RECESS', '2:00 - 3:00', '3:00 - 4:00', '4:00 - 5:00')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Mechanical', 'M1', 'Thursday', 'OB', 'IE&M', 'MQCR', 'DME', '*****', 'CTP LAB', '(M11-CAD LAB', 'M12-CAE LAB 2)', '201', '201', '201', '201', '*****', ' ', ' ', ' ', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', 'RECESS', '2:00 - 3:00', '3:00 - 4:00', '4:00 - 5:00')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Mechanical', 'M1', 'Friday', 'OB', 'MCCTD', 'IE&M', 'CAT II', 'CAT II', '*****', 'X', 'X', '201', '201', '201', '201', '201', '*****', 'X', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', '12:00 - 1:00', 'RECESS', 'X', 'X')";
		db.execSQL(sql);
		
		sql = "insert into Mech123 values('Mechanical', 'M2', 'Monday', 'OB', 'DME', 'RAC', 'IE&M', '*****', 'CADA LAB', '(M21-202', 'M22-203)', '202', '202', '202', '202', '*****', ' ', ' ', ' ', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', 'RECESS', '2:00 - 3:00', '3:00 - 4:00', '4:00 - 5:00')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Mechanical', 'M2', 'Tuesday', 'OB', 'MQCR', 'MCCTD', 'CAT II', 'CAT II', '*****', 'X', 'X', '202', '202', '202', '202', '202', '*****', 'X', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', '12:00 - 1:00', 'RECESS', 'X', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Mechanical', 'M2', 'Wednesday', 'IE&M', 'RAC', 'MCCTD', 'MQCR', '*****', 'CTP LAB', '(M21-CAD LAB', 'M22-CAE LAB 2)', '202', '202', '202', '202', '*****', ' ', ' ', ' ', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', 'RECESS', '2:00 - 3:00', '3:00 - 4:00', '4:00 - 5:00')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Mechanical', 'M2', 'Thursday', 'DME', 'MQCR', 'OB', 'MCCTD', '*****', 'ICE&RAC LAB', '(M21-L001', 'M22-L102)', '202', '202', '202', '202', '*****', ' ', ' ', ' ', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', 'RECESS', '2:00 - 3:00', '3:00 - 4:00', '4:00 - 5:00')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Mechanical', 'M2', 'Friday', 'DME', 'RAC', 'IE&M', 'MCCTD', '*****', 'MCCTD LAB', '(M21-202', 'M22-203)', '202', '202', '202', '202', '*****', ' ', ' ', ' ', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', 'RECESS', '2:00 - 3:00', '3:00 - 4:00', '4:00 - 5:00')";
		db.execSQL(sql);
		
		sql = "insert into Mech123 values('Mechanical', 'M3', 'Monday', 'DME', 'MQCR', 'IE&M', 'MCCTD', '*****', 'ICE&RAC LAB', '(M31-L001', 'M32-L102)', '203', '203', '203', '203', '*****', ' ', ' ', ' ', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', 'RECESS', '2:00 - 3:00', '3:00 - 4:00', '4:00 - 5:00')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Mechanical', 'M3', 'Tuesday', 'MCCTD', 'DME', 'CADA LAB', '(M31-203', 'M32-CR2)', 'MQCR', 'IE&M', 'OB', '203', '203', ' ', ' ', ' ', '203', '203', '203', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', '12:00 - 1:00', '2:00 - 3:00', '3:00 - 4:00', '4:00 - 5:00')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Mechanical', 'M3', 'Wednesday', 'OB', 'RAC', 'IE&M', 'MCCTD', '*****', 'MCCTD LAB', '(M31-202', 'M32-203)', '203', '203', '203', '203', '*****', ' ', ' ', ' ', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', 'RECESS', '2:00 - 3:00', '3:00 - 4:00', '4:00 - 5:00')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Mechanical', 'M3', 'Thursday', 'CAT II', 'CAT II', 'RAC', 'MQCR', '*****', 'X', 'X', 'X', '203', '203', '203', '203', '*****', 'X', 'X', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', 'RECESS', 'X', 'X', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Mechanical', 'M3', 'Friday', 'OB', 'DME', 'MCCTD', 'RAC', '*****', 'CTP LAB', '(M31-CAD LAB', 'M32-CAE LAB 2)', '203', '203', '203', '203', '*****', ' ', ' ', ' ', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', 'RECESS', '2:00 - 3:00', '3:00 - 4:00', '4:00 - 5:00')";
		db.execSQL(sql);
		
		sql = "insert into Mech123 values('Mechanical', 'M4', 'Monday', 'DME', 'IE&M', 'MCCTD', 'MQCR', '*****', 'CTP LAB', '(M41-CAD LAB', 'M42-CAE LAB 2)', '204', '204', '204', '204', '*****', ' ', ' ', ' ', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', 'RECESS', '2:00 - 3:00', '3:00 - 4:00', '4:00 - 5:00')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Mechanical', 'M4', 'Tuesday', 'RAC', 'DME', 'MCCTD', 'MQCR', '*****', 'ICE&RAC LAB', '(M41-L001', 'M42-L102)', '204', '204', '204', '204', '*****', ' ', ' ', ' ', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', 'RECESS', '2:00 - 3:00', '3:00 - 4:00', '4:00 - 5:00')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Mechanical', 'M4', 'Wednesday', 'RAC', 'OB', 'CADA LAB', '(M41-204', 'M42-205)', 'X', 'X', 'X', '204', '204', ' ', ' ', ' ', 'X', 'X', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', '12:00 - 1:00', 'X', 'X', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Mechanical', 'M4', 'Thursday', 'OB', 'RAC', 'IE&M', 'MCCTD', '*****', 'MCCTD LAB', '(M41-204', 'M42-201)', '204', '204', '204', '204', '*****', ' ', ' ', ' ', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', 'RECESS', '2:00 - 3:00', '3:00 - 4:00', '4:00 - 5:00')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Mechanical', 'M4', 'Friday', 'OB', 'IE&M', 'MQCR', 'MCCTD', '*****', 'DME', 'CAT II', 'CAT II', '204', '204', '204', '204', '*****', '204', '204', '204', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', 'RECESS', '2:00 - 3:00', '3:00 - 4:00', '4:00 - 5:00')";
		db.execSQL(sql);
		
		sql = "insert into Mech123 values('Mechanical', 'M5', 'Monday', 'OB', 'MQCR', 'IE&M', 'MCCTD', '*****', 'MCCTD LAB', '(M51-204', 'M52-205)', '205', '205', '205', '205', '*****', ' ', ' ', ' ', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '12:00 - 1:00', 'RECESS', '2:00 - 3:00', '3:00 - 4:00', '4:00 - 5:00')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Mechanical', 'M5', 'Tuesday', 'MCCTD', 'DME', 'RAC', 'CAT II', 'CAT II', '*****', 'OB', 'IE&M', '205', '205', '205', '205', '205', '*****', '205', '205', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', '12:00 - 1:00', 'RECESS', '2:00 - 3:00', '3:00 - 4:00')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Mechanical', 'M5', 'Wednesday', 'DME', 'MQCR', 'CTP LAB', '(M51-CAD LAB', 'M52-CAE LAB 2)', '*****', 'RAC', 'OB', '205', '205', ' ', ' ', ' ', '*****', '205', '205', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', '12:00 - 1:00', 'RECESS', '2:00 - 3:00', '3:00 - 4:00')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Mechanical', 'M5', 'Thursday', 'MCCTD', 'RAC', 'CADA LAB', '(M51-205', 'M52-CR1)', '*****', 'IE&M', 'DME', '205', '205', ' ', ' ', ' ', '*****', '205', '205', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', '12:00 - 1:00', 'RECESS', '2:00 - 3:00', '3:00 - 4:00')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Mechanical', 'M5', 'Friday', 'MQCR', 'MCCTD', 'ICE&RAC LAB', '(M51-L001', 'M52-L102)', '*****', 'X', 'X', '205', '205', ' ', ' ', ' ', '*****', 'X', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', '12:00 - 1:00', 'RECESS', 'X', 'X')";
		db.execSQL(sql);
		
		sql = "insert into Mech123 values('Mechanical', 'M6', 'Monday', 'OB', 'DME', 'MCCTD', 'RAC', '*****', 'MQCR', 'CAT II', 'CAT II', 'CR1', 'CR1', 'CR1', 'CR1', '*****', 'CR1', 'CR1', 'CR1', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', 'RECESS', '2:00 - 3:00', '3:00 - 4:00', '4:00 - 5:00')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Mechanical', 'M6', 'Tuesday', 'OB', 'RAC', 'MCCTD', 'MQCR', '*****', 'MCCTD LAB', '(M61-CR1', 'M62-202)', 'CR1', 'CR1', 'CR1', 'CR1', '*****', ' ', ' ', ' ', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', 'RECESS', '2:00 - 3:00', '3:00 - 4:00', '4:00 - 5:00')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Mechanical', 'M6', 'Wednesday', 'IE&M', 'MCCTD', 'ICE&RAC LAB', '(M61-L001', 'M62-L102)', '*****', 'X', 'X', 'CR1', 'CR1', ' ', ' ', ' ', '*****', 'X', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', '12:00 - 1:00', 'RECESS', 'X', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Mechanical', 'M6', 'Thursday', 'IE&M', 'DME', 'CTP LAB', '(M61-CAD LAB', 'M62-CAE LAB 2)', '*****', 'RAC', 'MQCR', 'CR1', 'CR1', ' ', ' ', ' ', '*****', 'CR1', 'CR1', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', '12:00 - 1:00', 'RECESS', '2:00 - 3:00', '3:00 - 4:00')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Mechanical', 'M6', 'Friday', 'IE&M', 'DME', 'MCCTD', 'OB', '*****', 'CADA LAB', '(M61-CR1', 'M62-201)', 'CR1', 'CR1', 'CR1', 'CR1', '*****', ' ', ' ', ' ', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', 'RECESS', '2:00 - 3:00', '3:00 - 4:00', '4:00 - 5:00')";
		db.execSQL(sql);
		
		sql = "insert into Mech123 values('Mechanical', 'M7', 'Monday', 'IE&M', 'RAC', 'CTP LAB', '(M71-CAD LAB', 'M72-CAE LAB 2)', '*****', 'OB', 'MCCTD', 'CR2', 'CR2', ' ', ' ', ' ', '*****', 'CR2', 'CR2', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', '12:00 - 1:00', 'RECESS', '2:00 - 3:00', '3:00 - 4:00')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Mechanical', 'M7', 'Tuesday', 'IE&M', 'DME', 'ICE&RAC LAB', '(M71-L001', 'M72-L102)', '*****', 'MCCTD', 'MQCR', 'CR2', 'CR2', ' ', ' ', ' ', '*****', 'CR2', 'CR2', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', '12:00 - 1:00', 'RECESS', '2:00 - 3:00', '3:00 - 4:00')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Mechanical', 'M7', 'Wednesday', 'OB', 'RAC', 'MCCTD LAB', '(M71-CR1', 'M72-CR2)', '*****', 'MCCTD', 'MQCR', 'CR2', 'CR2', ' ', ' ', ' ', '*****', 'CR2', 'CR2', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', '12:00 - 1:00', 'RECESS', '2:00 - 3:00', '3:00 - 4:00')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Mechanical', 'M7', 'Thursday', 'RAC', 'IE&M', 'DME', 'MCCTD', '*****', 'MQCR', 'CAT II', 'CAT II', 'CR2', 'CR2', 'CR2', 'CR2', '*****', 'CR2', 'CR2', 'CR2', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', 'RECESS', '2:00 - 3:00', '3:00 - 4:00', '4:00 - 5:00')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Mechanical', 'M7', 'Friday', 'OB', 'DME', 'CADA LAB', '(M71-CR2', 'M72-205)', '*****', 'X', 'X', 'CR2', 'CR2', ' ', ' ', ' ', '*****', 'X', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', '12:00 - 1:00', 'RECESS', 'X', 'X')";
		db.execSQL(sql);
		
		sql = "insert into Mech123 values('Automobile', 'M8', 'Monday', 'ICE&RAC LAB', '(M81-L001', 'M82-L102)', 'MCCTD', 'IE&M', 'DME', 'MQCR', 'OB', ' ', ' ', ' ', '302', '302', 'T1', 'T1', 'T1', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', '12:00 - 1:00', '2:00 - 3:00', '3:00 - 4:00', '4:00 - 5:00')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Automobile', 'M8', 'Tuesday', 'RAC', 'MQCR', 'MCCTD', '*****', 'CADA LAB', '(M81-204', 'M82-302)', 'X', '301', '301', '301', '*****', ' ', ' ', ' ', 'X', '10:00 - 11:00', '11:00 - 12:00', '12:00 - 1:00', 'RECESS', '2:00 - 3:00', '3:00 - 4:00', '4:00 - 5:00', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Automobile', 'M8', 'Wednesday', 'MCCTD', 'RAC', 'IE&M', '*****', 'MCCTD LAB', '(M81-201', 'M82-204)', 'X', '301', '301', '301', '*****', ' ', ' ', ' ', 'X', '10:00 - 11:00', '11:00 - 12:00', '12:00 - 1:00', 'RECESS', '2:00 - 3:00', '3:00 - 4:00', '4:00 - 5:00', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Automobile', 'M8', 'Thursday', 'OB', 'CAT II', 'CAT II', '*****', 'MQCR', 'DME', 'IE&M', 'X', '303', '303', '303', '*****', '303', '303', '303', 'X', '10:00 - 11:00', '11:00 - 12:00', '12:00 - 1:00', 'RECESS', '2:00 - 3:00', '3:00 - 4:00', '4:00 - 5:00', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Automobile', 'M8', 'Friday', 'CTP LAB', '(M81-CAD LAB', 'M82-CAE LAB 2)', 'IE&M', 'RAC', 'MCCTD', 'DME', 'OB', ' ', ' ', ' ', 'T1', 'T1', '304', '304', '304', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', '12:00 - 1:00', '2:00 - 3:00', '3:00 - 4:00', '4:00 - 5:00')";
		db.execSQL(sql);
		
		sql = "insert into Mech123 values('Electrical', 'EL1', 'Monday', 'PSOC', 'CE', 'EMF', ' ', 'PS LAB (E1)', '(RM / TBL)', 'X', 'X', 'A-104', 'A-104', 'A-104', ' ', ' ', ' ', 'X', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'X', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Electrical', 'EL1', 'Tuesday', 'PSOC', 'MCP', 'EMF', '*****', 'CE', 'IDSP', 'X', 'X', 'C-14', 'C-14', 'C-14', '*****', 'A-104', 'A-104', 'X', 'X', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', 'X', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Electrical', 'EL1', 'Wednesday', 'MCP', 'IDSP', 'PSOC', '*****', ' ', 'EMD (E1) (AP)', ' ', 'X', 'C-14', 'C-14', 'C-14', '*****', ' ', 'A-11', ' ', 'X', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Electrical', 'EL1', 'Thursday', 'IDSP', 'CE', 'ELECTIVE-I', '*****', ' ', 'T & P', ' ', 'X', 'C-16', 'C-16', 'C-16', '*****', ' ', 'E-205', ' ', 'X', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Electrical', 'EL1', 'Friday', 'EMF', 'CE', '*****', 'PSOC', 'MCP', 'IDSP', 'X', 'X', 'C-13', 'C-13', '*****', 'A-104', 'A-104', 'A-104', 'X', 'X', '11:00 - 12:00', '12:00 - 1:00', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00', 'X', 'X')";
		db.execSQL(sql);
		
		sql = "insert into Mech123 values('Electrical', 'EL2', 'Monday', 'MCP', 'PSOC', 'EMF', '*****', 'CE', 'IDSP', 'ELECTIVE-I', 'X', 'C-14', 'C-14', 'C-14', '*****', 'A-104', 'A-104', 'A-104', 'X', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Electrical', 'EL2', 'Tuesday', 'MCP', 'CE', 'IDSP', ' ', 'PS LAB (E2)', '(JSR / BGP)', '*****', 'ELECTIVE-I', 'A-104', 'A-104', 'A', ' ', ' ', ' ', '*****', ' ', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'RECESS', '5:00 - 6:00')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Electrical', 'EL2', 'Wednesday', 'EMF', 'PSOC', 'CE', '*****', 'IDSP', 'X', 'X', 'X', 'C-15', 'C-15', 'C-15', '*****', 'C-16', 'X', 'X', 'X', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'RECESS', '3:00 - 4:00', 'X', 'X', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Electrical', 'EL2', 'Thursday', ' ', 'T & P', ' ', 'ELECTIVE-I', '*****', 'MCP', 'PSOC', 'EMF', ' ', 'E-205', ' ', ' ', '*****', 'C-13', 'C-13', 'C-13', ' ', '8:00 - 11:00', ' ', '1:00 - 2:00', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Electrical', 'EL2', 'Friday', 'PSOC', 'IDSP', 'CE', '*****', ' ', 'EMD (E2) (AP)', ' ', 'X', 'C-14', 'C-14', 'C-14', '*****', ' ', 'A-11', ' ', 'X', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00', 'X')";
		db.execSQL(sql);
		
		sql = "insert into Mech123 values('Electrical', 'EL3', 'Monday', 'PSOC', 'MCP', 'CE', '*****', 'EMF', 'IDSP', 'ELECTIVE-I', 'X', 'C-15', 'C-15', 'C-15', '*****', 'C-14', 'C-14', 'C-14', 'X', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Electrical', 'EL3', 'Tuesday', ' ', 'EMD (E3) (S ROY)', ' ', '*****', 'PSOC', 'IDSP', 'ELECTIVE-I', 'X', ' ', 'A-11', ' ', '*****', 'A-106', 'A-106', 'A-106', 'X', ' ', '8:00 - 11:00', ' ', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Electrical', 'EL3', 'Wednesday', 'MCP', 'IDSP', 'CE', ' ', 'PS LAB (E3)', '(SRG/SBS)', 'X', 'X', 'A-104', 'A-104', 'A-104', ' ', ' ', ' ', 'X', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', ' ', '11:00 - 2:00', ' ', 'X', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Electrical', 'EL3', 'Thursday', 'EMF', 'MCP', 'ELECTIVE-I', '*****', 'PSOC', 'CE', 'IDSP', 'X', 'C-18', 'C-18', 'C-18', '*****', 'C-15', 'C-15', 'C-15', 'X', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Electrical', 'EL3', 'Friday', ' ', 'T & P', ' ', '*****', 'EMF', 'PSOC', 'CE', 'X', ' ', 'E-205', ' ', '*****', 'E-205', 'E-205', 'E-205', 'X', ' ', '8:00 - 11:00', ' ', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00', 'X')";
		db.execSQL(sql);
		
		sql = "insert into Mech123 values('Electrical', 'EL4', 'Monday', ' ', 'EMD (E4) (SKB)', ' ', '*****', 'PSOC', 'CE', 'ELECTIVE-I', 'X', ' ', 'A-11', ' ', '*****', 'C-18', 'C-18', 'C-18', 'X', ' ', '8:00 - 11:00', ' ', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Electrical', 'EL4', 'Tuesday', 'MCP', 'PSOC', 'IDSP', '*****', 'EMF', 'CE', 'ELECTIVE-I', 'X', 'C-15', 'C-15', 'C-15', '*****', 'A-11', 'A-11', 'A-11', 'X', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Electrical', 'EL4', 'Wednesday', ' ', 'T & P', ' ', '*****', 'MCP', 'CE', 'IDSP', 'X', ' ', 'E-205', ' ', '*****', 'E-205', 'E-205', 'E-205', 'X', ' ', '8:00 - 11:00', ' ', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Electrical', 'EL4', 'Thursday', 'PSOC', 'CE', 'ELECTIVE-I', '*****', 'IDSP', 'EMF', 'IDSP', 'X', 'C-13', 'C-13', 'C-13', '*****', 'A-104', 'A-104', 'A-104', 'X', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Electrical', 'EL4', 'Friday', ' ', 'PS LAB (E4)', '(SS/TBL)', 'MCP', 'PSOC', 'EMF', 'X', 'X', ' ', ' ', ' ', 'C-15', 'C-15', 'C-15', 'X', 'X', ' ', '8:00 - 11:00', ' ', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'X', 'X')";
		db.execSQL(sql);
		
		sql = "insert into Mech123 values('Electrical', 'EL5', 'Monday', 'PSOC', 'EMF', 'CE', '*****', 'MCP', 'IDSP', 'ELECTIVE-I', 'X', 'C-16', 'C-16', 'C-16', '*****', 'E-205', 'E-205', 'E-205', 'X', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Electrical', 'EL5', 'Tuesday', ' ', 'T & P', '', '*****', 'CE', 'IDSP', 'ELECTIVE-I', 'X', ' ', 'E-205', ' ', '*****', 'E-205', 'E-205', 'E-205', 'X', ' ', '8:00 - 11:00', ' ', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Electrical', 'EL5', 'Wednesday', 'EMF', 'PSOC', 'CE', '*****', 'IDSP', 'IDSP', 'X', 'X', 'C-16', 'C-16', 'C-16', '*****', 'A-104', 'A-104', 'X', 'X', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', 'X', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Electrical', 'EL5', 'Thursday', ' ', 'EMD (E5) (AP)', ' ', '*****', 'PSOC', 'MCP', 'EMF', 'X', ' ', 'A-11', ' ', '*****', 'A-11', 'A-11', 'A-11', 'X', ' ', '8:00 - 11:00', ' ', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Electrical', 'EL5', 'Friday', 'MCP', 'PSOC', 'CE', ' ', 'PS LAB (E5)', '(SRG/SNB)', 'X', 'X', 'A-11', 'A-11', 'A-11', ' ', ' ', ' ', 'X', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', ' ', '11:00 - 2:00', ' ', 'X', 'X')";
		db.execSQL(sql);
		
		sql = "insert into Mech123 values('Electrical', 'EL6', 'Monday', ' ', 'T & P', ' ', '*****', 'PSOC', 'MCP', 'ELECTIVE-I', 'X', ' ', 'E-205', ' ', '*****', 'A-11', 'A-11', 'A-11', 'X', ' ', '8:00 - 11:00', ' ', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Electrical', 'EL6', 'Tuesday', 'PSOC', 'MCP', 'EMF', '*****', 'IDSP', 'CE', 'ELECTIVE-I', 'X', 'C-16', 'C-16', 'C-16', '*****', 'C-18', 'C-18', 'C-18', 'X', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Electrical', 'EL6', 'Wednesday', ' ', 'EMD (E6) (SKB)', ' ', '*****', 'EMF', 'CE', 'IDSP', 'X', ' ', 'A-11', ' ', '*****', 'C-18', 'C-18', 'C-18', 'X', ' ', '8:00 - 11:00', ' ', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Electrical', 'EL6', 'Thursday', 'CE', 'IDSP', 'PSOC', 'ELECTIVE-I', '*****', ' ', 'PS LAB (E6)', '(SS/SHM)', 'A-104', 'A-104', 'A-104', ' ', '*****', ' ', ' ', ' ', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '1:00 - 2:00', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Electrical', 'EL6', 'Friday', 'EMF', 'MCP', 'IDSP', '*****', 'CE', 'PSOC', 'X', 'X', 'C-18', 'C-18', 'C-18', '*****', 'C-18', 'C-18', 'X', 'X', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', 'X', 'X')";
		db.execSQL(sql);
		
		sql = "insert into Mech123 values('Civil', 'CV1', 'Monday', 'GTE-II(SST)', 'DCS-II(PCS)', 'EE-II(AN)', '*****', ' ', 'CAT II', ' ', 'X', 'C4', 'C4', 'C4', '*****', ' ', 'C4', ' ', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', 'RECESS', ' ', '2:00 - 5:00', ' ', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Civil', 'CV1', 'Tuesday', 'GTE-II(SST)', 'WRE-II(BD)', 'CPM(MIN)', '*****', 'DCS-II(PCS)', 'E&C(MCM)(C24)', 'E&C(MCM)(C24)', 'X', 'C6', 'C6', 'C6', '*****', 'C6', 'C6', 'C6', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', 'RECESS', '2:00 - 3:00', '3:00 - 4:00', '4:00 - 5:00', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Civil', 'CV1', 'Wednesday', ' ', 'WRD(JPP)', ' ', 'WRE-II(BD)(C4)', '*****', 'GTE-II(SST)', 'CPM(MIN)', 'EE-II(AN)', ' ', 'C4', ' ', 'C4', '*****', 'C4', 'C4', 'C4', ' ', '8:00 - 11:00', ' ', '11:00 - 12:00','RECESS', '2:00 - 3:00', '3:00 - 4:00', '4:00 - 5:00')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Civil', 'CV1', 'Thursday', ' ', 'GTE DESIGN(PNN)', ' ', 'WRE-II(BD)(C4)', '*****', 'CPM(MIN)', 'DCS-II(PCS)', 'EE-II(AN)', ' ', ' ', ' ', ' ', '*****', 'C4', 'C4', 'C4', ' ', '8:00 - 11:00', ' ', '11:00 - 12:00', 'RECESS', '2:00 - 3:00', '3:00 - 4:00', '4:00 - 5:00')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Civil', 'CV1', 'Friday', 'GTE-II(SST)', 'WRE-II(BD)', 'DCS-II(PCS)', '*****', 'SD RCC(PCS)', 'SD RCC(PCS)', 'SD RCC(PCS)', 'X', 'C4', 'C4', 'C4', '*****', 'C4', 'C4', 'C4', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', 'RECESS', '2:00 - 3:00', '3:00 - 4:00', '4:00 - 5:00', 'X')";
		db.execSQL(sql);
		
		sql = "insert into Mech123 values('Civil', 'CV2', 'Monday', 'GTE-II(LB)', 'EE-II(SS)', 'DCS-II(AKP)', '*****', ' ', 'WRD(AT)', ' ', 'X', 'C6', 'C6', 'C6', '*****', ' ', 'C6', ' ', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', 'RECESS', ' ', '2:00 - 5:00', ' ', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Civil', 'CV2', 'Tuesday', ' ', 'GTE DESIGN(AG)', ' ', 'WRE-II(BD)(C4)', '*****', 'CPM(MK)', 'GTE-II(LB)', 'EE-II(SS)', ' ', ' ', ' ', ' ', '*****', 'C4', 'C4', 'C4', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', 'RECESS', '2:00 - 3:00', '3:00 - 4:00', '4:00 - 5:00')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Civil', 'CV2', 'Wednesday', 'CPM(MK)', 'WRE-II(BD)', 'DCS-II(AKP)', '*****', ' ', 'CAT II', ' ', 'X', 'C6', 'C6', 'C6', '*****', ' ', 'C6', ' ', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', 'RECESS', ' ', '2:00 - 5:00', ' ', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Civil', 'CV2', 'Thursday', 'GTE-II(LB)', 'WRE-II(BD)', 'DCS-II(AKP)', '*****', 'GTE-II(LB)', 'E&C(SST)(C24)', 'E&C(SST)(C24)', 'X', 'C6', 'C6', 'C6', '*****', 'C6', 'C6', 'C6', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', 'RECESS', '2:00 - 3:00', '3:00 - 4:00', '4:00 - 5:00', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Civil', 'CV2', 'Friday', 'SD RCC(AKP)', 'SD RCC(AKP)', 'SD RCC(AKP)', 'WRE-II(BD)', '*****', 'CPM(MK)', 'DCS-II(AKP)', 'EE-II(SS)', 'C6', 'C6', 'C6', 'C6', '*****', 'C6', 'C6', 'C6', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', 'RECESS', '2:00 - 3:00', '3:00 - 4:00', '4:00 - 5:00')";
		db.execSQL(sql);
		
		sql = "insert into Mech123 values('Civil', 'CV3', 'Monday', 'E&C(KNM)(E105)', 'E&C(KNM)(E105)', 'CPM(MIN)(C1)', '*****', 'EE-II(SM)', 'WRE-II(SRP)', 'GTE-II(BP)', 'X', ' ', ' ', ' ', '*****', 'C1', 'C1', 'C1', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', 'RECESS', '2:00 - 3:00', '3:00 - 4:00', '4:00 - 5:00', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Civil', 'CV3', 'Tuesday', 'CPM(MIN)', 'DCS-II(JCS)', 'GTE-II(BP)', '*****', ' ', 'WRD(SRP)', ' ', 'X', 'C1', 'C1', 'C1', '*****', ' ', 'C1', ' ', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', 'RECESS', ' ', '2:00 - 5:00', ' ', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Civil', 'CV3', 'Wednesday', 'WRE-II(SRP)', ' ', 'GTE DESIGN(LB)', ' ', '*****', 'EE-II(SM)', 'DCS-II(JCS)', 'GTE-II(BP)', 'C7', ' ', ' ', ' ', '*****', 'C7', 'C7', 'C7', '8:00 - 9:00', ' ', '9:00 - 12:00', ' ', 'RECESS', '2:00 - 3:00', '3:00 - 4:00', '4:00 - 5:00')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Civil', 'CV3', 'Thursday', 'WRE-II(SRP)', 'DCS-II(JCS)', 'CPM(MIN)', '*****', ' ', 'SD RCC(MK)', ' ', 'X', 'C1', 'C1', 'C1', '*****', ' ', 'C1', ' ', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', 'RECESS', ' ', '2:00 - 5:00', ' ', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Civil', 'CV3', 'Friday', ' ', 'CAT II', ' ', 'EE-II(SM)', '*****', 'DCS-II(JCS)', 'WRE-II(SRP)', 'GTE-II(BP)', ' ', 'C1', ' ', 'C1', '*****', 'C1', 'C1', 'C1', ' ', '8:00 - 11:00', ' ', '11:00 - 12:00', 'RECESS', '2:00 - 3:00', '3:00 - 4:00', '4:00 - 5:00')";
		db.execSQL(sql);
		
		sql = "insert into Mech123 values('Civil', 'CV4', 'Monday', 'WRE-II', ' ', 'GTE DESIGN (MCM)', ' ', '*****', 'DCS-II (SB)', 'EE-II (EM)', 'CPM (MCM)',  'C7', ' ', ' ', ' ', '*****', 'C7', 'C7', 'C7', '8:00 - 9:00', ' ', '9:00 - 12:00', ' ', 'RECESS', '2:00 - 3:00', '3:00 - 4:00', '4:00 - 5:00')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Civil', 'CV4', 'Tuesday', 'CPM (MCM)', 'GTE-II (BGM)', 'WRE-II (KPS)', '*****', ' ', 'SD RCC (SB)', ' ', 'X', 'C2', 'C2', 'C2', '*****', ' ', 'C2', ' ', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', 'RECESS', ' ', '2:00 - 5:00', ' ')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Civil', 'CV4', 'Wednesday', 'GTE-II (BGM)', ' ', 'WRD (KPS)', ' ', '*****', 'WRE-II (KPS)', 'DCS-II (SB)', 'EE-II (EM)', 'C2', ' ', 'C2', ' ', '*****', 'C2', 'C2', 'C2', '8:00 - 9:00', ' ', '9:00 - 12:00', ' ', 'RECESS', '2:00 - 3:00', '3:00 - 4:00', '4:00 - 5:00')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Civil', 'CV4', 'Thursday', 'CPM (MCM)', 'GTE-II (BGM)', 'DCS-II (SB)', '*****', ' ', 'CAT-II', ' ', 'X', 'C2', 'C2', 'C2', '*****', ' ', 'C2', ' ', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', 'RECESS', ' ', '2:00 - 5:00', ' ', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Civil', 'CV4', 'Friday', 'EE-II (EM)', 'WRE-II (KPS)', 'GTE-II (BGM)', '*****', 'DCS-II', 'E&C (SST) (E105)', 'E&C (SST) (E105)', 'X', 'C2', 'C2', 'C2', '*****', 'C2', 'C2', 'C2', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', 'RECESS', '2:00 - 3:00', '3:00 - 4:00', '4:00 - 5:00', 'X')";
		db.execSQL(sql);
		
		sql = "insert into Mech123 values('Civil', 'CV5', 'Monday', 'DCS-II (AS)', 'GTE-II (AJ)', 'WRE-II (JPP)', '*****', ' ', 'WRD (JPP)', ' ', 'X', 'C2', 'C2', 'C2', 'RECESS', ' ', 'C2', ' ', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', 'RECESS', ' ', '2:00 - 5:00', ' ', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Civil', 'CV5', 'Tuesday', ' ', 'CAT-II', ' ', '*****', 'EE-II (SM)', 'DCS-II (AS)', 'CPM (VS)', 'X', ' ', 'C3', ' ', '*****', 'C3', 'C3', 'C3', 'X', ' ', '8:00 - 11:00', ' ', 'RECESS', '2:00 - 3:00', '3:00 - 4:00', '4:00 - 5:00', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Civil', 'CV5', 'Wednesday', 'CPM (VS)', 'WRE-II (JPP)', 'EE-II (SM)', '*****', 'GTE-II (AJ)', 'E&C (AS)', 'E&C (AS)', 'X', 'C3', 'C3', 'C3', '*****', 'C3', 'C3', 'C3', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', 'RECESS', '2:00 - 3:00', '3:00 - 4:00', '4:00 - 5:00', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Civil', 'CV5', 'Thursday', ' ', 'GTE DESIGN (AJ)', '(COMPUTATIONAL LAB)', 'WRE-II (JPP)', '*****', 'DCS-II (AS)', 'GTE-II (AJ)', 'EE-II (SM)', ' ', ' ', ' ', 'C1', '*****', 'C7', 'C7', 'C7', ' ', '8:00 - 11:00', ' ', '11:00 - 12:00', 'RECESS', '2:00 - 3:00', '3:00 - 4:00', '4:00 - 5:00')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Civil', 'CV5', 'Friday', ' ', 'SD RCC (NCM)', ' ', 'WRE-II (JPP)', '*****', 'DCS-II (AS)', 'GTE-II (AJ)', 'CPM (VS)', ' ', 'C3', ' ', 'C3', '*****', 'C3', 'C3', 'C3', ' ', '8:00 - 11:00', ' ', '11:00 - 12:00', 'RECESS', '2:00 - 3:00', '3:00 - 4:00', '4:00 - 5:00')";
		db.execSQL(sql);
		
		sql = "insert into Mech123 values('Civil', 'CV6', 'Monday', 'CPM (AT)', 'DCS-II (MK)', 'WRE-II (VS)', '*****', 'GTE-II (MCM)', 'E&C (AS)', 'E&C (AS)', 'X', 'C3', 'C3', 'C3', '*****', 'C3', 'C3', 'C3', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', 'RECESS', '2:00 - 3:00', '3:00 - 4:00', '4:00 - 5:00', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Civil', 'CV6', 'Tuesday', 'WRE-II (VS)', 'GTE-II (MCM)', 'EE-II (NRP)', '*****', ' ', 'WRD (VS)', ' ', 'X', 'C7', 'C7', 'C7', '*****', ' ', 'C7', ' ', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', 'RECESS', ' ', '2:00 - 5:00', ' ', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Civil', 'CV6', 'Wednesday', 'EE-II (NRP)', 'DCS-II (MK)', 'GTE-II (MCM)', '*****', ' ', 'SD RCC (MK)', ' ', 'X', 'C1', 'C1', 'C1', '*****', ' ', 'C1', ' ', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', 'RECESS', ' ', '2:00 - 5:00', ' ', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Civil', 'CV6', 'Thursday', ' ', 'CAT-II', ' ', 'WRE-II (VS)', '*****', 'EE-II (NRP)', 'DCS-II (MK)', 'GTE-II (MCM)', ' ', 'C3', ' ', 'C3', '*****', 'C3', 'C3', 'C3', ' ', '8:00 - 11:00', ' ', '11:00 - 12:00', 'RECESS', '2:00 - 3:00', '3:00 - 4:00', '4:00 - 5:00')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Civil', 'CV6', 'Friday', ' ', 'GTE DESIGN (MCM)', '(COMPUTATIONAL LAB)', 'CPM (AT)', '*****', 'WRE-II (VS)', 'CPM (AT)', 'DCS-II (MK)', ' ', ' ', ' ', 'C7', '*****', 'C9', 'C9', 'C9', ' ', '8:00 - 11:00', ' ', '11:00 - 12:00', 'RECESS', '2:00 - 3:00', '3:00 - 4:00', '4:00 - 5:00')";
		db.execSQL(sql);
		
		sql = "insert into Mech123 values('ETC', 'ETC1', 'Monday', 'DSP', 'VLSID', 'AWP', ' ', 'VLSI LAB', ' ', 'X', 'X', 'CL-13', 'CL-13', 'CL-13', ' ', ' ', ' ', 'X', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', ' ', '11:00 - 2:00', ' ', 'X', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('ETC', 'ETC1', 'Tuesday', 'DCN', 'AWP', 'AMP', ' ', 'ED&S LAB', ' ', 'X', 'X', 'CL-13', 'CL-13', 'CL-13', ' ', ' ', ' ', 'X', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', ' ', '11:00 - 2:00', ' ', 'X', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('ETC', 'ETC1', 'Wednesday', 'DSP', 'DCN', 'AMP', ' ', 'DSP LAB', ' ', 'X', 'X', 'CL-13', 'CL-13', 'CL-13', ' ', ' ', ' ', 'X', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', ' ', '11:00 - 2:00', ' ', 'X', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('ETC', 'ETC1', 'Thursday', 'DCN', 'VLSID', 'AMP', 'AWP', 'DSP', 'X', 'X', 'X', 'CL-13', 'CL-13', 'CL-13', 'CL-13', 'CL-13', 'X', 'X', 'X', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'X', 'X', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('ETC', 'ETC1', 'Friday', 'AWP', 'DSP', 'VLSID', '*****', 'DCN', 'CAT', 'CAT', 'X', 'CL-08', 'CL-08', 'CL-08', '*****', 'CL-14', 'CL-14', 'CL-14', 'X', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00', 'X')";
		db.execSQL(sql);
		
		sql = "insert into Mech123 values('ETC', 'ETC2', 'Monday', 'VLSID', 'VLSID', 'DCN', ' ', 'DSP LAB', ' ', 'X', 'X', 'CL-12', 'CL-12', 'CL-12', ' ', ' ', ' ', 'X', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', ' ', '11:00 - 2:00', ' ', 'X', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('ETC', 'ETC2', 'Tuesday', 'AMP', 'AMP', 'AWP', '*****', 'DSP', 'VLSID', 'DCN', 'X', 'CL-03', 'CL-03', 'CL-03', '*****', 'CL-03', 'CL-03', 'CL-03', 'X', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('ETC', 'ETC2', 'Wednesday', 'DCN', 'DSP', 'AWP', '*****', 'CAT', 'CAT', 'X', 'X', 'CL-12', 'CL-12', 'CL-12', '*****', 'CL-12', 'CL-12', 'X', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', 'X', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('ETC', 'ETC2', 'Thursday', 'DSP', 'AWP', 'AMP', ' ', 'VLSI LAB', ' ', 'X', 'X', 'CL-03', 'CL-03', 'CL-03', ' ', ' ', ' ', 'X', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', ' ', '11:00 - 2:00', ' ', 'X', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('ETC', 'ETC2', 'Friday', 'DCN', 'DSP', 'AWP', ' ', 'ED&S LAB', ' ', 'X', 'X', 'CL-12', 'CL-12', 'CL-12', ' ', ' ', ' ', 'X', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', ' ', '11:00 - 2:00', ' ', 'X', 'X')";
		db.execSQL(sql);
		
		sql = "insert into Mech123 values('ETC', 'ETC3', 'Monday', 'AMP', 'DSP', 'AWP', ' ', 'ED&S LAB', ' ', 'X', 'X', 'CL-14', 'CL-14', 'CL-14', ' ', ' ', ' ', 'X', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00',' ', '11:00 - 2:00', ' ', 'X', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('ETC', 'ETC3', 'Tuesday', 'AWP', 'DSP', 'AMP', ' ', 'DSP LAB', ' ', 'X', 'X', 'CL-12', 'CL-12', 'CL-12', ' ', ' ', ' ', 'X', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', ' ', '11:00 - 2:00', ' ', 'X', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('ETC', 'ETC3', 'Wednesday', 'AWP', 'CAT', 'CAT', '*****', 'DCN', 'DCN', 'VLSID', 'X', 'CL-14', 'CL-14', 'CL-14', '*****', 'CL-14', 'CL-14', 'CL-14', 'X', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('ETC', 'ETC3', 'Thursday', 'DCN', 'DSP', '*****', ' ', 'VLSI LAB', ' ', 'X', 'X', 'CLA-11', 'CLA-11', '*****', ' ', ' ', ' ', 'X', 'X', '11:00 - 12:00', '12:00 - 1:00', 'RECESS', ' ', '3:00 - 6:00', ' ', 'X', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('ETC', 'ETC3', 'Friday', 'AWP', 'DCN', 'DSP', '*****', 'VLSID', 'VLSID', 'AMP', 'X', 'CL-12', 'CL-12', 'CL-12', '*****', 'CL-12', 'CL-12', 'CL-12', 'X', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00', 'X')";
		db.execSQL(sql);
		
		sql = "insert into Mech123 values('ETC', 'ETC4', 'Monday', 'DSP', 'VLSID', 'AMP', '*****', 'AWP', 'DCN', 'DCN', 'X', 'CL-13', 'CL-13', 'CL-13', '*****', 'CL-13', 'CL-13', 'CL-13', 'X', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('ETC', 'ETC4', 'Tuesday', 'VLSID', 'DSP', 'DCN', '*****', ' ', 'DSP LAB', ' ', 'X', 'CLA-12', 'CLA-12', 'CLA-12', '*****', ' ', ' ', ' ', 'X', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'RECESS', ' ', '3:00 - 6:00', ' ', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('ETC', 'ETC4', 'Wednesday', 'AMP', 'DSP', 'AWP', '*****', ' ', 'VLSI LAB', ' ', 'X', 'CLA-11', 'CLA-11', 'CLA-11', '*****', ' ', ' ', ' ', 'X', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'RECESS', ' ', '3:00 - 6:00', ' ', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('ETC', 'ETC4', 'Thursday', 'AWP', 'DSP', 'VLSID', ' ', 'ED&S LAB', ' ', 'X', 'X', 'CL-12', 'CL-12', 'CL-12', ' ', ' ', ' ', 'X', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', ' ', '11:00 - 2:00', ' ', 'X', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('ETC', 'ETC4', 'Friday', 'DCN', 'AWP', '*****', 'AMP', 'CAT', 'CAT', 'X', 'X', 'CL-13', 'CL-13', '*****', 'CL-13', 'CL-13', 'CL-13', 'X', 'X', '11:00 - 12:00', '12:00 - 1:00', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00', 'X', 'X')";
		db.execSQL(sql);
		
		sql = "insert into Mech123 values('ETC', 'ETC5', 'Monday', 'AWP', 'DCN', 'DCN', '*****', 'VLSID', 'DSP', 'DSP', 'X', 'CL-14', 'CL-14', 'CL-14', '*****', 'CL-14', 'CL-14', 'CL-14', 'X', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('ETC', 'ETC5', 'Tuesday', ' ', 'VLSI LAB', ' ', '*****', 'VLSID', 'CAT', 'CAT', 'X', ' ', ' ', ' ', '*****', 'CLA-17', 'CLA-17', 'CLA-17', 'X', ' ', '11:00 - 2:00', ' ', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('ETC', 'ETC5', 'Wednesday', ' ', 'ED&S LAB', ' ', '*****', 'AWP', 'VLSID', 'AMP', 'X', ' ', ' ', ' ', '*****', 'CL-13', 'CL-13', 'CL-13', 'X', ' ', '11:00 - 2:00', ' ', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('ETC', 'ETC5', 'Thursday', 'AWP', 'DCN', 'DCN', '*****', 'AMP', 'DSP', 'X', 'X', 'CLA-13', 'CLA-13', 'CLA-13', '*****', 'CLA-13', 'CLA-13', 'X', 'X', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', 'X', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('ETC', 'ETC5', 'Friday', 'DSP', 'AWP', 'AMP', ' ', 'DSP LAB', ' ', 'X', 'X', 'CL-13', 'CL-13', 'CL-13', ' ', ' ', ' ', 'X', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', ' ', '11:00 - 2:00', ' ', 'X', 'X')";
		db.execSQL(sql);
		
		sql = "insert into Mech123 values('ETC', 'ETC6', 'Monday', 'VLSID', 'AMP', 'AWP', '*****', 'DSP', 'DCN', 'DCN', 'X', 'CLA-11', 'CLA-11', 'CLA-11', '*****', 'CLA-11', 'CLA-11', 'CLA-11', 'X', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('ETC', 'ETC6', 'Tuesday', 'DSP', 'VLSID', 'AWP', '*****', ' ', 'ED&S LAB', ' ', 'X', 'CLA-17', 'CLA-17', 'CLA-17', '*****', ' ', ' ', ' ', 'X', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'RECESS', ' ', '3:00 - 6:00', ' ', ' ')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('ETC', 'ETC6', 'Wednesday', 'DCN', 'DSP', 'VLSID', '*****', 'AWP', 'AMP', 'X', 'X', 'CLA-13', 'CLA-13', 'CLA-13', '*****', 'CLA-13', 'CLA-13', 'X', 'X', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', 'X', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('ETC', 'ETC6', 'Thursday', ' ', 'DSP LAB', ' ', '*****', 'AMP', 'CAT', 'CAT', 'X', ' ', ' ', ' ', '*****', 'CL-14', 'CL-14', 'CL-14', 'X', ' ', '11:00 - 2:00', ' ', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('ETC', 'ETC6', 'Friday', 'AWP', 'DSP', 'DCN', ' ', 'VLSI LAB', ' ', 'X', 'X', 'CLA-11', 'CLA-11', 'CLA-11', ' ', ' ', ' ', 'X', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', ' ', '11:00 - 2:00', ' ', 'X', 'X')";
		db.execSQL(sql);
		
		sql = "insert into Mech123 values('ETC', 'ETC7', 'Monday', 'DSP', 'VLSID', 'AWP', '*****', ' ', 'DSP LAB', ' ', 'X', 'CLA-17', 'CLA-17', 'CLA-17', '*****', ' ', ' ', ' ', 'X', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'RECESS', ' ', '3:00 - 6:00', ' ', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('ETC', 'ETC7', 'Tuesday', 'AWP', 'DCN', 'DSP', 'AMP', 'CAT', 'CAT', 'X', 'X', 'CLA-11', 'CLA-11', 'CLA-11', 'CLA-11', 'CLA-11', 'CLA-11', 'X', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'X', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('ETC', 'ETC7', 'Wednesday', 'DCN', 'AMP', 'VLSID', ' ', 'VLSI LAB', ' ', 'X', 'X', 'CLA-11', 'CLA-11', 'CLA-11', ' ', ' ', ' ', 'X', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', ' ', '11:00 - 2:00', ' ', 'X', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('ETC', 'ETC7', 'Thursday', 'DCN', 'AWP', 'DSP', '*****', ' ', 'ED&S LAB', ' ', 'X', 'CLA-14', 'CLA-14', 'CLA-14', '*****', ' ', ' ', ' ', 'X', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'RECESS', ' ', '3:00 - 6:00', ' ', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('ETC', 'ETC7', 'Friday', 'AMP', 'DCN', 'AWP', 'VLSID', 'DSP', 'X', 'X', 'X', 'CLA-12', 'CLA-12', 'CLA-12', 'CLA-12', 'CLA-12', 'X', 'X', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', '12:00 - 1:00', 'X', 'X', 'X')";
		db.execSQL(sql);
		
		sql = "insert into Mech123 values('EEE', 'EEE1', 'Monday', 'CE', 'ACS', 'PE', 'PSOC', 'MCP', 'X', 'X', 'X', 'CLA-15', 'CLA-15', 'CLA-15', 'CLA-15', 'CLA-15', 'X', 'X', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', '12:00 - 1:00', 'X', 'X', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('EEE', 'EEE1', 'Tuesday', 'PE', 'MCP', 'CE', ' ', 'PE LAB', ' ', 'X', 'X', 'CLA-12', 'CLA-12', 'CLA-12', ' ', ' ', ' ', 'X', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', ' ', '11:00 - 2:00', ' ', 'X', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('EEE', 'EEE1', 'Wednesday', 'ACS', 'PE', 'PSOC', 'CE', 'CAT', 'CAT', 'X', 'X', 'CLA-12', 'CLA-12', 'CLA-12', 'CLA-12', 'CLA-12', 'CLA-12', 'X', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'X', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('EEE', 'EEE1', 'Thursday', 'CE', 'PSOC', 'ACS', ' ', 'PS LAB', ' ', 'X', 'X', 'CL-11', 'CL-11', 'CL-11', ' ', ' ', ' ', 'X', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', ' ', '11:00 - 2:00', ' ', 'X', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('EEE', 'EEE1', 'Friday', 'PSOC', 'MCP', 'PE', ' ', 'CE LAB', ' ', 'X', 'X', 'CLA-13', 'CLA-13', 'CLA-13', ' ', ' ', ' ', 'X', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', ' ', '11:00 - 2:00', ' ', 'X', 'X')";
		db.execSQL(sql);
		
		sql = "insert into Mech123 values('EEE', 'EEE2', 'Monday', 'PSOC', 'PE', 'ACS', ' ', 'PE LAB', ' ', 'X', 'X', 'CL-16', 'CL-16', 'CL-16', ' ', ' ', ' ', 'X', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', ' ', '11:00 - 2:00', ' ', 'X', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('EEE', 'EEE2', 'Tuesday', 'MCP', 'CE', 'PE', '*****', ' ', 'PS LAB', ' ', 'X', 'CL-14', 'CL-14', 'CL-14', '*****', ' ', ' ', ' ', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', 'RECESS', ' ', '3:00 - 6:00', ' ', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('EEE', 'EEE2', 'Wednesday', 'ACS', 'CE', ';PSOC', ' ', 'CE LAB', ' ', 'X', 'X', 'CL-14', 'CL-14', 'CL-14', ' ', ' ', ' ', 'X', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', ' ', '11:00 - 2:00', ' ', 'X', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('EEE', 'EEE2', 'Thursday', 'MCP', 'ACS', 'CE', 'PSOC', 'PE', 'X', 'X', 'X', 'CL-14', 'CL-14', 'CL-14', 'CL-14', 'CL-14', 'X', 'X', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', '12:00 - 1:00', 'X', 'X', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('EEE', 'EEE2', 'Friday', 'PSOC', 'PE', 'CE', 'MCP', 'CAT', 'CAT', 'X', 'X', 'CL-14', 'CL-14', 'CL-14', 'CL-14', 'CL-14', 'CL-14', 'X', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'X', 'X')";
		db.execSQL(sql);
		
		sql = "insert into Mech123 values('EEE', 'EEE3', 'Monday', 'MCP', 'CE', 'ACS', ' ', 'CE LAB', ' ', 'X', 'X', 'CLA-11', 'CLA-11', 'CLA-11', ' ', ' ', ' ', 'X', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', ' ', '11:00 - 2:00', ' ', 'X', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('EEE', 'EEE3', 'Tuesday', 'PSOC', 'PE', 'MCP', 'CE', 'CAT', 'CAT', 'X', 'X', 'CLA-13', 'CLA-13', 'CLA-13', 'CLA-13', 'CLA-13', 'CLA-13', 'X', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'X', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('EEE', 'EEE3', 'Wednesday', 'PE', 'PSOC', 'ACS', ' ', 'PE LAB', ' ', 'X', 'X', 'CLA-13', 'CLA-13', 'CLA-13', ' ', ' ', ' ', 'X', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', ' ', '11:00 - 2:00', ' ', 'X', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('EEE', 'EEE3', 'Thursday', ' ', 'PS LAB', ' ', 'PE', 'CE', 'PSOC', 'X', 'X', ' ', ' ', ' ', 'CLA-15', 'CLA-15', 'CLA-15', 'X', 'X', ' ', '8:00 - 11:00', ' ', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'X', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('EEE', 'EEE3', 'Friday', 'ACS', 'MCP', 'CE', 'PSOC', 'PE', 'X', 'X', 'X', 'CLA-14', 'CLA-14', 'CLA-14', 'CLA-14', 'CLA-14', 'X', 'X', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', '12:00 - 1:00', 'X', 'X', 'X')";
		db.execSQL(sql);
		
		sql = "insert into Mech123 values('EEE', 'EEE4', 'Monday', 'CE', 'MCP', 'ACS', 'PE', 'PSOC', 'X', 'X', 'X', 'CLA-12', 'CLA-12', 'CLA-12', 'CLA-12', 'CLA-12', 'X', 'X', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', '12:00 - 1:00', 'X', 'X', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('EEE', 'EEE4', 'Tuesday', 'PE', 'MCP', 'PSOC', '*****', ' ', 'CE LAB', ' ', 'X', 'CL-14', 'CL-14', 'CL-14', '*****', ' ', ' ', ' ', 'X', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'RECESS', ' ', '3:00 - 6:00', ' ', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('EEE', 'EEE4', 'Wednesday', 'CE', 'PE', 'ACS', '*****', ' ', 'PS LAB', ' ', 'X', 'CLA-15', 'CLA-15', 'CLA-15', '*****', ' ', ' ', ' ', 'X', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'RECESS', ' ', '3:00 - 6:00', ' ', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('EEE', 'EEE4', 'Thursday', 'MCP', 'CAT', 'CAT', '*****', 'PSOC', 'CE', 'ACS', 'X', 'CLA-16', 'CLA-16', 'CLA-16', '*****', 'CLA-16', 'CLA-16', 'CLA-16', 'X', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('EEE', 'EEE4', 'Friday', 'PE', 'CE', 'PSOC', ' ', 'PE LAB', ' ', 'X', 'X', 'CLA-15', 'CLA-15', 'CLA-15', ' ', ' ', ' ', 'X', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', ' ', '11:00 - 2:00', ' ', 'X', 'X')";
		db.execSQL(sql);
		
		sql = "insert into Mech123 values('EEE', 'EEE5', 'Monday', 'MCP', 'ACS', 'CE', 'PSOC', 'PE', 'X', 'X', 'X', 'CLA-13', 'CLA-13', 'CLA-13', 'CLA-13', 'CLA-13', 'X', 'X', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', '12:00 - 1:00', 'X', 'X', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('EEE', 'EEE5', 'Tuesday', 'ACS', 'MCP', 'PSOC', ' ', 'CE LAB', ' ', 'X', 'X', 'CLA-14', 'CLA-14', 'CLA-14', ' ', ' ', ' ', 'X', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', ' ', '11:00 - 2:00', ' ', 'X', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('EEE', 'EEE5', 'Wednesday', ' ', 'PS LAB', ' ', 'PE', 'MCP', 'CE(SKG)', 'X', 'X', ' ', ' ', ' ', 'CLA-14', 'CLA-14', 'CLA-14', 'X', 'X', ' ', '8:00 - 11:00', ' ', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'X', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('EEE', 'EEE5', 'Thursday', 'PSOC', 'ACS', 'PE', '*****', ' ', 'PE LAB', ' ', 'X', 'CLA-17', 'CLA-17', 'CLA-17', '*****', ' ', ' ', ' ', 'X', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'RECESS', ' ', '3:00 - 6:00', ' ', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('EEE', 'EEE5', 'Friday', 'CE', 'CE', 'PSOC', '*****', 'PE', 'CAT', 'CAT', 'X', 'CLA-15', 'CLA-15', 'CLA-15', '*****', 'CLA-15', 'CLA-15', 'CLA-15', 'X', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00', 'X')";
		db.execSQL(sql);
		
		sql = "insert into Mech123 values('EEE', 'EEE6', 'Monday', 'PSOC', 'MCP', 'ACS', '*****', ' ', 'PS LAB', ' ', 'X', 'CLA-14', 'CLA-14', 'CLA-14', '*****', ' ', ' ', ' ', 'X', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'RECESS', ' ', '3:00 - 6:00', ' ', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('EEE', 'EEE6', 'Tuesday', 'MCP', 'ACS', 'PSOC', 'CE', 'PE', 'X', 'X', 'X', 'CLA-15', 'CLA-15', 'CLA-15', 'CLA-15', 'CLA-15', 'X', 'X', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', '12:00 - 1:00', 'X', 'X', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('EEE', 'EEE6', 'Wednesday', 'CE', 'ACS', 'MCP', '*****', 'PE', 'CAT', 'CAT', 'X', 'CLA-16', 'CLA-16', 'CLA-16', '*****', 'CLA-16', 'CLA-16', 'CLA-16', 'X', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('EEE', 'EEE6', 'Thursday', 'PE', 'PSOC', 'CE', ' ', 'CE LAB', ' ', 'X', 'X', 'CLA-11', 'CLA-11', 'CLA-11', ' ', ' ', ' ', 'X', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', ' ', '11:00 - 2:00', ' ', 'X', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('EEE', 'EEE6', 'Friday', ' ', 'PE LAB', ' ', 'PSOC', 'CE', 'PE', 'X', 'X', ' ', ' ', ' ', 'CLA-16', 'CLA-16', 'CLA-16', 'X', 'X', ' ', '8:00 - 11:00', ' ', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'X', 'X')";
		db.execSQL(sql);
		
		sql = "insert into Mech123 values('E&I', 'E&I', 'Monday', 'DSP', 'INST-II', 'INST-II', '*****', ' ', 'I&DS LAB', ' ', 'X', 'CLA-16', 'CLA-16', 'CLA-16', '*****', ' ', ' ', ' ', 'X', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('E&I', 'E&I', 'Tuesday', 'INST-II', 'PC-I', 'PE', 'DSP', 'CAT', 'CAT', 'X', 'X', 'CLA-16', 'CLA-16', 'CLA-16', 'CLA-16', 'CLA-16', 'CLA-16', 'X', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'X', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('E&I', 'E&I', 'Wednesday', 'DSP', 'PE', 'PC-I', '*****', ' ', 'DSP LAB', ' ', 'X', 'CLA-17', 'CLA-17', 'CLA-17', '*****', ' ', ' ', ' ', 'X', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('E&I', 'E&I', 'Thursday', 'INST-II', 'EECO.', 'EECO.', 'PC-I', 'PC-I', 'PE', 'X', 'X', 'CLA-12', 'CLA-12', 'CLA-12', 'CLA-12', 'CLA-12', 'CLA-12', 'X', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'X', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('E&I', 'E&I', 'Friday', 'EECO.', 'DSP', 'PE', ' ', 'I LAB', ' ', 'X', 'X', 'CL-16', 'CL-16', 'CL-16', ' ', ' ', ' ', 'X', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'X', 'X')";
		db.execSQL(sql);
		
		sql = "insert into Mech123 values('Computer Science', 'CS1', 'Monday', ' ', 'SE LAB', ' ', '*****', 'CD', 'OOSD', 'HPCA', 'ECO', ' ', 'DL-9', ' ', '*****', 'C1', 'C1', 'C1', 'C1', ' ', '8:00 - 11:00', ' ', 'RECESS', '2:00 - 3:00', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Computer Science', 'CS1', 'Tuesday', ' ', 'CD LAB', ' ', '*****', 'OOSD', 'CD', 'CG', 'X', ' ', 'DL-1', ' ', '*****', 'C3', 'C3', 'C3', 'X', ' ', '11:00 - 2:00', ' ', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Computer Science', 'CS1', 'Wednesday', 'CG', 'CD', 'OOSD', '******', 'ECO', 'HPCA', 'X', 'X', 'C10', 'C10', 'C10', '*****', 'C4', 'C4', 'X', 'X', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', 'X', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Computer Science', 'CS1', 'Thursday', ' ', 'CG LAB', ' ', '*****', 'OOSD', 'HPCA', 'CD', 'X', ' ', 'DL-4', ' ', '*****', 'C7', 'C7', 'C7', 'X', ' ', '8:00 - 11:00', ' ', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Computer Science', 'CS1', 'Friday', 'CG', 'ECO', 'HPCA', '*****', ' ', 'CAT', ' ', 'X', 'C12', 'C12', 'C12', '*****', ' ', 'DL-1', ' ', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00', 'X')";
		db.execSQL(sql);
		
		sql = "insert into Mech123 values('Computer Science', 'CS2', 'Monday', 'OOSD', 'CD', 'CG', '*****', ' ', 'CAT', ' ', 'X', 'C9', 'C9', 'C9', '*****', ' ', 'DL-1', ' ', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', 'RECESS', ' ', '3:00 - 6:00', ' ', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Computer Science', 'CS2', 'Tuesday', 'HPCA', 'CG', 'CD', '*****', 'ECO', 'OOSD', 'X', 'X', 'C13', 'C13', 'C13', '*****', 'C7', 'C7', 'X', 'X', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', 'X', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Computer Science', 'CS2', 'Wednesday', ' ', 'CD LAB', ' ', '*****', 'HPCA', 'CG', 'CD', 'ECO', ' ', 'DL-3', ' ', '*****', 'C7', 'C7', 'C7', 'C7', ' ', '8:00 - 11:00', ' ', 'RECESS', '2:00 - 3:00', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Computer Science', 'CS2', 'Thursday', ' ', 'SE LAB', ' ', '*****', 'ECO', 'OOSD', 'HPCA', 'X', ' ', 'DL-9', ' ', '*****', 'C8', 'C8', 'C8', 'X', ' ', '11:00 - 2:00', ' ', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Computer Science', 'CS2', 'Friday', ' ', 'CG LAB', ' ', '*****', 'HPCA', 'CD', 'OOSD', 'X', ' ', 'DL-4', ' ', '*****', 'C5', 'C5', 'C5', 'X', ' ', '8:00 - 11:00', ' ', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00', 'X')";
		db.execSQL(sql);
		
		sql = "insert into Mech123 values('Computer Science', 'CS3', 'Monday', 'CD', 'CG', 'OOSD', '*****', ' ', 'CAT', ' ', 'X', 'C13', 'C13', 'C13', '*****', ' ', 'DL-2', ' ', 'X', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'RECESS', ' ', '3:00 - 6:00', ' ', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Computer Science', 'CS3', 'Tuesday', ' ', 'CD LAB', ' ', '*****', 'CG', 'CD', 'HPCA', 'OOSD', ' ', 'DL-1', ' ', '*****', 'C8', 'C8', 'C8', 'C8', ' ', '8:00 - 11:00', ' ', 'RECESS', '2:00 - 3:00', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Computer Science', 'CS3', 'Wednesday', 'OOSD', 'ECO', 'HPCA', '*****', ' ', 'SE LAB', ' ', 'X', 'C12', 'C12', 'C12', '*****', ' ', 'DL-9', ' ', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', 'RECESS', ' ', '3:00 - 6:00', ' ', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Computer Science', 'CS3', 'Thursday', 'OOSD', 'ECO', 'CD', '*****', ' ', 'CG LAB', ' ', 'X', 'C12', 'C12', 'C12', '*****', ' ', 'DL-7', ' ', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', 'RECESS', ' ', '3:00 - 6:00', ' ', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Computer Science', 'CS3', 'Friday', 'X', 'X', 'X', 'X', 'CD', 'ECO', 'CG', 'HPCA', 'X', 'X', 'X', 'X', 'C7', 'C7', 'C7', 'C7', 'X', 'X', 'X', 'X', '2:00 - 3:00', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00')";
		db.execSQL(sql);
		
		sql = "insert into Mech123 values('Computer Science', 'CS4', 'Monday', 'OOSD', 'CG', 'CD', ' ', 'CG LAB', ' ', 'X', 'X', 'C13', 'C13', 'C13', ' ', 'DL-6', ' ', 'X', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', ' ', '11:00 - 2:00', ' ', 'X', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Computer Science', 'CS4', 'Tuesday', ' ', 'SE LAB', ' ', '*****', 'ECO', 'CG', 'HPCA', 'X', ' ', 'DL-2', ' ', '*****', 'C9', 'C9', 'C9', 'X', ' ', '11:00 - 2:00', ' ', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Computer Science', 'CS4', 'Wednesday', 'HPCA', 'OOSD', 'CD', '*****', 'ECO', 'CG', 'X', 'X', 'C18', 'C18', 'C18', '*****', 'C8', 'C8', 'X', 'X', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', 'X', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Computer Science', 'CS4', 'Thursday', 'HPCA', 'CD', 'OOSD', '*****', ' ', 'CAT', ' ', 'X', 'C14', 'C14', 'C14', '*****', ' ', 'DL-2', ' ', 'X', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'RECESS', ' ', '3:00 - 6:00', ' ', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Computer Science', 'CS4', 'Friday', ' ', 'CD LAB', ' ', '*****', 'ECO', 'CD', 'OOSD', 'HPCA', ' ', 'DL-9', ' ', '*****', 'C8', 'C8', 'C8', 'C8', ' ', '8:00 - 11:00', ' ', 'RECESS', '2:00 - 3:00', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00')";
		db.execSQL(sql);
		
		sql = "insert into Mech123 values('Computer Science', 'CS5', 'Monday', 'OOSD', 'ECO', 'CD', '*****', 'HPCA', 'CG', 'X', 'X', 'C4', 'C4', 'C4', '*****', 'C4', 'C4', 'X', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', 'RECESS', '12:00 - 1:00', '1:00 - 2:00', 'X', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Computer Science', 'CS5', 'Tuesday', ' ', 'CG LAB', ' ', '*****', 'ECO', 'HPCA', 'CG', 'CD', ' ', 'DL-9', ' ', '*****', 'C10', 'C10', 'C10', 'C10', ' ', '8:00 - 11:00', ' ', 'RECESS', '2:00 - 3:00', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Computer Science', 'CS5', 'Wednesday', 'OOSD', 'CG', 'CD', '*****', ' ', 'SE LAB', ' ', 'X', 'C13', 'C13', 'C13', '*****', ' ', 'DL-3', ' ', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', 'RECESS', ' ', '3:00 - 6:00', ' ', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Computer Science', 'CS5', 'Thursday', ' ', 'CD LAB', ' ', '*****', 'HPCA', 'OOSD', 'CD', 'X', ' ', 'DL-6', ' ', '*****', 'C9', 'C9', 'C9', 'X', ' ', '11:00 - 2:00', ' ', '*****', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Computer Science', 'CS5', 'Friday', 'HPCA', 'OOSD', 'ECO', '*****', ' ', 'CAT', ' ', 'X', 'C13', 'C13', 'C13', '*****', ' ', 'DL-2', ' ', 'X', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00', 'X')";
		db.execSQL(sql);
		
		sql = "insert into Mech123 values('Computer Science', 'CS6', 'Monday', 'CG', 'CD', 'ECO', ' ', 'SE LAB', ' ', 'X', 'X', 'C16', 'C16', 'C16', ' ', 'DL-9', ' ', 'X', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', ' ', '11:00 - 2:00', ' ', 'X', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Computer Science', 'CS6', 'Tuesday', ' ', 'CD LAB', ' ', '*****', 'ECO', 'HPCA', 'CD', 'OOSD', ' ', 'DL-2', ' ', '*****', 'C11', 'C11', 'C11', 'C11', ' ', '8:00 - 11:00', ' ', 'RECESS', '2:00 - 3:00', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Computer Science', 'CS6', 'Wednesday', 'CD', 'HPCA', 'OOSD', '*****', ' ', 'CG LAB', ' ', 'X', 'C14', 'C14', 'C14', '*****', ' ', 'DL-7', ' ', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Computer Science', 'CS6', 'Thursday','OOSD', 'CG', 'HPCA', '*****', ' ', 'CAT', ' ', 'X', 'C16', 'C16', 'C16', '*****', ' ', 'DL-1', ' ', 'X', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Computer Science', 'CS6', 'Friday', 'HPCA', 'OOSD', 'CD', '*****', 'ECO', 'CG', 'X', 'X', 'C13', 'C13', 'C13', '*****', 'C12', 'C12', 'X', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', 'X', 'X')";
		db.execSQL(sql);
		
		sql = "insert into Mech123 values('Computer Science', 'CS7', 'Monday', 'ECO', 'CG', 'CD', '*****', 'HPCA', 'OOSD', 'X', 'X', 'C17', 'C17', 'C17', '*****', 'C8', 'C8', 'X', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', 'X', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Computer Science', 'CS7', 'Tuesday', ' ', 'SE LAB', ' ', '*****', 'CG', 'CD', 'HPCA', 'X', ' ', 'DL-9', ' ', '*****', 'C12', 'C12', 'C12', 'X', ' ', '11:00 - 2:00', ' ', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Computer Science', 'CS7', 'Wednesday', 'OOSD', 'CG', 'HPCA', '*****', ' ', 'CAT', ' ', 'X', 'C16', 'C16', 'C16', '*****', ' ', 'DL-1', ' ', 'X', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Computer Science', 'CS7', 'Thursday', ' ', 'CG LAB', ' ', 'CD', 'OOSD', 'ECO', 'X', 'X', ' ', 'DL-9', ' ', 'C17', 'C17', 'C17', 'X', 'X', ' ', '8:00 - 11:00', ' ', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'X', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Computer Science', 'CS7', 'Friday', 'OOSD', 'ECO', 'CG', 'HPCA', '*****', ' ', 'CD LAB', ' ', 'C14', 'C14', 'C14', 'C14', '*****', ' ', 'DL-6', ' ', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', 'RECESS', ' ', '3:00 - 6:00', ' ')";
		db.execSQL(sql);
		
		sql = "insert into Mech123 values('Computer Science', 'CS8', 'Monday', 'ECO', 'CD', 'OOSD', '*****', ' ', 'CG LAB', ' ', 'X', 'C14', 'C14', 'C14', '*****', ' ', 'DL-3', ' ', 'X', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'RECESS', ' ', '3:00 - 6:00', ' ', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Computer Science', 'CS8', 'Tuesday', 'OOSD', 'HPCA', 'ECO', '*****', ' ', 'CAT', ' ', 'X', 'C13', 'C13', 'C13', '*****', ' ', 'DL-1', ' ', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', 'RECESS', ' ', '3:00 - 6:00', ' ', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Computer Science', 'CS8', 'Wednesday', ' ', 'SE LAB', ' ', '*****', 'OOSD', 'CD', 'CG', 'HPCA', ' ', 'DL-9', ' ', '*****', 'C9', 'C9', 'C9', 'C9', ' ', '8:00 - 11:00', ' ', 'RECESS', '2:00 - 3:00', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Computer Science', 'CS8', 'Thursday', 'HPCA', 'CG', 'CD', '*****', ' ', 'CD LAB', ' ', 'X', 'C13', 'C13', 'C13', '*****', ' ', 'DL-6', ' ', 'X', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', '*****', ' ', '3:00 - 4:00', ' ', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('Computer Science', 'CS8', 'Friday', 'ECO', 'OOSD', 'CD', '*****', 'HPCA', 'CG', 'X', 'X', 'C15', 'C15', 'C15', '*****', 'C12', 'C12', 'X', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', 'RECESS', '12:00 - 1:00', '1:00 - 2:00', 'X', 'X')";
		db.execSQL(sql);
		
		sql = "insert into Mech123 values('IT', 'IT1', 'Monday', 'OOSD', 'MC', 'DA', '*****', ' ', 'CD LAB', ' ', 'X', 'C18', 'C18', 'C18', '*****', ' ', 'DL-7', ' ', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', 'RECESS', ' ', '3:00 - 6:00', ' ', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('IT', 'IT1', 'Tuesday', 'ECO', 'OOSD', 'MC', 'CD', '*****', ' ', 'DA LAB', ' ', 'C14', 'C14', 'C14', 'C14', '*****', ' ', 'DL-3', ' ', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', 'RECESS', ' ', '3:00 - 6:00', ' ')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('IT', 'IT1', 'Wednesday', 'DA', 'CD', 'ECO', ' ', 'CAT', ' ', 'X', 'X', 'C15', 'C15', 'C15', ' ', 'DL-9', ' ', 'X', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', ' ', '8:00 - 2:00', ' ', 'X', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('IT', 'IT1', 'Thursday', 'ECO', 'DA', 'OOSD', '*****', 'MC', 'CD', 'X', 'X', 'C14', 'C14', 'C14', '*****', 'C11', 'C11', 'X', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', 'X', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('IT', 'IT1', 'Friday', 'OOSD', 'CD', 'DA', '*****', ' ', 'SE LAB', ' ', 'X', 'C15', 'C15', 'C15', '*****', ' ', 'DL-9', ' ', 'X', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'RECESS', ' ', '3:00 - 6:00', ' ', 'X')";
		db.execSQL(sql);
		
		sql = "insert into Mech123 values('IT', 'IT2', 'Monday', 'MC', 'OOSD', 'ECO', '*****', 'DA', 'CD', 'X', 'X', 'C17', 'C17', 'C17', '*****', 'C9', 'C9', 'X', 'X', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', 'X', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('IT', 'IT2', 'Tuesday', 'CD', 'OOSD', 'DA', '*****', ' ', 'CD LAB', ' ', 'X', 'C15', 'C15', 'C15', '*****', ' ', 'DL-6', ' ', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', 'RECESS', ' ', '3:00 - 6:00', ' ', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('IT', 'IT2', 'Wednesday', 'MC', 'CD', 'OOSD', 'DA', '*****', ' ', 'DA LAB', ' ', 'C16', 'C16', 'C16', 'C16', '*****', ' ', 'DL-6', ' ', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', 'RECESS', ' ', '3:00 - 6:00', ' ')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('IT', 'IT2', 'Thursday', 'OOSD', 'MC', 'ECO', '*****', ' ', 'SE LAB', ' ', 'X', 'C15', 'C15', 'C15', '*****', ' ', 'DL-4', ' ', 'X', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'RECESS', ' ', '3:00 - 6:00', ' ', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('IT', 'IT2', 'Friday', ' ', 'CAT', ' ', '*****', 'ECO', 'CD', 'DA', 'X', ' ', 'DL-4', ' ', '*****', 'C13', 'C13', 'C13', 'X', ' ', '11:00 - 2:00', ' ', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00', 'X')";
		db.execSQL(sql);
		
		sql = "insert into Mech123 values('IT', 'IT3', 'Monday', 'MC', 'OOSD', 'DA', 'CD', '*****', ' ', 'SE', ' ', 'C6', 'C6', 'C6', 'C6', '*****', ' ', 'DL-9', ' ', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', 'RECESS', ' ', '3:00 - 6:00', ' ')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('IT', 'IT3', 'Tuesday', 'DA', 'MC', 'CD', '*****', ' ', 'CD LAB', ' ', 'X', 'C16', 'C16', 'C16', '*****', ' ', 'DL-7', ' ', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', 'RECESS', ' ', '3:00 - 6:00', ' ', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('IT', 'IT3', 'Wednesday', 'DA', 'MC', 'CD', '*****', 'ECO', 'OOSD', 'X', 'X', 'C17', 'C17', 'C17', '*****', 'C13', 'C13', 'X', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', 'RECESS', '12:00 - 1:00', '1:00 - 2:00', 'X', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('IT', 'IT3', 'Thursday', 'OOSD', 'ECO', 'DA', '*****', ' ', 'DA LAB', ' ', 'X', 'C16', 'C16', 'C16', '*****', ' ', 'DL-3', ' ', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', 'RECESS', ' ', '3:00 - 6:00', ' ', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('IT', 'IT3', 'Friday', 'CD', 'OOSD', 'ECO', ' ', 'CAT', ' ', 'X', 'X', 'C16', 'C16', 'C16', ' ', 'DL-9', ' ', 'X', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', ' ', '11:00 - 2:00', ' ', 'X', 'X')";
		db.execSQL(sql);
		
		sql = "insert into Mech123 values('IT', 'IT4', 'Monday', ' ', 'CAT', ' ', '*****', 'DA', 'ECO', 'OOSD', 'X', ' ', 'DL-7', ' ', '*****', 'C10', 'C10', 'C10', 'X', ' ', '11:00 - 2:00', ' ', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('IT', 'IT4', 'Tuesday', 'ECO', 'OOSD', 'CD', 'MC', '*****', ' ', 'SE LAB', ' ', 'C17', 'C17', 'C17', 'C17', '*****', ' ', 'DL-9', ' ', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('IT', 'IT4', 'Wednesday', 'DA', 'MC', '*****', 'OOSD', 'DA', 'CD', 'X', 'X', 'C12', 'C12', '*****', 'C10', 'C10', 'C10', 'X', 'X', '11:00 - 12:00', '12:00 - 1:00', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00', 'X', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('IT', 'IT4', 'Thursday', ' ', 'CD LAB', ' ', '*****', 'MC', 'OOSD', 'CD', 'X', ' ', 'DL-7', ' ', '*****', 'C12', 'C12', 'C12', 'X', ' ', '11:00 - 2:00', ' ', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', '5:00 - 6:00', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('IT', 'IT4', 'Friday', 'ECO', 'CD', 'DA', '*****', ' ', 'DA LAB', ' ', 'X', 'C17', 'C17', 'C17', '*****', ' ', 'DL-3', ' ', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', 'RECESS', ' ', '3:00 - 6:00', ' ', 'X')";
		db.execSQL(sql);
		
		sql = "insert into Mech123 values('IT', 'IT5', 'Monday', 'CD', 'DA', 'OOSD', '*****', ' ', 'SE LAB', ' ', 'X', 'C7', 'C7', 'C7', '*****', ' ', 'DL-4', ' ', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', 'RECESS', ' ', '3:00 - 6:00', ' ', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('IT', 'IT5', 'Tuesday', 'OOSD', 'MC', 'DA', 'ECO', '*****', ' ', 'CAT', ' ', 'C18', 'C18', 'C18', 'C18', '*****', ' ', 'DL-2', ' ', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', 'RECESS', ' ', '3:00 - 6:00', ' ')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('IT', 'IT5', 'Wednesday', 'DA', 'MC', 'CD', '*****', ' ', 'DA LAB', ' ', 'X', 'C17', 'C17', 'C17', '*****', ' ', 'DL-4', ' ', 'X', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'RECESS', ' ', '3:00 - 6:00', ' ', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('IT', 'IT5', 'Thursday', 'CD', 'DA', 'OOSD', '*****', 'MC', 'ECO', 'X', 'X', 'C17', 'C17', 'C17', '*****', 'C13', 'C13', 'X', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', 'X', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('IT', 'IT5', 'Friday', 'ECO', 'OOSD', 'CD', '*****', ' ', 'CD LAB', ' ', 'X', 'C16', 'C16', 'C16', '*****', ' ', 'DL-7', ' ', 'X', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'RECESS', ' ', '3:00 - 6:00', ' ', 'X')";
		db.execSQL(sql);
		
		sql = "insert into Mech123 values('IT', 'IT6', 'Monday', 'DA', 'ECO', 'OOSD', '*****', ' ', 'CD LAB', ' ', 'X', 'C18', 'C18', 'C18', '*****', ' ', 'DL-6', ' ', 'X', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'RECESS', ' ', '3:00 - 6:00', ' ', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('IT', 'IT6', 'Tuesday', 'MC', 'DA', 'CD', '*****', 'ECO', 'OOSD', 'X', 'X', 'C15', 'C15', 'C15', '*****', 'C16', 'C16', 'X', 'X', '11:00 - 12:00', '12:00 - 1:00', '1:00 - 2:00', 'RECESS', '3:00 - 4:00', '4:00 - 5:00', 'X', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('IT', 'IT6', 'Wednesday', 'OOSD', 'ECO', 'CD', '*****', ' ', 'CAT', ' ', 'X', 'C18', 'C18', 'C18', '*****', ' ', 'DL-2', ' ', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', 'RECESS', ' ', '3:00 - 6:00', ' ', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('IT', 'IT6', 'Thursday', 'MC', 'CD', 'DA', '*****', ' ', 'SE LAB', ' ', 'X', 'C18', 'C18', 'C18', '*****', ' ', 'DL-9', ' ', 'X', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', 'RECESS', ' ', '3:00 - 6:00', ' ', 'X')";
		db.execSQL(sql);
		sql = "insert into Mech123 values('IT', 'IT6', 'Friday', 'CD', 'DA', 'MC', 'OOSD', '*****', ' ', 'DA LAB', ' ', 'C18', 'C18', 'C18', 'C18', '*****', ' ', 'DL-4', ' ', '8:00 - 9:00', '9:00 - 10:00', '10:00 - 11:00', '11:00 - 12:00', 'RECESS', ' ', '3:00 - 6:00', ' ')";
		db.execSQL(sql);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

		print();
		mon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				restore();
				DAY = "Monday";
				
				mon.setBackgroundResource(R.drawable.monday1);
				print();
				
			}
		});
		tue.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				restore();
				DAY = "Tuesday";
				
				tue.setBackgroundResource(R.drawable.tuesday1);
				print();
			}
		});
		wed.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				restore();
				DAY = "Wednesday";
			
				wed.setBackgroundResource(R.drawable.wednesday1);
				print();
			}
		});
		thur.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				restore();
				DAY = "Thursday";
				
				thur.setBackgroundResource(R.drawable.thursday1);
				print();
			}
		});
		fri.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				restore();
				DAY = "Friday";
			
				fri.setBackgroundResource(R.drawable.friday1);
				print();
			}
		});
		
		
	}
	
	
	public void restore()
	{
		mon.setBackgroundResource(R.drawable.monday0);
		tue.setBackgroundResource(R.drawable.tuesday0);
		wed.setBackgroundResource(R.drawable.wednesday0);
		thur.setBackgroundResource(R.drawable.thursday0);
		fri.setBackgroundResource(R.drawable.friday0);
		
		Cfirst.setBackgroundColor(Color.rgb(213, 213, 204));
		Tfirst.setBackgroundColor(Color.rgb(213, 213, 204));
		first.setBackgroundColor(Color.rgb(213, 213, 204));
		
		Csecond.setBackgroundColor(Color.rgb(236, 236, 232));
		Tsecond.setBackgroundColor(Color.rgb(236, 236, 232));
		second.setBackgroundColor(Color.rgb(236, 236, 232));
		
		Cthird.setBackgroundColor(Color.rgb(213, 213, 204));
		Tthird.setBackgroundColor(Color.rgb(213, 213, 204));
		third.setBackgroundColor(Color.rgb(213, 213, 204));
		
		Cfourth.setBackgroundColor(Color.rgb(236, 236, 232));
		Tfourth.setBackgroundColor(Color.rgb(236, 236, 232));
		fourth.setBackgroundColor(Color.rgb(236, 236, 232));
		
		Cfifth.setBackgroundColor(Color.rgb(213, 213, 204));
		Tfifth.setBackgroundColor(Color.rgb(213, 213, 204));
		fifth.setBackgroundColor(Color.rgb(213, 213, 204));
		
		Csixth.setBackgroundColor(Color.rgb(236, 236, 232));
		Tsixth.setBackgroundColor(Color.rgb(236, 236, 232));
		sixth.setBackgroundColor(Color.rgb(236, 236, 232));
		
		Cseventh.setBackgroundColor(Color.rgb(213, 213, 204));
		Tseventh.setBackgroundColor(Color.rgb(213, 213, 204));
		seventh.setBackgroundColor(Color.rgb(213, 213, 204));
		
		Ceighth.setBackgroundColor(Color.rgb(236, 236, 232));
		Teighth.setBackgroundColor(Color.rgb(236, 236, 232));
		eighth.setBackgroundColor(Color.rgb(236, 236, 232));
		
	}
	public void print()
	{
		for(int j=0; j<list.length; j++)
        {
        	if(list[j].equals(branch))
        	{
        		switch(j)
				{
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
					
				}
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(Display.this, android.R.layout.simple_expandable_list_item_1, c_list);
				ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(Display.this, android.R.layout.simple_expandable_list_item_1, list);
				rightDrawerList.setAdapter(adapter);
				leftDrawerList.setAdapter(adapter1);
				
				rightDrawerList.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						//branch = temp;
						section = arg0.getItemAtPosition(arg2).toString();
						print();
						mDrawerLayout.closeDrawer(rightDrawerList);
					}
				});
        	}
        }	
		
		Cursor c = db.rawQuery("select * from Mech123 where brName='"+branch+"' and secName='"+section+"' and day='"+DAY+"'"   , null);
		while (c.moveToNext()) {
			//sect1.setText(section);
			br.setText(branch);
			cls.setText(section);
			TextView arr[] = {first, second, third, fourth, fifth, sixth, seventh, eighth, Cfirst, Csecond, Cthird, Cfourth, Cfifth, Csixth, Cseventh, Ceighth, Tfirst, Tsecond, Tthird, Tfourth, Tfifth, Tsixth, Tseventh, Teighth};
			for(int j=0; j<arr.length; j++)
			{
				if(arr[j].getVisibility()==4)
				{
					arr[j].setVisibility(0);
				}
				
			}
			//sect1.setTextColor(Color.WHITE);
			// day.setText(c.getString(1));
			first.setText(c.getString(3));
			second.setText(c.getString(4));
			third.setText(c.getString(5));
			fourth.setText(c.getString(6));
			fifth.setText(c.getString(7));
			sixth.setText(c.getString(8));
			seventh.setText(c.getString(9));
			eighth.setText(c.getString(10));
			Cfirst.setText(c.getString(11));
			Csecond.setText(c.getString(12));
			Cthird.setText(c.getString(13));
			Cfourth.setText(c.getString(14));
			Cfifth.setText(c.getString(15));
			Csixth.setText(c.getString(16));
			Cseventh.setText(c.getString(17));
			Ceighth.setText(c.getString(18));
			Tfirst.setText(c.getString(19));
			Tsecond.setText(c.getString(20));
			Tthird.setText(c.getString(21));
			Tfourth.setText(c.getString(22));
			Tfifth.setText(c.getString(23));
			Tsixth.setText(c.getString(24)); 
			Tseventh.setText(c.getString(25));
			Teighth.setText(c.getString(26)); 
			for(int j=0; j<arr.length; j++)
			{
				if(arr[j].getText().equals("X"))
				{
					arr[j].setText("");
					arr[j].setBackgroundColor(Color.WHITE);
				}
				if(arr[j].getText().equals("*****"))
				{
					arr[j].setBackgroundColor(Color.WHITE);
				}
				if(arr[j].getText().equals("RECESS"))
				{
					arr[j].setBackgroundColor(Color.WHITE);
				}
			}
			
		}
	}
	
	
	
	

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Intent i = getIntent();
		branch = i.getStringExtra("branch");
		section = i.getStringExtra("section");
		print();
		sp = getSharedPreferences(myPrefs, MODE_PRIVATE);
        String t_branch = sp.getString("branch", "");  
        String t_section = sp.getString("section", ""); 
        if((!t_branch.equals(""))&&(!t_section.equals("")))
        {
		branch = t_branch;
		section = t_section;
		print();
        }
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId())
		{
		case(R.id.action_settings):
			Intent i=new Intent(Display.this, Settings.class);
			startActivity(i);
			break;
		case(R.id.holidaylist):
			Intent i1=new Intent(Display.this, Holiday.class);
			startActivity(i1);
			break;	
		case(R.id.about):
			Intent i2 = new Intent(Display.this, AboutUs.class);
			startActivity(i2);
			break;	
		case(android.R.id.home):
			if(mDrawerLayout.isDrawerOpen(leftDrawerList))
				mDrawerLayout.closeDrawer(leftDrawerList);
			else
				mDrawerLayout.openDrawer(leftDrawerList);
			break;
		}
		return super.onOptionsItemSelected(item);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display, menu);
		return true;
	}

}
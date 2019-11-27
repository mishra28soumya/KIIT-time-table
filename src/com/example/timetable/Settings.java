package com.example.timetable;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.Toast;

public class Settings extends Activity {
	
	ArrayAdapter<String> adapter_br, adapter_cls;
	String list[] = {"Automobile", "Computer Science", "Civil", "Electrical", "EEE", "ETC", "E&I", "IT", "Mechanical"}; 
	String list1[] = {"Automobile", "Computer Science", "Civil", "Electrical", "EEE", "ETC", "E&I", "IT", "Mechanical"}; 
	String mech_class[] = {"M1", "M2", "M3", "M4", "M5", "M6", "M7"};
	String cse_class[] = {"CS1", "CS2", "CS3", "CS4", "CS5", "CS6", "CS7", "CS8"};
	String it_class[] = {"IT1", "IT2", "IT3", "IT4", "IT5", "IT6"};
	String auto_class[] = {"M8"};
	String ee_class[] = {"EL1", "EL2", "EL3", "EL4", "EL5", "EL6"};
	String etc_class[] = {"ETC1", "ETC2", "ETC3", "ETC4", "ETC5", "ETC6", "ETC7"};
	String ei_class[] = {"E&I"};
	String eee_class[] = {"EEE1", "EEE2", "EEE3", "EEE4", "EEE5", "EEE6"};
	String civil_class[] = {"CV1", "CV2", "CV3", "CV4", "CV5", "CV6"};
	String c_list[], b_name=" ", c_name=" ", myPrefs="myPrefs";
	Spinner spinner_br, spinner_cls;
	int pos_br, pos_cls;
	SharedPreferences sp;
	Button save;
	int flag=1;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		
		
		ActionBar ab = getActionBar();
		ab.setBackgroundDrawable(new ColorDrawable(Color.rgb(109, 208, 247)));
		int actionBarTitleId = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
		if(actionBarTitleId>0)
		{
			TextView title = (TextView) findViewById(actionBarTitleId);
			if(title!=null)
				title.setTextColor(Color.WHITE);
		}
		
		sp=getSharedPreferences(myPrefs, MODE_PRIVATE);
		pos_br = sp.getInt("branch_position", 0);
		pos_cls = sp.getInt("section_position", 0);
		c_name = sp.getString("section", "");
		b_name = sp.getString("branch", "");
		
	/*
		if(!(c_name.equals("") && b_name.equals("")))
		{
			Drawable ds = getResources().getDrawable(R.drawable.sav_custom);
			if(save.getResources().equals(ds))
				save.setBackgroundResource(R.drawable.update_custom);
		}
		else
		{
			Drawable du = getResources().getDrawable(R.drawable.update_custom);
			if(save.getResources().equals(du))
				save.setBackgroundResource(R.drawable.sav_custom);
		}
		
	*/
		
		save = (Button) findViewById(R.id.save);
		save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				flag=1;
				save.setBackgroundResource(R.drawable.update_custom);
				Editor e1 = sp.edit();
				
				e1.putString("branch", b_name);
				e1.putString("section", c_name);
				e1.putInt("branch_position", pos_br);
				e1.putInt("section_position", pos_cls);
				e1.commit();
				Toast.makeText(Settings.this, "Saved", Toast.LENGTH_LONG).show();
			}
		});
		spinner_br=(Spinner) findViewById(R.id.spinner_br);
		spinner_cls=(Spinner) findViewById(R.id.spinner_cls);
		adapter_br = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, list);
		spinner_br.setAdapter(adapter_br);
		spinner_br.setPrompt("Select Branch");
		spinner_br.setSelection(pos_br);
		spinner_cls.setSelection(pos_cls);
		spinner_br.setOnItemSelectedListener(new OnItemSelectedListener() {

			

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				b_name = list[arg2];
				pos_br=arg2;
				
				switch (arg2) {
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
				c_name=c_list[pos_cls];
				adapter_cls = new ArrayAdapter<String>(Settings.this, android.R.layout.simple_dropdown_item_1line, c_list);
				spinner_cls.setAdapter(adapter_cls);
				spinner_cls.setPrompt("Select Section");
				spinner_cls.setSelection(pos_cls);
				spinner_cls.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						//Toast.makeText(SettingsActivity.this, "hello", Toast.LENGTH_SHORT).show();
						c_name = arg0.getItemAtPosition(arg2).toString();
						b_name = list[pos_br];
						pos_cls=arg2;
						flag=0;
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
						
					}
				});
				//Toast.makeText(Settings.this, spinner_br.getSelectedItem().toString()+"    "+spinner_cls.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}

			
		});
		
		
	}
	/*@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		if(flag==0)
		{
			final AlertDialog.Builder adb = new AlertDialog.Builder(this);
			adb.setTitle("Save changes");
			adb.setMessage("Some changes need to be saved. Do you wish to save them now?");
			adb.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					flag=1;
					Editor e1 = sp.edit();
					
					e1.putString("branch", b_name);
					e1.putString("section", c_name);
					e1.putInt("branch_position", pos_br);
					e1.putInt("section_position", pos_cls);
					e1.commit();
					Toast.makeText(Settings.this, "Saved", Toast.LENGTH_LONG).show();
					dismissDialog(adb.hashCode());
				}
			});
			adb.setPositiveButton("No", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
					dismissDialog(adb.hashCode());
					
				}
			});
			adb.create().show();
		}
	} */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		
			super.onPause();
		
		
		
	}
	
		//cb.setChecked(sp.getBoolean("flag", false));
		/*cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				
				if(isChecked==false)
				{
					if(choice==true)
					{
					Editor e1 = sp.edit();
					e1.putBoolean("flag", isChecked);
					e1.putString("branch", " ");
					e1.putString("section", " ");
					e1.putInt("branch_position", 0);
					e1.putInt("section_position", 0);
					e1.commit();
					}
				}
				else
				{
					if(choice==true)
					{
					Editor e1 = sp.edit();
					e1.putBoolean("flag", isChecked);
					e1.putString("branch", b_name);
					e1.putString("section", c_name);
					e1.putInt("branch_position", pos_br);
					e1.putInt("section_position", pos_cls);
					e1.commit();
					}
					else
					{
						Editor e1 = sp.edit();
						e1.putBoolean("flag", isChecked);
						e1.putString("branch", b_name);
						e1.putString("section", c_name);
						e1.putInt("branch_position", pos_br);
						e1.putInt("section_position", pos_cls);
						e1.putBoolean("go", false);
						e1.commit();
					}
				}
					
			}
		});
		spinner_br=(Spinner) findViewById(R.id.spinner_br);
		spinner_cls=(Spinner) findViewById(R.id.spinner_cls);
		adapter_br = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, list1);
		spinner_br.setAdapter(adapter_br);
		spinner_br.setPrompt("Select Branch");
		spinner_br.setSelection(pos_br);
		spinner_cls.setSelection(pos_cls);
		spinner_br.setOnItemSelectedListener(new OnItemSelectedListener() {

			

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				b_name = list[arg2];
				pos_br=arg2;
				
				switch (arg2) {
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
				c_name=c_list[pos_cls];
				adapter_cls = new ArrayAdapter<String>(Settings.this, android.R.layout.simple_dropdown_item_1line, c_list);
				spinner_cls.setAdapter(adapter_cls);
				spinner_cls.setPrompt("Select Section");
				spinner_cls.setSelection(pos_cls);
				spinner_cls.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						//Toast.makeText(SettingsActivity.this, "hello", Toast.LENGTH_SHORT).show();
						c_name = arg0.getItemAtPosition(arg2).toString();
						b_name = list[pos_br];
						pos_cls=arg2;
						if(cb.isChecked())
						{
						Editor e = sp.edit();
						e.putString("branch", b_name);
						e.putString("section", c_name);
						e.putInt("branch_position", pos_br);
						e.putInt("section_position", pos_cls);
						e.putBoolean("go", false);
						e.commit();
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
						
					}
				});
				Toast.makeText(Settings.this, spinner_br.getSelectedItem().toString()+"    "+spinner_cls.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}

			
		});
				}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
	}*/

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId())
		{
		case(R.id.restore):
			sp=getSharedPreferences(myPrefs, MODE_PRIVATE);
			pos_br = sp.getInt("branch_position", 0);
			pos_cls = sp.getInt("section_position", 0);
			c_name = sp.getString("section", "");
			b_name = sp.getString("branch", "");
			if(!(c_name.equals("") && b_name.equals("")))
			{
			AlertDialog.Builder adb = new AlertDialog.Builder(Settings.this);
			adb.setTitle("Are you sure?");
			adb.setMessage("Do you want to restore your default settings?");
			adb.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Editor e1 = sp.edit();
					
					e1.putString("branch", "");
					e1.putString("section", "");
					e1.putInt("branch_position", 0);
					e1.putInt("section_position", 0);
					e1.commit();
					Toast.makeText(Settings.this, "Restored", Toast.LENGTH_LONG).show();
					spinner_br.setSelection(0);
					spinner_cls.setSelection(0);
					save.setBackgroundResource(R.drawable.sav_custom);
				}
			});
			adb.setPositiveButton("No", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			});
			adb.create().show();
			}
			else
			{
				Toast.makeText(Settings.this, "Default values not set", Toast.LENGTH_SHORT).show();
			}
			break;
		case(R.id.about):
			Intent i=new Intent(Settings.this, AboutUs.class);
			startActivity(i);
			break;
		case(R.id.holidaylist):
			Intent i1=new Intent(Settings.this, Holiday.class);
			startActivity(i1);
			break;		
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		sp=getSharedPreferences(myPrefs, MODE_PRIVATE);
		
		String s = sp.getString("branch", "");
		if(s.equals(""))
		{
			save.setBackgroundResource(R.drawable.sav_custom);
		}
		else
		{
			save.setBackgroundResource(R.drawable.update_custom);
		}
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		
		return true;
	}

}

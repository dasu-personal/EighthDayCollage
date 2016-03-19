package com.dasugames.eighthdaycollage.activity.menu;



import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.dasugames.eighthdaycollage.EighthDayCollageApplication;
import com.dasugames.eighthdaycollage.activity.EighthDayCollageActivity;
import com.dasugames.eighthdaycollage.activity.dashboard.PickPathPoiToEditActivity;
import com.dasugames.eighthdaycollage.activity.dashboard.ScreenActivity;
import com.dasugames.eighthdaycollage.activity.phonerecord.BasicTextActivity;
import com.dasugames.eighthdaycollage.data.ScenarioData;
import com.dasugames.eighthdaycollage.data.ScreenData;
import com.dasugames.eighthdaycollage.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
/**
 * For the proof of concept, this will only allow users to start
 * a new scenario. Eventually, I want to change this to
 * allow for loading saved scenarios as well. Yes, I know
 * that this is slightly redundant from starting a new
 * scenario and importing a save file.
 * @author darren.sue
 *
 */
public class TitleActivity extends VerticalMenuActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.vertical_menu_layout);
		super.onCreate(savedInstanceState);
		

		
		try {
			// try loading resume data
			loadScenario();
			app.getCurrentApplicationData().setCurrentScreen(app.getCurrentApplicationData().getSaveData().getCurrentScenario().getStartScreen());
		} catch (Exception e) {
			// potentially no save data
			ScreenData dummyScreen = new ScreenData();
			app.getCurrentApplicationData().getSaveData().setCurrentScenario(new ScenarioData());
			app.getCurrentApplicationData().getSaveData().getCurrentScenario().setStartScreen(dummyScreen);
			app.getCurrentApplicationData().setCurrentScreen(dummyScreen);
			
			button1.setEnabled(false); // no scenario to load
		}
		
		//setContentView(R.layout.vertical_menu_layout);
		// TODO need to get the eightDayCollegeApplication from application data
		initButton();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void initButton(){
		
		button1.setText("Resume scenario");
		button2.setText("New scenario");
		button3.setText("Load from save");
		button4.setText("Import from wifi");
		button5.setText("Back");
		
		
		// TODO do something about each of these functionalities
		button4.setEnabled(false);
		button6.setEnabled(false);
		
		
		button1.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {	
				startActivitySimple(ScreenActivity.class);
			}
 			}); 
		button2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {	
				startActivitySimple(CreateNewScenarioActivity.class);

			}
 			});
		button3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// rename scene
				startActivitySimple(LoadScenarioActivity.class);

			}

		});
		button4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startActivitySimple(ImportScenarioActivity.class);

			}
		});
		button5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				back();
			}
		});
		button6.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

				

			}
		});
		
	}

	@Override
	protected void back() {
		// TODO Auto-generated method stub
		finish();
	}
	

}

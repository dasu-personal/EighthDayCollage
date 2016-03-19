package com.dasugames.eighthdaycollage.activity.menu;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import com.dasugames.eighthdaycollage.EighthDayCollageApplication;
import com.dasugames.eighthdaycollage.activity.dashboard.PickPathPoiToEditActivity;
import com.dasugames.eighthdaycollage.activity.dashboard.PlaceNewPathPoiActivity;
import com.dasugames.eighthdaycollage.activity.dashboard.ScreenActivity;
import com.dasugames.eighthdaycollage.data.ScenarioData;
import com.dasugames.eighthdaycollage.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class EditMainActivity extends VerticalMenuActivity {
	EighthDayCollageApplication eightDayCollageApplication;
	EighthDayCollageApplication app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO I may have to move this to the parent Vertical Menu Activity
		// If not, this will be a great way to make sure that the buttons are not doing anything stupid.
		setContentView(R.layout.vertical_menu_layout);
		super.onCreate(savedInstanceState);
		
		app = (EighthDayCollageApplication) this.getApplicationContext();
		
		button1.setText("New paths/poi");
		button2.setText("Edit paths/poi"); 
		button3.setText("Edit scene");
		button4.setText("Edit scenario");
		button5.setText("Back");
		
		// TODO do something about each of these functionalities
		button6.setEnabled(false);
		
		button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivitySimple( PlaceNewPathPoiActivity.class);

			}

		});
		button2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivitySimple( PickPathPoiToEditActivity.class);
			}
		});
		button3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startActivitySimple(EditSceneActivity.class);


			}

		});
		button4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// this starts up the screenactivity.
				startActivitySimple( EditScenarioActivity.class);

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
		startActivitySimple(ScreenActivity.class);

	}
	
	
	

}

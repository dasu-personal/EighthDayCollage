package com.dasugames.eighthdaycollage.activity.menu;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.dasugames.eighthdaycollage.EighthDayCollageApplication;
import com.dasugames.eighthdaycollage.activity.dashboard.PickPathPoiToEditActivity;
import com.dasugames.eighthdaycollage.activity.dashboard.PlaceNewPathPoiActivity;
import com.dasugames.eighthdaycollage.activity.dashboard.ScreenActivity;
import com.dasugames.eighthdaycollage.activity.phonerecord.BasicAudioActivity;
import com.dasugames.eighthdaycollage.data.PointOfInterestData;
import com.dasugames.eighthdaycollage.data.ScreenData;
import com.dasugames.eighthdaycollage.resource.MusicResource;
import com.dasugames.eighthdaycollage.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.MediaStore.Files;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class CreateNewPoiActivity extends AbstractVerticalMenuWithMusicHandler {
	EighthDayCollageApplication eightDayCollageApplication;

	EighthDayCollageApplication app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO I may have to move this to the parent Vertical Menu Activity
		// If not, this will be a great way to make sure that the buttons are not doing anything stupid.
		setContentView(R.layout.vertical_menu_layout);
		super.onCreate(savedInstanceState);
		
		app = (EighthDayCollageApplication) this.getApplicationContext();
		
		button1.setText("Record new info point with phone microphone");
		button2.setText("Import info point from phone");
		button3.setText("Import info point from other scene");
		button4.setText("Import info point from saved scenario"); 
		button5.setText("Back");
		
		// TODO do something about each of these functionalitie
		//button1.setEnabled(false);
		button2.setEnabled(false);
		button3.setEnabled(false);
		button4.setEnabled(false);
		button6.setEnabled(false);
		
		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
				startActivitySimpleResult(BasicAudioActivity.class,0);

			}
		});
		button2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO fill this in with something meaningful
			}
		});
		button3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO fill this in with something meaningful
			}
		});
		button4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO fill this in with something meanningful
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
				// whatever
			}
		});

	}
	
	@Override
	protected void back() {
		// go back to previous activity, which will be the new pathpoi page
		// I am also setting the temporary references used for building
		// new links and scenes back here
		app.getCurrentApplicationData().setNextPoi(null);
		//app.getCurrentApplicationData().setOriginatingScreen(null);
		startActivitySimple(PlaceNewPathPoiActivity.class);

	}
	

	@Override
	protected void handleGeneratedMusicResource(String musicResource) {

		
		
		// get working poi
		PointOfInterestData poi = app.getCurrentApplicationData().getNextPoi();

		app.setPoiMusic(poi, musicResource);
		
		app.getCurrentApplicationData().getCurrentScreen().getInteractions()
				.add(poi);

		// set this one to null
		app.getCurrentApplicationData().setNextPoi(null);

		startActivitySimple( ScreenActivity.class);

		
	}
	
	

}

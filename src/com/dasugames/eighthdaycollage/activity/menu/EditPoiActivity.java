package com.dasugames.eighthdaycollage.activity.menu;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.dasugames.eighthdaycollage.EighthDayCollageApplication;
import com.dasugames.eighthdaycollage.activity.EighthDayCollageActivity;
import com.dasugames.eighthdaycollage.activity.dashboard.MovePoiActivity;
import com.dasugames.eighthdaycollage.activity.dashboard.PickPathPoiToEditActivity;
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

public class EditPoiActivity extends AbstractVerticalMenuWithMusicHandler {
	EighthDayCollageApplication eightDayCollageApplication;

	EighthDayCollageApplication app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO I may have to move this to the parent Vertical Menu Activity
		// If not, this will be a great way to make sure that the buttons are not doing anything stupid.
		setContentView(R.layout.vertical_menu_layout);
		super.onCreate(savedInstanceState);
		
		app = (EighthDayCollageApplication) this.getApplicationContext();
		
		button1.setText("Move poi");
		button2.setText("Redo sound");
		button3.setText("Delete poi");
		button4.setText("Back");
		
		// TODO do something about each of these functionalitie
		button3.setEnabled(false);
		button5.setEnabled(false);
		button6.setEnabled(false);
		
		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				app.getCurrentApplicationData().setOriginatingScreen(app.getCurrentApplicationData().getCurrentScreen());
				startActivitySimple(MovePoiActivity.class);


			}
		});
		button2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

				startActivitySimpleResult(BasicAudioActivity.class, 0);
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
				back();
			}
		});
		button5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

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
		app.getCurrentApplicationData().setNextPoi(null);
		startActivitySimple(PickPathPoiToEditActivity.class);
	}
	

	@Override
	protected void handleGeneratedMusicResource(String musicResource) {
		// get working poi
		PointOfInterestData poi = app.getCurrentApplicationData().getNextPoi();
		app.setPoiMusic(poi, musicResource);
		
		// set this one to null
		app.getCurrentApplicationData().setNextPoi(null);
		
		startActivitySimple(ScreenActivity.class);
		
	}
	
	

}

package com.dasugames.eighthdaycollage.activity.menu;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.dasugames.eighthdaycollage.EighthDayCollageApplication;
import com.dasugames.eighthdaycollage.activity.dashboard.BrowseScreenActivity;
import com.dasugames.eighthdaycollage.activity.dashboard.PickPathPoiToEditActivity;
import com.dasugames.eighthdaycollage.activity.dashboard.ScreenActivity;
import com.dasugames.eighthdaycollage.activity.phonerecord.BasicAudioActivity;
import com.dasugames.eighthdaycollage.activity.phonerecord.BasicCameraActivity;
import com.dasugames.eighthdaycollage.data.ApplicationData;
import com.dasugames.eighthdaycollage.data.LinkData;
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

public class EditAmbienceActivity extends AbstractVerticalMenuWithMusicHandler {
	EighthDayCollageApplication eightDayCollageApplication;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO I may have to move this to the parent Vertical Menu Activity
		// If not, this will be a great way to make sure that the buttons are not doing anything stupid.
		setContentView(R.layout.vertical_menu_layout);
		super.onCreate(savedInstanceState);
		
		
		button1.setText("Record new ambience with phone microphone");
		button2.setText("Import ambience from phone");
		button3.setText("Import ambience from other scene");
		button4.setText("Import ambience from saved scenario"); 
		button6.setText("Back");
		
		// TODO do something about each of these functionalitie
		//button1.setEnabled(false);
		button2.setEnabled(false);
		button4.setEnabled(false);
		button5.setEnabled(false);
		
		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startActivitySimpleResult( BasicAudioActivity.class,0);

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
				startActivitySimpleResult(BrowseScreenActivity.class, 1);
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

			}
		});
		button6.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				back();
				
			}
		});

	}
	
	@Override
	protected void back() {
		startActivitySimple( EditMainActivity.class);

	}


	@Override
	protected void handleGeneratedMusicResource(String musicResource) {
		ScreenData screenData = app.getCurrentApplicationData().getCurrentScreen(); 
		app.setBackgroundAmbience(screenData, musicResource);
		screenData.setBackgroundMusic(musicResource);
		
		startActivitySimple(ScreenActivity.class);

		
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		super.onActivityResult( requestCode,  resultCode,  data);
		
		if (requestCode != 1){
			return;
		}
		ApplicationData applicationData = app.getCurrentApplicationData();
		ScreenData targetedScreen = app.getCurrentApplicationData().getCurrentScreen();
		ScreenData originatingScreen = app.getCurrentApplicationData().getOriginatingScreen();
		Log.d("whereami", "before targeted screen check null");		
		if ( targetedScreen != null){
			Log.d("whereami", "after verified that targeted screen was not null");	

			originatingScreen.setBackgroundMusic(originatingScreen.getBackgroundMusic());
			
			
			// reset the application values to move on with life
			applicationData.setCurrentScreen(targetedScreen);
			applicationData.setOriginatingScreen(null);
			
			
			// start a new scene and see where it takes us
			startActivitySimple(ScreenActivity.class);

			
		} else {
			// setup to try again
			app.getCurrentApplicationData().setCurrentScreen(app.getCurrentApplicationData().getOriginatingScreen());
			app.getCurrentApplicationData().setOriginatingScreen(null);
		}
	}
	
	

}

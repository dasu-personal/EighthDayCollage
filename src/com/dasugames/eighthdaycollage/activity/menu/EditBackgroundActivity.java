package com.dasugames.eighthdaycollage.activity.menu;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.dasugames.eighthdaycollage.EighthDayCollageApplication;
import com.dasugames.eighthdaycollage.activity.dashboard.PickPathPoiToEditActivity;
import com.dasugames.eighthdaycollage.activity.dashboard.ScreenActivity;
import com.dasugames.eighthdaycollage.activity.phonerecord.BasicCameraActivity;
import com.dasugames.eighthdaycollage.data.ApplicationData;
import com.dasugames.eighthdaycollage.data.LinkData;
import com.dasugames.eighthdaycollage.data.ScreenData;
import com.dasugames.eighthdaycollage.resource.ImageResource;
import com.dasugames.eighthdaycollage.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class EditBackgroundActivity extends AbstractVerticalMenuWithPhotoHandler {
	private EighthDayCollageApplication app;
	private static String TAG = "EditBackground";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO I may have to move this to the parent Vertical Menu Activity
		// If not, this will be a great way to make sure that the buttons are not doing anything stupid.
		setContentView(R.layout.vertical_menu_layout);
		super.onCreate(savedInstanceState);
		
		app = (EighthDayCollageApplication) this.getApplicationContext();
		
		button1.setText("Take new photo with camera");
		button2.setText("Import photo from phone");
		button3.setText("Import photo from other scene");
		button4.setText("Import photo from saved scenario"); 
		button6.setText("Back");
		
		// TODO do something about each of these functionalitie
		//button1.setEnabled(false);
		button2.setEnabled(false);
		button3.setEnabled(false);
		button4.setEnabled(false);
		button5.setEnabled(false);
		
		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

				// Intent a = new Intent(MainActivity.this,
				// BasicCameraActivity.class);
				startActivitySimpleResult( BasicCameraActivity.class, 0);
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
				// TODO no idea how this is actually going to work.
			}
		});
		button6.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// this just goes back to the previous screen
				back();
				
			}
		});

	}
	

	/*
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data != null) {
			// Some setup before I can run other stuff
			ApplicationData applicationData = app.getCurrentApplicationData();

			
			// Setup the new bitmap used by a new screendata
			//Bitmap bp = (Bitmap) data.getExtras().get("data");
			ImageResource createdBackgroundImage;
			
			String fileName = data.getExtras().getString("data");
			ScreenData newScreen;
			try {
				Log.d(TAG, "try to get recorded file with " + fileName);
				createdBackgroundImage = new ImageResource(fileName, this.getApplicationContext());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				Log.d(TAG, "Could not create screen due to not being able to get the screen data out of the internal storage");
				e.printStackTrace();
				return;
			}
			ScreenData screenData = applicationData.getCurrentScreen();
			screenData.getBackgroundImage().removeReference();
			screenData.setBackgroundImage(createdBackgroundImage);
			
			// start a new scene and see where it takes us
			Intent a = new Intent(EditBackgroundActivity.this, ScreenActivity.class);
			a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(a);
			finish();
			
		}
		// TODO maybe you want to spit out some error message here or something
		// for now we error silently and the user can pick a new method to create
		// a new scene
	}
	*/
	
	@Override
	protected void back() {
		startActivitySimple(EditMainActivity.class);
	}
	


	@Override
	protected void handleGeneratedImageResource(String imageResource) {
		ApplicationData applicationData = app.getCurrentApplicationData();

		ScreenData screenData = applicationData.getCurrentScreen();
		//screenData.getBackgroundImage().removeReference();
		try {
			app.setBackgroundImage(screenData, imageResource);
			
			// start a new scene and see where it takes us
			startActivitySimple(ScreenActivity.class);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
	}

}

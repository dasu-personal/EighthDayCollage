package com.dasugames.eighthdaycollage.activity.menu;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.dasugames.eighthdaycollage.EighthDayCollageApplication;
import com.dasugames.eighthdaycollage.activity.dashboard.BrowseScreenActivity;
import com.dasugames.eighthdaycollage.activity.dashboard.PickPathPoiToEditActivity;
import com.dasugames.eighthdaycollage.activity.dashboard.PlaceNewPathPoiActivity;
import com.dasugames.eighthdaycollage.activity.dashboard.ScreenActivity;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

/**
 * This is an activity created after the new link has been placed
 * to decide where the new link points to.
 * @author darren.sue
 *
 */
public class CreateNewLinkActivity extends VerticalMenuActivity {



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.vertical_menu_layout);
		super.onCreate(savedInstanceState);
		
		
		button1.setText("Link to new scene");
		button2.setText("Link to existing scene");
		button3.setText("Link imported scenario"); 
		button4.setText("Back");
		
		// TODO do something about each of these functionalities
		button3.setEnabled(false);
		button5.setEnabled(false);
		button6.setEnabled(false);
		
		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startActivitySimple(CreateNewSceneActivity.class);
			}
		});
		button2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startActivitySimpleResult(BrowseScreenActivity.class,0);
			}
		});
		button3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startActivitySimple( ImportScenarioActivity.class);

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
				// TODO probably doesn't need to be here
			}
		});
		button6.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO probably doesn't need to be here
			}
		});

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		ApplicationData applicationData = app.getCurrentApplicationData();
		ScreenData targetedScreen = app.getCurrentApplicationData().getCurrentScreen();
		ScreenData originatingScreen = app.getCurrentApplicationData().getOriginatingScreen();
		LinkData originatingLink = app.getCurrentApplicationData().getNextLink();
		Log.d("whereami", "before targeted screen check null");		
		if ( targetedScreen != null){
			Log.d("whereami", "after verified that targeted screen was not null");	

			// update the newly created link
			originatingLink.setTargetedScreen(targetedScreen);
			originatingScreen.getLinks().add(originatingLink);
			
			
			// add the paired link
			LinkData pairedLink = new LinkData();
			pairedLink.setDirection((originatingLink.getDirection() + 180) % 360);
			pairedLink.setTargetedScreen(originatingScreen);
			targetedScreen.getLinks().add(pairedLink);
			
			
			pairedLink.setPairedLink(originatingLink);
			originatingLink.setPairedLink(pairedLink);
			
			// reset the application values to move on with life
			applicationData.setCurrentScreen(targetedScreen);
			applicationData.setOriginatingScreen(null);
			applicationData.setNextLink(null);
			
			// start a new scene and see where it takes us
			startActivitySimple(ScreenActivity.class);

			
		} else {
			// setup to try again
			app.getCurrentApplicationData().setCurrentScreen(app.getCurrentApplicationData().getOriginatingScreen());
		}
	}
	
	protected void back() {
		
		// go back to previous activity, which will be the new pathpoi page
		// I am also setting the temporary references used for building
		// new links and scenes back here
		app.getCurrentApplicationData().setNextLink(null);
		app.getCurrentApplicationData().setOriginatingScreen(null);
		startActivitySimple( PlaceNewPathPoiActivity.class);

	}
	

}

package com.dasugames.eighthdaycollage.activity.menu;

import java.io.FileNotFoundException;

import com.dasugames.eighthdaycollage.EighthDayCollageApplication;
import com.dasugames.eighthdaycollage.activity.dashboard.ScreenActivity;
import com.dasugames.eighthdaycollage.activity.phonerecord.BasicCameraActivity;
import com.dasugames.eighthdaycollage.data.ApplicationData;
import com.dasugames.eighthdaycollage.data.LinkData;
import com.dasugames.eighthdaycollage.data.ScreenData;
import com.dasugames.eighthdaycollage.R;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
/**
 * This is called from the create new link activity, where it returns on back press.
 * This activity corresponds to the creation of a new screen to an existing scenario.
 * @author darren.sue
 *
 */
public class CreateNewSceneActivity extends AbstractVerticalMenuWithPhotoHandler {
	EighthDayCollageApplication eightDayCollageApplication;
	
	/**
	 * What it says on the tin is what this does. One thing to note
	 * though is that right now I am running under the assumption that
	 * this will only get called when new links are created.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO I may have to move this to the parent Vertical Menu Activity
		// If not, this will be a great way to make sure that the buttons are not doing anything stupid.
		setContentView(R.layout.vertical_menu_layout);
		super.onCreate(savedInstanceState);
		
		
		button1.setText("Take new photo with camera");
		button2.setText("Import photo from phone");
		button3.setText("Import photo from current scenario");
		button4.setText("Import photo from saved scenario");
		button5.setText("Back");
		
		// TODO do something about each of these functionalities
		button2.setEnabled(false);
		button3.setEnabled(false);
		button4.setEnabled(false);
		button6.setEnabled(false);
		
		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

				// Intent a = new Intent(MainActivity.this,
				// BasicCameraActivity.class);
				startActivitySimpleResult( BasicCameraActivity.class,0);
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

	@Override
	protected void back() {
		
		startActivitySimple(CreateNewLinkActivity.class);

	}


	@Override
	protected void handleGeneratedImageResource(String imageResource) {

		// Some setup before I can run other stuff
		ApplicationData applicationData = app.getCurrentApplicationData();
		LinkData originatingLink = applicationData.getNextLink();
		ScreenData originatingScreen = applicationData.getOriginatingScreen();
		
		ScreenData newScreen = new ScreenData();
		try {
			app.setBackgroundImage(newScreen, imageResource);
			applicationData.setCurrentScreen(newScreen);
			
			// update the newly created link
			originatingLink.setTargetedScreen(newScreen);
			originatingScreen.getLinks().add(originatingLink);
			
			// add the paired link
			LinkData pairedLink = new LinkData();
			pairedLink.setPairedLink(originatingLink);
			originatingLink.setPairedLink(pairedLink);
			pairedLink.setDirection((originatingLink.getDirection() + 180) % 360);
			pairedLink.setTargetedScreen(originatingScreen);
			newScreen.getLinks().add(pairedLink);
			
			// migrate the ambience from the old screen to the new screen
			String oldMusicResource = originatingScreen.getBackgroundMusic();
			app.setBackgroundAmbience(newScreen, oldMusicResource);
			
			
			// reset the application values to move on with life
			applicationData.setCurrentScreen(newScreen);
			applicationData.setOriginatingScreen(null);
			applicationData.setNextLink(null);
			
			// start a new scene and see where it takes us
			startActivitySimple( ScreenActivity.class);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		

		
	}
}

package com.dasugames.eighthdaycollage.activity.menu;

import com.dasugames.eighthdaycollage.activity.dashboard.BrowseScreenActivity;
import com.dasugames.eighthdaycollage.activity.dashboard.ScreenActivity;
import com.dasugames.eighthdaycollage.activity.phonerecord.BasicTextActivity;
import com.dasugames.eighthdaycollage.data.ScreenData;
import com.dasugames.eighthdaycollage.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
/**
 * This activity is called from the EditMainActivity which is called from the main screen.
 * It supplies options on which the user can make changes to the current scenario.
 * @author darren.sue
 *
 */
public class EditScenarioActivity extends AbstractVerticalMenuWithTextHandler {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO I may have to move this to the parent Vertical Menu Activity
		// If not, this will be a great way to make sure that the buttons are not doing anything stupid.
		setContentView(R.layout.vertical_menu_layout);
		super.onCreate(savedInstanceState);
		
		button1.setText("Set start scene");
		button2.setText("Rename scenario"); 
		button3.setText("Save scenario");
		button4.setText("Load scenario");
		button5.setText("New scenario");
		button6.setText("Back");
		
		
		button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				app.getCurrentApplicationData().setOriginatingScreen(app.getCurrentApplicationData().getCurrentScreen());
				startActivitySimpleResult(BrowseScreenActivity.class,1);
			}

		});
		button2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivitySimpleResult( BasicTextActivity.class, 0);
			}
		});
		button3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startActivitySimple(SaveScenarioActivity.class);


			}

		});
		button4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// this starts up the screenactivity.
				startActivitySimple( LoadScenarioActivity2.class);

			}
		});
		button5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startActivitySimple(CreateNewScenarioActivity2.class);
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
		startActivitySimple(EditMainActivity.class);

	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 1 && resultCode == Activity.RESULT_OK && app.getCurrentApplicationData().getCurrentScreen() != null){
			ScreenData decidedScreen = app.getCurrentApplicationData().getCurrentScreen();
			app.getCurrentApplicationData().getSaveData().getCurrentScenario().setStartScreen(decidedScreen);
			startActivitySimple(ScreenActivity.class);
			
		}
	}

	@Override
	protected void handleGeneratedTextResult(String newStringName) {
		app.getCurrentApplicationData().getSaveData().getCurrentScenario().setScenarioName(newStringName);
		startActivitySimple(ScreenActivity.class);
	}
	
	
	

}

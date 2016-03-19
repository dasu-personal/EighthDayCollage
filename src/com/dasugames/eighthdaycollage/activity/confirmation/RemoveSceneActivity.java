package com.dasugames.eighthdaycollage.activity.confirmation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.dasugames.eighthdaycollage.activity.dashboard.ScreenActivity;
import com.dasugames.eighthdaycollage.activity.menu.EditSceneActivity;
import com.dasugames.eighthdaycollage.data.LinkData;
import com.dasugames.eighthdaycollage.data.ScreenData;
import com.dasugames.eighthdaycollage.utils.Utils;
import com.dasugames.eighthdaycollage.R;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * This is an activity created after the new link has been placed
 * to decide where the new link points to.
 * @author darren.sue
 */
public class RemoveSceneActivity extends ConfirmationMenuActivity {


	Set<ScreenData> inaccessableScreenData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.confirmation_layout);
		super.onCreate(savedInstanceState);
		
	

		getInaccessableScreens();
		if (app.getCurrentApplicationData().getCurrentScreen() == app.getCurrentApplicationData().getSaveData().getCurrentScenario().getStartScreen()) {
			textDescription.setText("Cannot remove start screen.");
			confirmButton.setEnabled(false);
		}
		else if (inaccessableScreenData.size() > 1){
			textDescription.setText("Remove screen and " + (inaccessableScreenData.size() - 1) + " more inaccessable screens?");
		} else {
			textDescription.setText("Remove screen?");
		}
		
		confirmButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				removeInaccessableScreens(inaccessableScreenData);
				removeCurrentScreenLinks();
				// TODO what activity should we start now?
				startActivitySimple(ScreenActivity.class);

			}
		});
		cancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				back();
			}
		});

	}


	private void getInaccessableScreens(){
		inaccessableScreenData = Utils.graphTraversalRemoveScreen(app.getCurrentApplicationData().getSaveData().getCurrentScenario(), app.getCurrentApplicationData().getCurrentScreen());
	}

	private void removeInaccessableScreens(Set<ScreenData> screenDataToRemove){
		List<String> inaccessableResources = Utils.getResourcesForScreens(screenDataToRemove,  getApplicationContext());
		for (String inaccessableResource : inaccessableResources) {
			app.getResourceManager().removeReference(inaccessableResource, getApplicationContext());
		}
		
		
	}
	
	private void removeCurrentScreenLinks() {
		ScreenData screenToRemove = app.getCurrentApplicationData().getCurrentScreen();

		List<LinkData> links = screenToRemove.getLinks();
		List<LinkData> copiedLinks = new ArrayList<LinkData>(links);
		for (LinkData link : copiedLinks){
			Utils.getRidOfLink(link);
		}
		
		//Utils.getRidOfScreen(screenToRemove,  getApplicationContext(), app.getCurrentApplicationData().getSaveData().getResourceManager());
		
		
		// TODO go to somewhere more fancy than the start screen
		app.getCurrentApplicationData().setCurrentScreen(app.getCurrentApplicationData().getSaveData().getCurrentScenario().getStartScreen());
		startActivitySimple(ScreenActivity.class);
	}
	
	
	
	protected void back() {
		
		startActivitySimple(EditSceneActivity.class);

	}
	

}

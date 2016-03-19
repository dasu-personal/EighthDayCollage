package com.dasugames.eighthdaycollage.activity.confirmation;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.dasugames.eighthdaycollage.EighthDayCollageApplication;
import com.dasugames.eighthdaycollage.activity.dashboard.BrowseScreenActivity;
import com.dasugames.eighthdaycollage.activity.dashboard.PickPathPoiToEditActivity;
import com.dasugames.eighthdaycollage.activity.dashboard.PlaceNewPathPoiActivity;
import com.dasugames.eighthdaycollage.activity.dashboard.ScreenActivity;
import com.dasugames.eighthdaycollage.activity.menu.CreateNewSceneActivity;
import com.dasugames.eighthdaycollage.activity.menu.EditLinkActivity;
import com.dasugames.eighthdaycollage.activity.menu.ImportScenarioActivity;
import com.dasugames.eighthdaycollage.activity.menu.VerticalMenuActivity;
import com.dasugames.eighthdaycollage.data.ApplicationData;
import com.dasugames.eighthdaycollage.data.LinkData;
import com.dasugames.eighthdaycollage.data.ScreenData;
import com.dasugames.eighthdaycollage.resource.ManagableResource;
import com.dasugames.eighthdaycollage.resource.MusicResource;
import com.dasugames.eighthdaycollage.resource.ResourceManager;
import com.dasugames.eighthdaycollage.utils.Utils;
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
public class RemoveLinkActivity extends ConfirmationMenuActivity {


	Set<ScreenData> inaccessableScreenData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.confirmation_layout);
		super.onCreate(savedInstanceState);
		


		getInaccessableScreens();

		if (inaccessableScreenData.size() > 0){
			textDescription.setText("Remove link and " + inaccessableScreenData.size() + " inaccessable screens?");
		} else {
			textDescription.setText("Remove link?");
		}
		
		
		confirmButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				removeInaccessableScreens(inaccessableScreenData);
				removeCurrentLink();
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
		inaccessableScreenData = Utils.graphTraversalRemoveLink(app.getCurrentApplicationData().getSaveData().getCurrentScenario(), app.getCurrentApplicationData().getNextLink());
	}
	
	private void removeInaccessableScreens(Set<ScreenData> screenDataToRemove){
		ResourceManager resourceManager = app.getResourceManager();
		List<String> inaccessableResources = Utils.getResourcesForScreens(screenDataToRemove, getApplicationContext());
		for (String inaccessableResource : inaccessableResources) {
			resourceManager.removeReference(inaccessableResource, getApplicationContext());
		}
	}
	
	private void removeCurrentLink() {
		Utils.getRidOfLink(app.getCurrentApplicationData().getNextLink());
		
		app.getCurrentApplicationData().setNextLink(null);
		
		// TODO go to somewhere more fancy than the start screen
		app.getCurrentApplicationData().setCurrentScreen(app.getCurrentApplicationData().getSaveData().getCurrentScenario().getStartScreen());
		startActivitySimple(ScreenActivity.class);
	}
	

	
	protected void back() {
		
		startActivitySimple(EditLinkActivity.class);

	}
	

}

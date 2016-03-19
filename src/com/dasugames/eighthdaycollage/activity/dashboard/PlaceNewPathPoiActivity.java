package com.dasugames.eighthdaycollage.activity.dashboard;



import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.dasugames.eighthdaycollage.EighthDayCollageApplication;
import com.dasugames.eighthdaycollage.activity.EighthDayCollageActivity;
import com.dasugames.eighthdaycollage.activity.menu.CreateNewLinkActivity;
import com.dasugames.eighthdaycollage.activity.menu.CreateNewPoiActivity;
import com.dasugames.eighthdaycollage.activity.menu.EditLinkActivity;
import com.dasugames.eighthdaycollage.activity.menu.EditMainActivity;
import com.dasugames.eighthdaycollage.activity.menu.EditPoiActivity;
import com.dasugames.eighthdaycollage.customview.PlacePathsView;
import com.dasugames.eighthdaycollage.customview.PlacePoiView;
import com.dasugames.eighthdaycollage.customview.eventlistener.NewPathsListener;
import com.dasugames.eighthdaycollage.customview.eventlistener.NewPoisListener;
import com.dasugames.eighthdaycollage.data.LinkData;
import com.dasugames.eighthdaycollage.data.PointOfInterestData;
import com.dasugames.eighthdaycollage.utils.Utils;
import com.dasugames.eighthdaycollage.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
/**
 * This is going to be one of the more complex activities.
 * The user will be presented with two areas of the screen,
 * each representing the POI section or the links section of
 * the given scene. By clicking on an active space, the user
 * will be able to create a new path or POI at the given
 * location.
 * @author darren.sue
 *
 */
public class PlaceNewPathPoiActivity extends EighthDayCollageActivity {

	private ImageView iv;
	EighthDayCollageApplication app;
	private Button backButton;
	private PlacePathsView createPaths;
	private PlacePoiView createPois;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_path_poi_layout);

		app = (EighthDayCollageApplication) this.getApplicationContext();
		backButton = (Button) findViewById(R.id.backButton);
		backButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				back();
			}
		});
		createPaths = (PlacePathsView) findViewById(R.id.navigationPane);
		createPaths.setNewPathsListener(new NewPathsListener(){
			@Override
			public void onClick(double angle) {
				// TODO fill this in with something meaningful
				createNewLink(angle);
			}
		});
		
		createPois = (PlacePoiView) findViewById(R.id.infoPane);
		createPois.setNewPoisListener(new NewPoisListener(){
			@Override
			public void onClick(float x, float y) {
				// TODO fill this in with something meaningful
				createNewPoi(x, y);
			}
		});
		
		Bitmap bitmap = app.getImage(app.getCurrentApplicationData().getCurrentScreen().getBackgroundImageStringKey());
		
		iv = (ImageView) findViewById(R.id.backgroundImage);
		iv.setImageBitmap(bitmap);
		
		
	}
	
	private void createNewLink(double angle){
		
		// TODO the big thing here is that I think that I'll need to have all of these work by activty result
		// rather than doing everything manually
		
		LinkData createdLink = new LinkData();
		createdLink.setDirection(angle);
		app.getCurrentApplicationData().setNextLink(createdLink);
		app.getCurrentApplicationData().setOriginatingScreen(app.getCurrentApplicationData().getCurrentScreen());
		// will generate a paired link and add this one to the originating screen when the target screen has been
		// created
		
		// TODO call the new link activity
		// TODO remember to have the view refresh everything since it may be a different screen
		// which is a point that only matters if it was a successful creation, in which we finish
		// the view anyways

		startActivitySimple(CreateNewLinkActivity.class);

	}
	
	private void createNewPoi(float x, float y){
		
		// TODO the big thing here is that I think that I'll need to have all of these work by activty result
		// rather than doing everything manually
		
		PointOfInterestData poiData = new PointOfInterestData();
		poiData.setX(x);
		poiData.setY(y);
		app.getCurrentApplicationData().setNextPoi(poiData);
		app.getCurrentApplicationData().setOriginatingScreen(app.getCurrentApplicationData().getCurrentScreen());
		// will generate a paired link and add this one to the originating screen when the target screen has been
		// created
		
		// TODO call the new link activity
		// TODO remember to have the view refresh everything since it may be a different screen
		// which is a point that only matters if it was a successful creation, in which we finish
		// the view anyways

		startActivitySimple(CreateNewPoiActivity.class);

	}
	
	@Override
	protected void back() {
		// TODO do I need to start up the options menu again?
		startActivitySimple(EditMainActivity.class);

	}
	




}

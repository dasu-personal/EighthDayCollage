package com.dasugames.eighthdaycollage.activity.dashboard;



import com.dasugames.eighthdaycollage.EighthDayCollageApplication;
import com.dasugames.eighthdaycollage.activity.menu.EditPoiActivity;
import com.dasugames.eighthdaycollage.customview.PathsView;
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
 * 
 * @author darren.sue
 *
 */
public class MovePoiActivity extends ScreenActivity {

	private ImageView iv;
	EighthDayCollageApplication app;
	private Button backButton;
	private PlacePathsView createPaths;
	private PlacePoiView createPois;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.move_poi_screen_layout);

		app = (EighthDayCollageApplication) this.getApplicationContext();
		backButton = (Button) findViewById(R.id.backButton);
		backButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				MovePoiActivity.this.back();
			}
		});
		pathsView = (PathsView) findViewById(R.id.navigationPane);
		addListenersToArrowButtons();
		
		createPois = (PlacePoiView) findViewById(R.id.infoPane);
		createPois.setNewPoisListener(new NewPoisListener(){
			@Override
			public void onClick(float x, float y) {
				// TODO fill this in with something meaningful
				replacePoi(x, y);
			}
		});
		
		Bitmap bitmap = app.getImage(app.getCurrentApplicationData().getCurrentScreen().getBackgroundImageStringKey());
		iv = (ImageView) findViewById(R.id.backgroundImage);
		iv.setImageBitmap(bitmap);
		
		
	}
	
	
	private void replacePoi(float x, float y){
		PointOfInterestData poiData = app.getCurrentApplicationData().getNextPoi();
		poiData.setX(x);
		poiData.setY(y);
		
		// TODO you'll probably want to remove the created link when you traverse from edit path to this
		// If you do, you'll need to add it back to the originating screen.
		app.getCurrentApplicationData().getOriginatingScreen().getInteractions().remove(poiData);
		app.getCurrentApplicationData().getCurrentScreen().getInteractions().add(poiData);
		
		app.getCurrentApplicationData().setNextPoi(null);
		app.getCurrentApplicationData().setOriginatingScreen(null);
		
		startActivitySimple(ScreenActivity.class);
	}
	protected void back() {
		startActivitySimple(EditPoiActivity.class);
	}
	
	



}

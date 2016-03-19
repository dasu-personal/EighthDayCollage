package com.dasugames.eighthdaycollage.activity.dashboard;



import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.dasugames.eighthdaycollage.EighthDayCollageApplication;
import com.dasugames.eighthdaycollage.activity.menu.EditLinkActivity;
import com.dasugames.eighthdaycollage.activity.menu.EditMainActivity;
import com.dasugames.eighthdaycollage.activity.menu.EditPoiActivity;
import com.dasugames.eighthdaycollage.customview.ArrowButtonView;
import com.dasugames.eighthdaycollage.customview.PathsView;
import com.dasugames.eighthdaycollage.customview.PoiButtonView;
import com.dasugames.eighthdaycollage.customview.PoiView;
import com.dasugames.eighthdaycollage.data.LinkData;
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
 * The paths and the POI on this screen are buttons
 * that can be tapped to edit the given individual
 * elements.
 * @author darren.sue
 *
 */
public class PickPathPoiToEditActivity extends ScreenActivity {


	private ImageView iv;
	EighthDayCollageApplication app;
	private Button backButton;
	private Button newButton;
	
	protected void addListenersToArrowButtons() {
		List<ArrowButtonView>arrowButtons =  pathsView.getArrowButtons();
		for (ArrowButtonView arrowButton : arrowButtons){
			arrowButton.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View arg0) {

					editSelectedArrow(((ArrowButtonView) arg0));
				}
			});

		}
	}
	
	protected void editSelectedArrow(ArrowButtonView arrowEdit){
		// a simplification as long as we are still going by one arrow per link
		// TODO this will need to be changed at some point
		editSelectedLink(arrowEdit.getLinkData().get(0));
	}
	
	protected void editSelectedLink(LinkData linkEdit){
		app.getCurrentApplicationData().setNextLink(linkEdit);
		
		
		startActivitySimple(EditLinkActivity.class);

		
	}
	
	protected void addListenersToPoiButtons(){
		List<PoiButtonView> poiButtons = poiView.getPoiButtons();
		for (PoiButtonView poiButton : poiButtons){
			poiButton.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View arg0) {

					editSelectedPoi(((PoiButtonView) arg0));
				}
			});
		}
	}
	
	protected void editSelectedPoi(PoiButtonView poiEdit){
		app.getCurrentApplicationData().setNextPoi(poiEdit.getPointOfInterestData());
	
		startActivitySimple(EditPoiActivity.class);

	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_path_poi_layout);

		app = (EighthDayCollageApplication) this.getApplicationContext();
		backButton = (Button) findViewById(R.id.backButton);
		backButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				back();

			}
		});
		
		newButton = (Button) findViewById(R.id.newButton);
		newButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

				startActivitySimple(PlaceNewPathPoiActivity.class);
			}
		});
		iv = (ImageView) findViewById(R.id.backgroundImage);
		//iv.setVisibility(View.INVISIBLE);
		
		Bitmap bitmap = app.getImage(app.getCurrentApplicationData().getCurrentScreen().getBackgroundImageStringKey());
		
		iv.setImageBitmap(bitmap);
		
		pathsView = (PathsView) findViewById(R.id.navigationPane);
		addListenersToArrowButtons();
		
		poiView = (PoiView) findViewById(R.id.infoPane);
		addListenersToPoiButtons();
		
		
	}
	
	@Override
	protected void back() {
		startActivitySimple(EditMainActivity.class);
	}
	

}

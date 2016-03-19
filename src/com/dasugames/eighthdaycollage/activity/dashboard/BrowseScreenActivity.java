package com.dasugames.eighthdaycollage.activity.dashboard;


import com.dasugames.eighthdaycollage.EighthDayCollageApplication;
import com.dasugames.eighthdaycollage.customview.PathsView;
import com.dasugames.eighthdaycollage.customview.PoiButtonView;
import com.dasugames.eighthdaycollage.customview.PoiView;
import com.dasugames.eighthdaycollage.data.LinkData;
import com.dasugames.eighthdaycollage.resource.MusicResource;
import com.dasugames.eighthdaycollage.utils.Utils;
import com.dasugames.eighthdaycollage.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.ImageView;

public class BrowseScreenActivity extends ScreenActivity {


	private Button selectButton;
	private Button backButton;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.browse_screen_layout);
		
		

		
		app = (EighthDayCollageApplication) this.getApplicationContext();
		selectButton = (Button) findViewById(R.id.selectScreenView);
		selectButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		
		backButton = (Button) findViewById(R.id.backButton);
		backButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				back();
			}
			
		});
		
		
		pathsView = (PathsView) findViewById(R.id.navigationPane);
		addListenersToArrowButtons();
		
		poiView = (PoiView) findViewById(R.id.infoPane);
		addListenersToPoiButtons();
		
		Bitmap bitmap = app.getImage(app.getCurrentApplicationData().getCurrentScreen().getBackgroundImageStringKey());

		// setting background from current screen data
		iv = (ImageView) findViewById(R.id.backgroundImage);
		iv.setImageBitmap(bitmap);
		
		// settuing up a view that is just going to be used for transitions anyways
		ivTransition = (ImageView) findViewById(R.id.transitionOutImage);
		ivTransition.setImageBitmap(bitmap);
		
	}
	
	

	
	@Override
	protected void back() {
		// this is a flag to communicate that the search was a failure
		// I think that this is too specialized to make more abstract
		app.getCurrentApplicationData().setCurrentScreen(null);
		isTransition = true;
		finish();
	}

}

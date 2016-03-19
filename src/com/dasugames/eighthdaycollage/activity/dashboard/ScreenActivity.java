package com.dasugames.eighthdaycollage.activity.dashboard;



import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.dasugames.eighthdaycollage.EighthDayCollageApplication;
import com.dasugames.eighthdaycollage.activity.EighthDayCollageActivity;
import com.dasugames.eighthdaycollage.activity.menu.EditMainActivity;
import com.dasugames.eighthdaycollage.customview.ArrowButtonView;
import com.dasugames.eighthdaycollage.customview.PathsView;
import com.dasugames.eighthdaycollage.customview.PoiButtonView;
import com.dasugames.eighthdaycollage.customview.PoiView;
import com.dasugames.eighthdaycollage.data.BackgroundMusicService;
import com.dasugames.eighthdaycollage.data.LinkData;
import com.dasugames.eighthdaycollage.data.MusicManager;
import com.dasugames.eighthdaycollage.data.PointOfInterestData;
import com.dasugames.eighthdaycollage.resource.MusicResource;
import com.dasugames.eighthdaycollage.utils.Utils;
import com.dasugames.eighthdaycollage.R;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ScreenActivity extends EighthDayCollageActivity {

	// TODO maybe this won't work
	//MediaPlayer mPlayerAmbience;
	MusicManager poiMusicManager;
	ImageView iv;
	ImageView ivTransition;
	EighthDayCollageApplication app;
	private Button editButton;
	// private MusicResource currentPoiResource; I don't think this is neccessary. It might be better to restart
	// the music file if the poi button is clicked again
	
	PathsView pathsView;
	PoiView poiView;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screen_layout);
		
		
		// given that all edit activities come back here, we can actually
		// autosave at this point without worrying
		// TODO maybe move this to onResume
		try {
			
			saveScenario();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// ERM...
			e.printStackTrace();
		}
		
		
		app = (EighthDayCollageApplication) this.getApplicationContext();
		editButton = (Button) findViewById(R.id.editScreenButton);
		editButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				/*
				// fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

				// Intent a = new Intent(MainActivity.this,
				// BasicCameraActivity.class);
				Intent a = new Intent(ScreenActivity.this, EditScreenActivity.class);
				a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				// startActivity(a);

				// a.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
				startActivity(a);
				ScreenActivity.this.finish();
				*/
				startActivitySimple(EditMainActivity.class);
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
		
		poiMusicManager = new MusicManager();
	}
	/*
	// TODO call this on screen entry
	protected void startAmbience() {
		// TODO lets see if this actually works
		// setting ambient sounds from current screen data
		

		

		MusicResource musicResource = app.getCurrentApplicationData().getCurrentScreen().getBackgroundMusic();
		if (musicResource != null) {
			try {
				backgroundMusicService.playMusic(musicResource);
			} catch (Exception e) {
				// This would indicate that the current screen no longer exists
				musicResource.removeReference();
				app.getCurrentApplicationData().getCurrentScreen().setBackgroundMusic(null);
			}
		} else {
			// this is neccessary when you traverse to an area without music
			stopAmbience();
		}

	}
	*/
	
	/*
	@Override
	public void onResume(){
		super.onResume();
		startAmbience();
	}
	*/
	
	@Override
	public void onPause() {
		super.onPause();

		stopPoi();
	}
	
	protected void stopAmbience(){
		/*
		if (backgroundMusicServiceBound){
			doUnbindService();
			backgroundMusicService.stopMusic();
		}
		*/
		app.getMusicAmbience().stopMusic();
		
		
	}
	
	protected void stopPoi(){
		poiMusicManager.stopMusic();
	}

	protected void addListenersToArrowButtons() {
		List<ArrowButtonView>arrowButtons =  pathsView.getArrowButtons();
		for (ArrowButtonView arrowButton : arrowButtons){
			arrowButton.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View arg0) {

					traverseArrowSimple(((ArrowButtonView) arg0));
				}
			});

		}
	}
	
	protected void addListenersToPoiButtons(){
		List<PoiButtonView> poiButtons = poiView.getPoiButtons();
		for (PoiButtonView poiButton : poiButtons){
			poiButton.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View arg0) {

					playPoiClip(((PoiButtonView) arg0));
				}
			});
		}
	}
	
	
	private void playPoiClip(PoiButtonView poiButton){
		PointOfInterestData poiData = poiButton.getPointOfInterestData();
		String musicResource = poiData.getSoundLogStringKey();
		if (musicResource != null){

			stopPoi();
	
			//mPlayerPoi.set
			try {
				Log.d("look at me", "try playing music with " + musicResource);
				poiMusicManager.playMusic(musicResource, false);
			} catch (Exception e) {
				// TODO will want to either remove the stack trace or get handle these other exceptions appropriately
				Log.d("look at me", e.toString());
				// this is removing the poi button
				
				app.getResourceManager().removeReference(musicResource, getApplicationContext());

				poiData.setSoundLogStringKey(null);
				app.getCurrentApplicationData().getCurrentScreen().getInteractions().remove(poiData);
				poiView.getPoiButtons().remove(poiButton);

				// TODO may want to invalidate stuff
				poiView.invalidate();
				
				return;

			}

		} else {
			Log.d("look at me", "try playing music with null. how cute.");
		}
	}
	
	// TODO activate this as the on click listener 
	private void traverseArrowSimple(ArrowButtonView arrowButton){
		List<LinkData> linkData = arrowButton.getLinkData();
		traverseLink(linkData.get(0)); // TODO this is not going to be this simple in the future
	}
	
	private void traverseLink( final LinkData linkData){
		
		final double direction = linkData.getDirection();
		
		
		handleExitAnimation(direction);
		
		// Setup the screen where we will be going next
		app.getCurrentApplicationData().setCurrentScreen(linkData.getTargetedScreen());
		
		
		// use that screen to set up the entrance animation
		handleEntranceAnimation(direction);
	}

	protected void handleExitAnimation(final double direction) {
		
		//ivTransition.setVisibility(View.VISIBLE);
		//ivTransition.clearAnimation();
		
		int height = ivTransition.getHeight();
		int width = ivTransition.getWidth();
		
		float yComponent =(float) Math.sin(direction*Math.PI/180);
		float xComponent =(float) Math.cos(direction*Math.PI/180);
		
		Animation a = new ScaleAnimation(1, 1/(1-yComponent*Y_TRANSITION_SCALE), 1, 1/(1-yComponent*Y_TRANSITION_SCALE), width / 2, height / 2);
		a.setDuration(500);
		Animation b = new AlphaAnimation(1, 0);
		b.setDuration(500);
		Animation c = new TranslateAnimation(0,-width*xComponent*X_TRANSITION_SCALE, 0, 0);
		c.setDuration(500);
		AnimationSet totalAnimation = new AnimationSet(false);
		totalAnimation.addAnimation(a);
		totalAnimation.addAnimation(b);
		totalAnimation.addAnimation(c);
		//totalAnimation.setFillBefore(true);
		//totalAnimation.setFillAfter(true);
		//totalAnimation.setStartOffset(50);


		ivTransition.startAnimation(totalAnimation);
	}
	
	
	private final float X_TRANSITION_SCALE = 1.0f; // should be one or less
	private final float Y_TRANSITION_SCALE = 0.5f; // must be less than one
	
	private void handleEntranceAnimation(double direction){
		
		iv.clearAnimation();
		
		
		

		
		
		int height = iv.getHeight();
		int width = iv.getWidth();
		
		float yComponent =(float) Math.sin(direction*Math.PI/180);
		float xComponent =(float) Math.cos(direction*Math.PI/180);
		
		
		Animation a = new ScaleAnimation(1/(1+yComponent*Y_TRANSITION_SCALE), 1f, 1/(1+yComponent*Y_TRANSITION_SCALE), 1f, width/2, height / 2);
		a.setDuration(500);
		Animation b = new AlphaAnimation(0, 1);
		b.setDuration(500);
		Animation c = new TranslateAnimation(width*xComponent*X_TRANSITION_SCALE, 0, 0, 0);
		c.setDuration(500);
		AnimationSet totalAnimation = new AnimationSet(false);
		totalAnimation.addAnimation(a);
		totalAnimation.addAnimation(b);
		totalAnimation.addAnimation(c);
		//totalAnimation.setFillBefore(true);
		//totalAnimation.setFillAfter(true);
		//totalAnimation.setStartOffset(50);
		Bitmap bitmap = app.getImage(app.getCurrentApplicationData().getCurrentScreen().getBackgroundImageStringKey());
		iv.setImageBitmap(bitmap);
		
		totalAnimation.setAnimationListener(new AnimationListener(){

			@Override
			public void onAnimationStart(Animation animation) {
				// nothing
				beforeTransition();
				
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				afterTransition();
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// Nothing
				
			}
			
		});

		iv.startAnimation(totalAnimation);
		

	}

	protected void afterTransition() {
		// After transition

		startAmbience();
		
		
		// need to get the new arrows after the movement
		pathsView.refreshArrows();
		addListenersToArrowButtons();
		
		// need to get the new poi after the movement
		poiView.refreshArrows();
		addListenersToPoiButtons();
		
		
		// TODO should re-enable buttons
		
		Bitmap bitmap = app.getImage(app.getCurrentApplicationData().getCurrentScreen().getBackgroundImageStringKey());
		ivTransition.setImageBitmap(bitmap);
	}
	
	protected void beforeTransition() {
		stopPoi();
		//stopAmbience();
		
		// TODO Should do something about the buttons
	}
	
	@Override
	protected void back(){
		// we want to leave the application entirely
		// TODO maybe we want to do a quit menu instead
		finish();
	}
	

	@Override
	protected void screenOut() {
		stopPoi();
	}

}

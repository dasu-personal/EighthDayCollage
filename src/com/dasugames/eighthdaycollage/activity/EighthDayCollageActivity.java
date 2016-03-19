package com.dasugames.eighthdaycollage.activity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import com.dasugames.eighthdaycollage.EighthDayCollageApplication;
import com.dasugames.eighthdaycollage.activity.dashboard.ScreenActivity;
import com.dasugames.eighthdaycollage.activity.menu.EditMainActivity;
import com.dasugames.eighthdaycollage.activity.menu.TitleActivity;
import com.dasugames.eighthdaycollage.data.SaveData;
import com.dasugames.eighthdaycollage.data.ScenarioData;
import com.dasugames.eighthdaycollage.resource.MusicResource;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public abstract class EighthDayCollageActivity extends Activity {
	
	protected boolean isTransition = false;
	protected EighthDayCollageApplication app;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		app = (EighthDayCollageApplication) this.getApplicationContext();
	}
	
	@Override
	public void onResume(){
		super.onResume();
		checkStatus();
		startAmbience();
	}

	/**
	 * This is necessary given that android may choose to wipe the application
	 * context if enough time has passed. In cases like those, we should just
	 * start over. 
	 */
	private void checkStatus(){
		if (app.getCurrentApplicationData() == null){
			startActivitySimple(TitleActivity.class);
		}
	}
	
	
	protected void startAmbience() {
		// restart music if necessary
		String mr = app.getCurrentApplicationData().getCurrentScreen()
				.getBackgroundMusic();
		if (mr != null) {
			try {

				Log.d("look at me", "try playing music with "
						+ mr);
				app.getMusicAmbience().playMusic(mr, true);
			} catch (Exception e) {
				Log.d("look at me", e.toString());
				app.getCurrentApplicationData().getCurrentScreen()
						.setBackgroundMusic(null);
				app.getMusicAmbience().stopMusic();
				return;

			}
		} else {
			Log.d("look at me", "try playing music with null. how cute.");
		}

	}

	@Override
	public void onPause() {
		super.onPause();
		screenOut();
		if (isTransition) {
			applicationActivityTransition();
			isTransition = false;
		} else {
			applicationQuit();
		}
		

	}
	
	protected void startActivitySimple(Class<? extends Activity> activityToStart) {
		isTransition = true;
		Intent a = new Intent(this, activityToStart);
		a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(a);
		this.finish();
	}
	
	protected void startActivitySimpleResult(Class<? extends Activity> activityToStart, int requestCode) {
		Intent a = new Intent(this, activityToStart);
		a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		isTransition = true;
		startActivityForResult(a, requestCode);
	}
	
	
	
	protected void screenOut() {
		
	}
	
	protected void applicationQuit() {
		// I need to stop the music here.
		app.getMusicAmbience().stopMusic();
		
	}
	
	protected void applicationActivityTransition() {
		
	}
	
	@Override
	public void onBackPressed() {
		this.back();
	}
	
	protected abstract void back();
	
	
	public void saveScenario() throws IOException {
		FileOutputStream fos;
		Log.d("save", "create save data");
		SaveData saveData = app.getCurrentApplicationData().getSaveData();
		Log.d("save", "try to save");
		fos = app.openFileOutput("saveData", Context.MODE_PRIVATE);
		ObjectOutputStream os = new ObjectOutputStream(fos);
		os.writeObject(saveData);
		os.close();
		fos.close();
		Log.d("save", "save success");

	}
	
	public void loadScenario() throws StreamCorruptedException, IOException, ClassNotFoundException {
			Log.d("save", "load save data start");
			FileInputStream fis = app.openFileInput("saveData");
			ObjectInputStream is = new ObjectInputStream(fis);
			SaveData saveData = (SaveData) is.readObject();
			
			is.close();
			fis.close();
			Log.d("save", "load save data success");
			
			app.getCurrentApplicationData().setSaveData(saveData);
	}
	

}

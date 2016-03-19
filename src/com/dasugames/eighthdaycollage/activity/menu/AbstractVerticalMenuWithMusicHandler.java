package com.dasugames.eighthdaycollage.activity.menu;


import com.dasugames.eighthdaycollage.EighthDayCollageApplication;
import com.dasugames.eighthdaycollage.resource.MusicResource;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public abstract class AbstractVerticalMenuWithMusicHandler extends VerticalMenuActivity {
	private static String TAG = "8Day";
	EighthDayCollageApplication app;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		app = (EighthDayCollageApplication) this.getApplicationContext();
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode != 0){
			return;
		}
		if (resultCode != Activity.RESULT_OK) {
			return;
		}
		if (data != null) {
			String fileName = data.getExtras().getString("data");
			handleGeneratedMusicResource(fileName);
		}
	}
	
	protected abstract void handleGeneratedMusicResource(String musicResource);

	protected void startActivitySimpleResult(Class<? extends Activity> activityToStart, int requestCode) {
		Intent a = new Intent(this, activityToStart);
		a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		// This is because for once we want to turn the music off.
		isTransition = false;
		startActivityForResult(a, requestCode);
	}
	
}

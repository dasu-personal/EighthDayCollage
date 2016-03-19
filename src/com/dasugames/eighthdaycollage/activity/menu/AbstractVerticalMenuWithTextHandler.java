package com.dasugames.eighthdaycollage.activity.menu;


import com.dasugames.eighthdaycollage.EighthDayCollageApplication;
import com.dasugames.eighthdaycollage.resource.MusicResource;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public abstract class AbstractVerticalMenuWithTextHandler extends VerticalMenuActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		app = (EighthDayCollageApplication) this.getApplicationContext();
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
        Log.d("text", "is result text");
		if (requestCode != 0 || resultCode != Activity.RESULT_OK){
			return;
		}
        Log.d("text", "result is text");
		
		if (data != null) {
			
			String textResult = data.getExtras().getString("data");
	        Log.d("text", "ifound text: " + textResult);
			if (textResult == null || textResult.trim().length() == 0){
				return;
			}

			handleGeneratedTextResult(textResult);
		}
	}
	
	protected abstract void handleGeneratedTextResult(String newTextName);

	protected void startActivitySimpleResult(Class<? extends Activity> activityToStart, int requestCode) {
		Intent a = new Intent(this, activityToStart);
		a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		// This is because for once we want to turn the music off.
		isTransition = false;
		startActivityForResult(a, requestCode);
	}
	
}

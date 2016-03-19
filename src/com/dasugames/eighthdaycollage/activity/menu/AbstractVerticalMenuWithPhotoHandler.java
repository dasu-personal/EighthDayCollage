package com.dasugames.eighthdaycollage.activity.menu;

import java.io.FileNotFoundException;

import com.dasugames.eighthdaycollage.EighthDayCollageApplication;
import com.dasugames.eighthdaycollage.data.ApplicationData;
import com.dasugames.eighthdaycollage.data.LinkData;
import com.dasugames.eighthdaycollage.data.ScreenData;
import com.dasugames.eighthdaycollage.resource.ImageResource;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public abstract class AbstractVerticalMenuWithPhotoHandler extends VerticalMenuActivity {
	private static String TAG = "8Day";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode != Activity.RESULT_OK) {
			return;
		}
		
		if (data != null) {

			
			// Setup the new bitmap used by a new screendata
			//Bitmap bp = (Bitmap) data.getExtras().get("data");
			String fileName = data.getExtras().getString("data");
			handleGeneratedImageResource(fileName);
		}
	}
	
	protected abstract void handleGeneratedImageResource(String imageResource);

}

package com.dasugames.eighthdaycollage.activity.menu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import com.dasugames.eighthdaycollage.activity.dashboard.ScreenActivity;
import com.dasugames.eighthdaycollage.activity.phonerecord.BasicCameraActivity;
import com.dasugames.eighthdaycollage.data.ScenarioData;
import com.dasugames.eighthdaycollage.data.ScreenData;
import com.dasugames.eighthdaycollage.R;

import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class CreateNewScenarioActivity extends AbstractVerticalMenuWithPhotoHandler {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.vertical_menu_layout);
		super.onCreate(savedInstanceState);
		
		button1.setText("Take new photo with camera");
		button2.setText("Import photo from phone");
		button3.setText("Import photo from save"); 
		button4.setText("Import scenario from save");
		button5.setText("Import scenario over wifi");
		button6.setText("Back");
		
		// TODO do something about each of these functionalities
		button2.setEnabled(false);
		button3.setEnabled(false);
		button4.setEnabled(false);
		button5.setEnabled(false);
		
		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

				// Intent a = new Intent(MainActivity.this,
				// BasicCameraActivity.class);
				startActivitySimpleResult(BasicCameraActivity.class,0);

			}
		});
		button2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO fill this in with something meaningful
			}
		});
		button3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO fill this in with something meaningful
			}
		});
		button4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO fill this in with something meanningful
			}
		});
		button5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO fill this in with something meaningful
			}
		});
		button6.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// this just goes back to the previous screen
				CreateNewScenarioActivity.this.finish();
			}
		});

	}

	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/*
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data != null) {
			
			String fileName = data.getExtras().getString("data");
			ScreenData newScreen;
			try {
				Log.d(TAG, "try to get recorded file with " + fileName);
				newScreen = new ScreenData(fileName, this.getApplicationContext());
				
				// TODO lets see if that works
				// TODO can't be helped I guess
				InputStream in = getResources().openRawResource(R.raw.music_test_fun);
				String sep = File.separator;
				String newFolder = "eighthdaycollage";
				String externalPath = Environment.getExternalStorageDirectory().getAbsolutePath();
				File myNewFolder = new File(externalPath + sep + newFolder);
				myNewFolder.mkdir();
				String audioFileString =  externalPath+sep+newFolder+sep + UUID.randomUUID().toString() + ".m4a";
				FileOutputStream out = new FileOutputStream(audioFileString);
				byte[] buff = new byte[1024];
				int read = 0;

				try {
				   while ((read = in.read(buff)) > 0) {
				      out.write(buff, 0, read);
				   }
				   in.close();
				   out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				newScreen.setBackgroundMusic(new MusicResource(audioFileString));
				
				// TODO handle autosave
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				Log.d(TAG, "Could not create screen due to not being able to get the screen data out of the internal storage");
				e.printStackTrace();
				return;
			}
			ScenarioData newScenario =  new ScenarioData();
			newScenario.setStartScreen(newScreen);
			app.getCurrentApplicationData().getSaveData().setCurrentScenario(newScenario);
			app.getCurrentApplicationData().setCurrentScreen(newScreen);
			startActivitySimple( ScreenActivity.class);
		}
		// TODO maybe you want to spit out some error message here or something
	}
	*/
	
	@Override
	protected void back() {
		startActivitySimple(TitleActivity.class);
	}
	

	@Override
	protected void handleGeneratedImageResource(String imageResource) {

		ScreenData newScreen;
		try {
			newScreen = new ScreenData();
			app.setBackgroundImage(newScreen, imageResource);
			ScenarioData newScenario = new ScenarioData();
			newScenario.setStartScreen(newScreen);
			ScenarioData oldScenario = app.getCurrentApplicationData().getSaveData().getCurrentScenario();
			if (oldScenario != null) {
				app.graphTraversalRemoveReference(oldScenario);
			}

			app.getCurrentApplicationData().getSaveData().setCurrentScenario(newScenario);
			app.getCurrentApplicationData().setCurrentScreen(newScreen);
			
			createDefaultMusic(newScreen);
			
			startActivitySimple(ScreenActivity.class);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			// in general, we should not progress in that state
			e.printStackTrace();
		}
		
	}
	
	
	protected void createDefaultMusic(ScreenData screenData) {

		InputStream in = getResources().openRawResource(R.raw.music_test_fun);
		String sep = File.separator;
		String newFolder = "eighthdaycollage";
		String externalPath = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
		File myNewFolder = new File(externalPath + sep + newFolder);
		myNewFolder.mkdir();
		String audioFileString = externalPath + sep + newFolder + sep
				+ UUID.randomUUID().toString() + ".m4a";
		FileOutputStream out;
		try {
			out = new FileOutputStream(audioFileString);

			byte[] buff = new byte[1024];
			int read = 0;
			try {
				while ((read = in.read(buff)) > 0) {
					out.write(buff, 0, read);
				}
				
				in.close();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			app.setBackgroundAmbience(screenData, audioFileString);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 

	}
}

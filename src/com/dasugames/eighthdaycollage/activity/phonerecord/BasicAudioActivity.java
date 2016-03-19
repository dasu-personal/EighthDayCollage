package com.dasugames.eighthdaycollage.activity.phonerecord;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.dasugames.eighthdaycollage.activity.menu.VerticalMenuActivity;
import com.dasugames.eighthdaycollage.resource.MusicResource;
import com.dasugames.eighthdaycollage.R;

public class BasicAudioActivity extends VerticalMenuActivity {
	


	private static final String LOG_TAG = "AudioRecordTest";
    private String audioFileString;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.vertical_menu_layout);
		super.onCreate(savedInstanceState);


		super.onCreate(savedInstanceState);

		
		
		button1.setText("Start recording");
		button2.setText("Complete recording");
		button2.setEnabled(false);
		button3.setText("Back");
		// button4.setVisibility(View.INVISIBLE);
		button4.setEnabled(false);
		button5.setEnabled(false);
		button6.setEnabled(false);
		
		// Rather unique to the Audio actxivity, we can't have noisy buttons
		button1.setSoundEffectsEnabled(false);
		button2.setSoundEffectsEnabled(false);
		button3.setSoundEffectsEnabled(true);
		
		button1.setOnClickListener(new OnClickListener() {
			@Override
	           public void onClick(View arg0) {
	                startRecording();
	           }
		});

		button2.setOnClickListener(new OnClickListener() {
			@Override
	           public void onClick(View arg0) {
	                completeRecording();
	           }
		});
		
		button3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// this just goes back to the previous screen
				finish();
				
			}
		});
		String sep = File.separator;
		String newFolder = "eighthdaycollage";
		String externalPath = Environment.getExternalStorageDirectory().getAbsolutePath();
		File myNewFolder = new File(externalPath + sep + newFolder);
		myNewFolder.mkdir();
		audioFileString =  externalPath+sep+newFolder+sep + UUID.randomUUID().toString() + ".m4a";
		//audioFileString =   "/" + "test" + ".mpa";
		
	}
	



    private MediaRecorder mRecorder = null;

    private void startRecording() {
    	button1.setEnabled(false);
    	button2.setEnabled(true);
    	
    	if (mRecorder != null){
            mRecorder.stop();
    		mRecorder.release();
    		mRecorder = null;
    	}
    	
    	Log.d(LOG_TAG, "setup media file");
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        Log.d(LOG_TAG, "try recording with " +  audioFileString);
        mRecorder.setOutputFile( audioFileString);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);


       
        try {
            mRecorder.prepare();
            mRecorder.start();
            Log.d(LOG_TAG, "prepare() and start() succeed");
        } catch (IOException e) {
            Log.d(LOG_TAG, "prepare() failed");
            mRecorder.reset();
            mRecorder.release();
            mRecorder = null;
            button1.setEnabled(true);
        	button2.setEnabled(false);
        }
    }

    private void completeRecording() {
    	// I think that this is too specialized to put in an abstract class
    	button1.setEnabled(true);
    	button2.setEnabled(false);
        mRecorder.stop();
        mRecorder.reset();
        mRecorder.release();
        mRecorder = null;
        
        Intent resultIntent = new Intent();
        resultIntent.putExtra("data", audioFileString);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }





    @Override
    public void onPause() {
        super.onPause();
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }


    }

	@Override
	protected void back() {
		Intent resultIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, resultIntent);
        finish();
		
	}
	
	protected void startAmbience() {
		// shouldn't play music since we are recording sound
	}

}


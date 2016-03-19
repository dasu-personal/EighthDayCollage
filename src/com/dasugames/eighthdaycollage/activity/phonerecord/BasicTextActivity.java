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
import android.widget.EditText;

import com.dasugames.eighthdaycollage.activity.EighthDayCollageActivity;
import com.dasugames.eighthdaycollage.activity.menu.VerticalMenuActivity;
import com.dasugames.eighthdaycollage.resource.MusicResource;
import com.dasugames.eighthdaycollage.R;

public class BasicTextActivity extends EighthDayCollageActivity {
	

	Button confirmButton;
	Button cancelButton;
	EditText textEntry;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.basic_text_record_layout);
		super.onCreate(savedInstanceState);

		confirmButton = (Button) findViewById(R.id.confirm_button);
		cancelButton = (Button) findViewById(R.id.back_button);
		textEntry = (EditText) findViewById(R.id.text_entry);

		confirmButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				completeTextEntry();
			}
		});

		cancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				back();
			}
		});
	}
	






    private void completeTextEntry() {
    	// I think that this is too specialized to put in an abstract class

        
        Intent resultIntent = new Intent();
        Log.d("text", "textgetting saved as: " + textEntry.getText().toString());
        resultIntent.putExtra("data", textEntry.getText().toString());
        setResult(Activity.RESULT_OK, resultIntent);
        
        finish();
    }

	@Override
	protected void back() {
		Intent resultIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, resultIntent);
        finish();
		
	}

}


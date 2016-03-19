package com.dasugames.eighthdaycollage.activity.confirmation;

import com.dasugames.eighthdaycollage.activity.EighthDayCollageActivity;
import com.dasugames.eighthdaycollage.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;


/**
 * This is just a helper activity for any menu layouts with
 * five button stacked vertically.
 * I imagine that i
 * @author darren.sue
 *
 */
public abstract class ConfirmationMenuActivity extends EighthDayCollageActivity {

	protected Button confirmButton;
	protected Button cancelButton;
	protected TextView textDescription;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		confirmButton = (Button) findViewById(R.id.confirmButton);
		cancelButton = (Button) findViewById(R.id.cancelButton);
		textDescription = (TextView) findViewById(R.id.descriptionText);
		
	}

}

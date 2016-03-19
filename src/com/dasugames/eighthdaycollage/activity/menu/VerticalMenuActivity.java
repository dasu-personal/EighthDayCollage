package com.dasugames.eighthdaycollage.activity.menu;

import com.dasugames.eighthdaycollage.activity.EighthDayCollageActivity;
import com.dasugames.eighthdaycollage.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;


/**
 * This is just a helper activity for any menu layouts with
 * five button stacked vertically.
 * I imagine that i
 * @author darren.sue
 *
 */
public abstract class VerticalMenuActivity extends EighthDayCollageActivity {

	protected Button button1;
	protected Button button2;
	protected Button button3;
	protected Button button4;
	protected Button button5;
	protected Button button6;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		button1  = (Button) findViewById(R.id.button1);
		button2  = (Button) findViewById(R.id.button2);
		button3  = (Button) findViewById(R.id.button3);
		button4  = (Button) findViewById(R.id.button4);
		button5  = (Button) findViewById(R.id.button5);
		button6  = (Button) findViewById(R.id.button6);
		
	}

}

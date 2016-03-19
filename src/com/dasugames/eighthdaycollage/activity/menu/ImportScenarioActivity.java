package com.dasugames.eighthdaycollage.activity.menu;



import com.dasugames.eighthdaycollage.EighthDayCollageApplication;
import com.dasugames.eighthdaycollage.R;

import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class ImportScenarioActivity extends VerticalMenuActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO I may have to move this to the parent Vertical Menu Activity
		// If not, this will be a great way to make sure that the buttons are not doing anything stupid.
		setContentView(R.layout.vertical_menu_layout);
		super.onCreate(savedInstanceState);
		
		app = (EighthDayCollageApplication) this.getApplicationContext();
		
		button1.setText("Import scenario from save");
		button2.setText("Import scenario over wifi");
		button3.setText("Back");
		
		// TODO do something about each of these functionalities
		button1.setEnabled(false);
		button2.setEnabled(false);
		button4.setEnabled(false);
		button5.setEnabled(false);
		button6.setEnabled(false);
		
		button2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO fill this in with something meaningful
			}
		});
		button3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				back();
			}
		});
		button4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
			}
		});
		button5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO probably doesn't need to be here
			}
		});
		button6.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO probably doesn't need to be here
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


	@Override
	protected void back() {
		startActivitySimple( CreateNewSceneActivity.class);

	}
	
}

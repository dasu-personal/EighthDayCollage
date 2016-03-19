package com.dasugames.eighthdaycollage.activity.menu;



import com.dasugames.eighthdaycollage.EighthDayCollageApplication;
import com.dasugames.eighthdaycollage.activity.dashboard.ScreenActivity;
import com.dasugames.eighthdaycollage.data.ScenarioData;
import com.dasugames.eighthdaycollage.data.ScreenData;
import com.dasugames.eighthdaycollage.utils.Utils;
import com.dasugames.eighthdaycollage.R;

import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LoadScenarioActivity extends VerticalMenuActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO I may have to move this to the parent Vertical Menu Activity
		// If not, this will be a great way to make sure that the buttons are not doing anything stupid.
		setContentView(R.layout.vertical_menu_layout);
		super.onCreate(savedInstanceState);
		
		app = (EighthDayCollageApplication) this.getApplicationContext();
		

		button6.setText("Back");
		
		checkSavedScenarios();
		

		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				setLoadedScenario(1);
			}
		});
		button2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				setLoadedScenario(2);
			}
		});
		button3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				setLoadedScenario(3);
				
			}
		});
		button4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				setLoadedScenario(4);
			}
		});
		button5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				setLoadedScenario(5);
			}
		});
		button6.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				back();
			}
		});

	}

	private void checkSavedScenarios() {
		decorateScenarioButtons(button1, 1);
		decorateScenarioButtons(button2, 2);
		decorateScenarioButtons(button3, 3);
		decorateScenarioButtons(button4, 4);
		decorateScenarioButtons(button5, 5);
		
	}
	
	private void decorateScenarioButtons(Button button, int index){
		ScenarioData[] savedScenarios = app.getCurrentApplicationData().getSaveData().getSavedScenarios();
		if (savedScenarios[index - 1] == null) {
			button.setEnabled(false);
			button.setText("No data available");
			return;
		}
		ScenarioData currentScenario = savedScenarios[index - 1];
		if (currentScenario.getScenarioName() == null){
			button.setText("Load scenario from slot " + index);
		} else {
			button.setText("Load scenario " + currentScenario.getScenarioName());
		}
	}
	
	private void setLoadedScenario(int scenarioIndex){
		ScenarioData loadingScenario = app.getCurrentApplicationData().getSaveData().getSavedScenarios()[scenarioIndex - 1];
		app.graphTraversalAddReference(loadingScenario);
		app.graphTraversalRemoveReference(app.getCurrentApplicationData().getSaveData().getCurrentScenario());
		app.getCurrentApplicationData().getSaveData().setCurrentScenario(loadingScenario);
		app.getCurrentApplicationData().setCurrentScreen(loadingScenario.getStartScreen());
		
		// either 
		startActivitySimple(ScreenActivity.class);
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
		startActivitySimple(TitleActivity.class);
		
	}
	
}

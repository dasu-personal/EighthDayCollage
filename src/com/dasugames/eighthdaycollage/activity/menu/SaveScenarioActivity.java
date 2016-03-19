package com.dasugames.eighthdaycollage.activity.menu;



import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.dasugames.eighthdaycollage.EighthDayCollageApplication;
import com.dasugames.eighthdaycollage.activity.dashboard.ScreenActivity;
import com.dasugames.eighthdaycollage.data.ScenarioData;
import com.dasugames.eighthdaycollage.data.ScreenData;
import com.dasugames.eighthdaycollage.utils.Utils;
import com.dasugames.eighthdaycollage.R;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SaveScenarioActivity extends VerticalMenuActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO I may have to move this to the parent Vertical Menu Activity
		// If not, this will be a great way to make sure that the buttons are not doing anything stupid.
		setContentView(R.layout.vertical_menu_layout);
		super.onCreate(savedInstanceState);
		
		app = (EighthDayCollageApplication) this.getApplicationContext();
		

		
		
		loadScenarioText();
		button6.setText("Back");


		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				saveOverScenario(1);

			}
		});
		button2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				saveOverScenario(2);

			}
		});
		button3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				saveOverScenario(3);
				
				
			}
		});
		button4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				saveOverScenario(4);
			}
		});
		button5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				saveOverScenario(5);
			}
		});
		button6.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				back();
			}
		});

	}

	private void loadScenarioText() {
		decorateScenarioButtons(button1, 1);
		decorateScenarioButtons(button2, 2);
		decorateScenarioButtons(button3, 3);
		decorateScenarioButtons(button4, 4);
		decorateScenarioButtons(button5, 5);
	}
	
	
	private void decorateScenarioButtons(Button button, int index){
		ScenarioData[] savedScenarios = app.getCurrentApplicationData().getSaveData().getSavedScenarios();
		if (savedScenarios[index - 1] == null){
			button.setText("Save over empty file " + index);
			return;
		}
		ScenarioData currentScenario = savedScenarios[index-1];
		if (currentScenario.getScenarioName() == null){
			button.setText("Save over scenario " + index);
		} else {
			button.setText("Save over scenario " + currentScenario.getScenarioName());
		}
	}
	
	private void saveOverScenario(int index){
		ScenarioData oldData = app.getCurrentApplicationData().getSaveData().getSavedScenarios()[index - 1];
		ScenarioData savingData = app.getCurrentApplicationData().getSaveData().getCurrentScenario();
		app.graphTraversalAddReference(savingData);
		
		// TODO put this in utility method
		try {
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos;
			oos = new ObjectOutputStream(baos);
			
			oos.writeObject(savingData);

			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			ScenarioData savingDataClone = (ScenarioData) ois.readObject();
			
			app.getCurrentApplicationData().getSaveData().getSavedScenarios()[index - 1] = savingDataClone;
			if (oldData != null){
				app.graphTraversalRemoveReference(oldData);
			}
			
			// either I can go back to the main activity where the changes automatically get saved, or I can do that here
			startActivitySimple(ScreenActivity.class);
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
		startActivitySimple(EditScenarioActivity.class);
		
	}
	
}

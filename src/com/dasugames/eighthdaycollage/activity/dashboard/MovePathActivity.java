package com.dasugames.eighthdaycollage.activity.dashboard;



import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.dasugames.eighthdaycollage.EighthDayCollageApplication;
import com.dasugames.eighthdaycollage.activity.menu.CreateNewLinkActivity;
import com.dasugames.eighthdaycollage.activity.menu.CreateNewPoiActivity;
import com.dasugames.eighthdaycollage.activity.menu.EditLinkActivity;
import com.dasugames.eighthdaycollage.customview.PlacePathsView;
import com.dasugames.eighthdaycollage.customview.PlacePoiView;
import com.dasugames.eighthdaycollage.customview.PoiView;
import com.dasugames.eighthdaycollage.customview.eventlistener.NewPathsListener;
import com.dasugames.eighthdaycollage.customview.eventlistener.NewPoisListener;
import com.dasugames.eighthdaycollage.data.LinkData;
import com.dasugames.eighthdaycollage.data.PointOfInterestData;
import com.dasugames.eighthdaycollage.utils.Utils;
import com.dasugames.eighthdaycollage.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
/**
 * This is going to be one of the more complex activities.
 * The user will be presented with two areas of the screen,
 * each representing the POI section or the links section of
 * the given scene. By clicking on an active space, the user
 * will be able to create a new path or POI at the given
 * location.
 * @author darren.sue
 *
 */
public class MovePathActivity extends ScreenActivity {

	static final private int MEDIA_TYPE_IMAGE = 1;
	private Uri fileUri;
	private ImageView iv;
	EighthDayCollageApplication app;
	private Button backButton;
	private PlacePathsView createPaths;
	private PlacePoiView createPois;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.move_path_screen_layout);

		app = (EighthDayCollageApplication) this.getApplicationContext();
		backButton = (Button) findViewById(R.id.backButton);
		backButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				back();
			}


		});
		createPaths = (PlacePathsView) findViewById(R.id.navigationPane);
		createPaths.setNewPathsListener(new NewPathsListener(){
			@Override
			public void onClick(double angle) {
				// TODO fill this in with something meaningful
				replaceLink(angle);
			}
		});
		
		poiView = (PoiView) findViewById(R.id.infoPane);
		addListenersToPoiButtons();
		
		
		Bitmap bitmap = app.getImage(app.getCurrentApplicationData().getCurrentScreen().getBackgroundImageStringKey());
		
		iv = (ImageView) findViewById(R.id.backgroundImage);
		iv.setImageBitmap(bitmap);
		
		
	}
	
	protected void back() {
		startActivitySimple(EditLinkActivity.class);
	}

	private void replaceLink(double angle){

		
		LinkData createdLink = app.getCurrentApplicationData().getNextLink();
		createdLink.setDirection(angle);
		// TODO you'll probably want to remove the created link when you traverse from edit path to this
		// If you do, you'll need to add it back to the originating screen.
		// obviously entire process is almost trivial for the link case, but I do want it to be consistant
		// with the poi case
		app.getCurrentApplicationData().getOriginatingScreen().getLinks().remove(createdLink);
		app.getCurrentApplicationData().getCurrentScreen().getLinks().add(createdLink);
		

		app.getCurrentApplicationData().setOriginatingScreen(null);
		app.getCurrentApplicationData().setNextLink(null);

		startActivitySimple(ScreenActivity.class);
	}

	
	



}

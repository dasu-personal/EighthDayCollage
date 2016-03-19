package com.dasugames.eighthdaycollage.activity.menu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.dasugames.eighthdaycollage.EighthDayCollageApplication;
import com.dasugames.eighthdaycollage.activity.dashboard.PickPathPoiToEditActivity;
import com.dasugames.eighthdaycollage.activity.dashboard.ScreenActivity;
import com.dasugames.eighthdaycollage.activity.phonerecord.BasicCameraActivity;
import com.dasugames.eighthdaycollage.data.ScenarioData;
import com.dasugames.eighthdaycollage.data.ScreenData;
import com.dasugames.eighthdaycollage.resource.DefaultMusicResource;
import com.dasugames.eighthdaycollage.resource.ImageResource;
import com.dasugames.eighthdaycollage.resource.MusicResource;
import com.dasugames.eighthdaycollage.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class CreateNewScenarioActivity2 extends CreateNewScenarioActivity {
	
	
	@Override
	protected void back() {
		startActivitySimple(EditScenarioActivity.class);
	}
	

}

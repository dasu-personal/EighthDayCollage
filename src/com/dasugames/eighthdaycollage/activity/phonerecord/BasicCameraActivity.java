package com.dasugames.eighthdaycollage.activity.phonerecord;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.dasugames.eighthdaycollage.activity.EighthDayCollageActivity;
import com.dasugames.eighthdaycollage.customview.CameraPreview;
import com.dasugames.eighthdaycollage.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;

public class BasicCameraActivity extends EighthDayCollageActivity {

    private Camera mCamera;
    private CameraPreview mPreview;
    private Button takePhoto;
    private static String TAG = "basicCameraActivity";
    
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_camera_layout);

        // TODO this might be bad
        setupCamera();
        
        // Set the button for taking the picture
        takePhoto = (Button) findViewById(R.id.button_capture);
        takePhoto.setOnClickListener(
        	    new OnClickListener() {
        	        @Override
        	        public void onClick(View v) {
        	            // TODO I may want to think about making all of these interactions thread safe
        	        	// dont want double clicks to cause problems here
        	            takePhoto.setEnabled(false);
        	            // get an image from the camera
        	            mCamera.takePicture(null, null, mPicture);
        	        }
        	    }
        	);
        
    }
    
    
    /** Create a file Uri for saving an image or video */
    // TODO get rid of this?
    /*
    private static Uri getOutputMediaFileUri(int type){
          return Uri.fromFile(getOutputMediaFile(type));
    }
    */

    /** Create a File for saving an image or video */
    // TODO get rid of this
    /*
    private static File getOutputMediaFile(int type, Context context){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),"myCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d(TAG, "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
            "IMG_"+ timeStamp + ".jpg");
        } else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
            "VID_"+ timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }
    */
    
    
    // TODO maybe think of a more elegant solution here...
    private static String getOutputMediaFileString(){
    	return UUID.randomUUID().toString();
    }

    private PictureCallback mPicture = new PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

        	Log.d(TAG, "Callback called");
        
            String pictureFileString = getOutputMediaFileString();
  
            try {

                FileOutputStream fo = openFileOutput(pictureFileString, Context.MODE_PRIVATE);
                fo.write(data);
                // remember close file output
                fo.close();
                Intent resultIntent = new Intent();
                resultIntent.putExtra("data", pictureFileString);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            } catch (Exception e) {
            	Log.d(TAG, "failed to record image" + e.getMessage());
                e.printStackTrace();
                pictureFileString = null;
                
            }

        }
    };
    
    @Override
    public void onPause(){
    	// TODO I accidently released this twice I think
    	/*
    	if (mCamera != null){
        	mCamera.release();
        	mCamera = null;
    	}
    	*/
    	super.onPause();
    }
    
    @Override
    public void onResume(){
    	//setupCamera();
    	//mCamera.release();

    	super.onResume();
    	// TODO this might be bad

    }
    


	protected void setupCamera() {
		// Create an instance of Camera
        mCamera = this.getCameraInstance();
        Log.i(TAG, mCamera.toString());

        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);
	}
	
    
    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
        	//Log.d(TAG,
        	// TODO this is where stuff is going down
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        	
        }
        return c; // returns null if camera is unavailable
    }


	@Override
	protected void back() {
		Intent resultIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, resultIntent);
        finish();
		
	}
}

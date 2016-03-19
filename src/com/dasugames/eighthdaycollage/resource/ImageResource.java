package com.dasugames.eighthdaycollage.resource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

/**
 * For now this just contains a link to the bitmap for the application.
 * However, it will eventually contain the information for the resource
 * so that we can load the resource after quitting the game. This will
 * also need some sort of way to manage the stored resources as well.
 * @author darren.sue
 *
 */
public class ImageResource extends ManagableResource  implements Serializable {
	private static final long serialVersionUID = 0L;
	
	// I do not want to serialize the context here. It is likely better to reconstruct that aspect on the other end.
	transient Context context;
	String fileName;
	
	public ImageResource(String fileName, Context context) throws FileNotFoundException {
		
		BitmapFactory.decodeStream(context
                .openFileInput(fileName));
		this.context = context;
		this.fileName = fileName;
	}

	/**
	 * This method is necessary to set the context after serialization
	 * @param context
	 */
	public void setContext(Context context){
		this.context = context;
	}


	public Bitmap getImage() {
		
		try {
			Bitmap bitmap = BitmapFactory.decodeStream(context
	                .openFileInput(fileName));
			Matrix mtx = new Matrix();
		    mtx.postRotate(90);

		    int w = bitmap.getWidth();
		    int h = bitmap.getHeight();
		    
		    return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Context getContext(){
		return context;
	}
	
	
	@Override
	protected void clearData() {
		context.deleteFile(fileName);
        Log.d("manageResource", "Deleting the following file: " + fileName);
		// Invalidating stuff so that I dont fail silently
		context = null;
		fileName = null;
	}



}

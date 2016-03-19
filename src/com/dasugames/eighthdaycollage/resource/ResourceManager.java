package com.dasugames.eighthdaycollage.resource;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

public class ResourceManager implements Serializable {
	private static final long serialVersionUID = 0L;
	private Map<String, ImageResource> imageResources;
	private Map<String, MusicResource> musicResources;
	
	public ResourceManager(){
		imageResources = new HashMap <String, ImageResource>();
		musicResources = new HashMap <String, MusicResource>();
	}

	/**
	 * This adds a music resource if one does not exist but adds a reference if it already does.
	 * For bulk operations on preexisting resources, the add reference is likely of more utility.
	 * @param uri
	 */
	public void addMusicResource(String uri){
		if (uri == null){
			return;
		}
		Log.d("manageResource", "add music resource: " + uri);
		MusicResource musicToAdd = musicResources.get(uri);
		if (musicToAdd == null){
			musicToAdd = new MusicResource(uri);
			musicResources.put(uri, musicToAdd);
		}
		musicToAdd.addReference();
	}
	
	/**
	 * This adds a image resource if one does not exist but adds a reference if it already does.
	 * For bulk operations on preexisting resources, the add reference is likely of more utility.
	 * @param uriString
	 * @param context
	 * @throws FileNotFoundException
	 */
	public void addImageResource( String uriString, Context context) throws FileNotFoundException{
		if (uriString == null){
			return;
		}
		Log.d("manageResource", "add image resource: " +uriString);
		ImageResource imageToAdd = imageResources.get(uriString);
		if (imageToAdd == null) {
			imageToAdd = new ImageResource(uriString, context);
			imageResources.put(uriString, imageToAdd);
		}
		imageToAdd.addReference();
		
	}
	
	
	/**
	 * This is a utility method that only handles pre-existing assets. It is useful for bulk operations
	 * as it will operate on all types of resources.
	 * @param name
	 * @param context
	 */
	public void removeReference(String name, Context context){
		if (name == null){
			return;
		}
		
		ImageResource currentImage = imageResources.get(name);
		if (currentImage != null){
			if (currentImage.getContext() == null){
				currentImage.setContext(context);
			}
			currentImage.removeReference();
			if (currentImage.isEmpty()){
				imageResources.remove(currentImage);
			}
			return;
		}
		
		MusicResource currentMusic = musicResources.get(name);
		if (currentMusic != null) {
			currentMusic.removeReference();
			
			if (currentMusic.isEmpty()){
				musicResources.remove(currentMusic);
			}
		}
	}
	
	/**
     * This is a utility method that only handles pre-existing assets. It is useful for bulk operations
	 * as it will operate on all types of resources.
	 * @param name
	 * @param context
	 */
	public void addReference(String name, Context context) {
		if (name == null) {
			return;
		}
		
		ImageResource currentImage = imageResources.get(name);
		if (currentImage != null){
			if (currentImage.getContext() == null){
				currentImage.setContext(context);
			}
			currentImage.addReference();
			return;
		}
		
		MusicResource currentMusic = musicResources.get(name);
		if (currentMusic != null) {
			currentMusic.addReference();
		}
	}
	
	public Bitmap getImageBitmap(String name, Context context) {
		Log.d("manageResource", "searching for image bitmap: " + name);
		ImageResource foundImageResource = imageResources.get(name);
		if (foundImageResource == null) {
			return null;
		}
		Log.d("manageResource", "found image resource: " + name);
		if (foundImageResource != null && foundImageResource.getContext() == null) {
			foundImageResource.setContext(context);
		}
		return foundImageResource.getImage();
	}
	
	public String getMusicUri(String name, Context context) {
		MusicResource foundMusicResource = musicResources.get(name);
		
		if (foundMusicResource == null) {
			return null;
		}
		return foundMusicResource.getUri();
		
		
	}

}

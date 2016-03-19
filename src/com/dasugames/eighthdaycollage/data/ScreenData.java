package com.dasugames.eighthdaycollage.data;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import com.dasugames.eighthdaycollage.resource.ImageResource;
import com.dasugames.eighthdaycollage.resource.MusicResource;

/**
 * Includes information about a single screen
 * Needs a name, imageData, ambientData, a list
 * of POIs, and a list of links
 * @author darren.sue
 *
 */
public class ScreenData implements Serializable{
	private static final long serialVersionUID = 0L;
	private List<LinkData> links;
	private String backgroundImageStringKey;
	private List<PointOfInterestData> interactions; // not even going to worry about this
	private String backgroundMusicStringKey; // not even going to worry about this
	private String titleName;
	
	

	public ScreenData()  {
		// This is a dummy screen and should only be used potentially on the title screen
		setLinks(new ArrayList<LinkData>());
		setInteractions(new ArrayList<PointOfInterestData>());
	}
	
	public ScreenData(String fileName) throws FileNotFoundException{
		setBackgroundImageStringKey(fileName);
		setLinks(new ArrayList<LinkData>());
		setInteractions(new ArrayList<PointOfInterestData>());
	}
	

	public List<LinkData> getLinks() {
		return links;
	}

	public void setLinks(List<LinkData> links) {
		this.links = links;
	}

	public String getBackgroundImageStringKey() {
		return backgroundImageStringKey;
	}

	public void setBackgroundImageStringKey(String backgroundImage) {
		// background image will be null if it is dummy screen

		this.backgroundImageStringKey = backgroundImage;
	}

	public List<PointOfInterestData> getInteractions() {
		return interactions;
	}

	private void setInteractions(List<PointOfInterestData> interactions) {
		this.interactions = interactions;
	}

	public String getBackgroundMusic() {
		return backgroundMusicStringKey;
	}

	public void setBackgroundMusic(String backgroundMusic) {
		this.backgroundMusicStringKey = backgroundMusic;
		
		
	}

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}
	
}

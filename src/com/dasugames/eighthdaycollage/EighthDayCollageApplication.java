package com.dasugames.eighthdaycollage;


import java.io.FileNotFoundException;
import java.util.List;

import com.dasugames.eighthdaycollage.data.ApplicationData;
import com.dasugames.eighthdaycollage.data.MusicManager;
import com.dasugames.eighthdaycollage.data.PointOfInterestData;
import com.dasugames.eighthdaycollage.data.ScenarioData;
import com.dasugames.eighthdaycollage.data.ScreenData;
import com.dasugames.eighthdaycollage.resource.ResourceManager;
import com.dasugames.eighthdaycollage.utils.Utils;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

public class EighthDayCollageApplication extends Application {
	private ApplicationData currentApplicationData;
	private MusicManager musicAmbience;
	
	public EighthDayCollageApplication(){
		musicAmbience = new MusicManager();
		setCurrentApplicationData(new ApplicationData());
	}

	public ApplicationData getCurrentApplicationData() {
		return currentApplicationData;
	}

	public void setCurrentApplicationData(ApplicationData currentApplicationData) {
		this.currentApplicationData = currentApplicationData;
	}

	
	public MusicManager getMusicAmbience() {
		return musicAmbience;
	}

	public void setMusicAmbience(MusicManager musicAmbience) {
		this.musicAmbience = musicAmbience;
	}
	
	public void setBackgroundAmbience(ScreenData screenData, String backgroundMusicString){
		String oldMusicResource = screenData.getBackgroundMusic();
		getResourceManager().addMusicResource(backgroundMusicString);
		getResourceManager().removeReference(oldMusicResource, getApplicationContext());
		screenData.setBackgroundMusic(backgroundMusicString);
		
	}
	
	public void setBackgroundImage(ScreenData screenData, String backgroundImageString) throws FileNotFoundException{
		String oldImageResource = screenData.getBackgroundImageStringKey();
		getResourceManager().addImageResource(backgroundImageString, getApplicationContext());
		getResourceManager().removeReference(oldImageResource, getApplicationContext());
		screenData.setBackgroundImageStringKey(backgroundImageString);
	}
	
	public void setPoiMusic(PointOfInterestData poiData, String backgroundMusicString) {
		String oldMusicResource = poiData.getSoundLogStringKey();
		getResourceManager().addMusicResource(backgroundMusicString);
		getResourceManager().removeReference(oldMusicResource, getApplicationContext());
		poiData.setSoundLogStringKey(backgroundMusicString);
		
	}
	
	public void addPointOfInterestSound(ScreenData screenData, PointOfInterestData poiData){
		List<PointOfInterestData> poiDataList = screenData.getInteractions();
		if (!poiDataList.contains(poiData)){
			getResourceManager().addMusicResource(poiData.getSoundLogStringKey());
			poiDataList.add(poiData);
		}
	}
	
	public void removePointOfInterestSound(ScreenData screenData, PointOfInterestData poiData){
		List<PointOfInterestData> poiDataList = screenData.getInteractions();
		if (poiDataList.contains(poiData)){
			getResourceManager().removeReference(poiData.getSoundLogStringKey(), getApplicationContext());
			poiDataList.remove(poiData);
		}
	}
	
	public Bitmap getImage(String imageResourceKey) {
		return getResourceManager().getImageBitmap(imageResourceKey, getApplicationContext());
	}
	
	public String getMusic(String musicResourceKey) {
		return getResourceManager().getMusicUri(musicResourceKey, getApplicationContext());
	}
	
	public ResourceManager getResourceManager(){
		return currentApplicationData.getSaveData().getResourceManager();
	}
	
	// TODO remove? It looks like we do not use this one
	public void getRidOfScreen(ScreenData screenData) {
		Utils.getRidOfScreen( screenData,  getApplicationContext(),  getResourceManager());
	}
	
	public void graphTraversalAddReference(ScenarioData scenarioData){
		Utils.graphTraversalAddReference( scenarioData, getApplicationContext(), getResourceManager());
	}
	
	public  void graphTraversalRemoveReference(ScenarioData scenarioData){
		Utils.graphTraversalRemoveReference( scenarioData, getApplicationContext(), getResourceManager());
		
	}
	
}


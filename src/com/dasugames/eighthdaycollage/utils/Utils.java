package com.dasugames.eighthdaycollage.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import com.dasugames.eighthdaycollage.data.LinkData;
import com.dasugames.eighthdaycollage.data.PointOfInterestData;
import com.dasugames.eighthdaycollage.data.ScenarioData;
import com.dasugames.eighthdaycollage.data.ScreenData;
import com.dasugames.eighthdaycollage.resource.ImageResource;
import com.dasugames.eighthdaycollage.resource.ManagableResource;
import com.dasugames.eighthdaycollage.resource.MusicResource;
import com.dasugames.eighthdaycollage.resource.ResourceManager;




import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

/**
 * A library of utility methods that can be called statically. Given the contexts and resource
 * manager that needs to be passed around to use these methods properly, many of these methods
 * are called from EigthDayCollageApplication, which supplies those automatically. It is recommended
 * that you call the methods via the application rather than statically here.
 * @author darren.sue
 *
 */
public class Utils {
	
	public static void graphTraversalRemoveReference(ScenarioData scenarioData, Context context, ResourceManager resourceManager){
		List<String> allResources = getAllResources(scenarioData, context);
		
		for (String currentResource : allResources) {
			resourceManager.removeReference(currentResource, context);
		}
	}
	
	public static List<String> getAllResources(ScenarioData scenarioData, Context context){
		Set<ScreenData> allScreens = graphTraversalGetCurrentlyAccessable(scenarioData);
		List<String> allResources = getResourcesForScreens(allScreens, context);
		return allResources;
	}


	public static List<String> getResourcesForScreens(
			Set<ScreenData> allScreens, Context context) {
		List<String> allResources = new ArrayList<String>();
		for (ScreenData currentScreen : allScreens){
			List<String> screenResource = getResourcesForScreen(currentScreen, context);
			allResources.addAll(screenResource);
		}
		return allResources;
	}


	protected static List<String> getResourcesForScreen (ScreenData currentScreen, Context context) {
		List<String> screenResource = new ArrayList<String>();
		
		String backgroundImage = currentScreen.getBackgroundImageStringKey();
		if (backgroundImage != null){
			screenResource.add(backgroundImage);
		}
		String ambientMusic = currentScreen.getBackgroundMusic();
		if (ambientMusic != null){
			screenResource.add(ambientMusic);
		}
		
		for (PointOfInterestData poiData : currentScreen.getInteractions()) {
			String soundLog = poiData.getSoundLogStringKey();
			if (soundLog != null){
				screenResource.add(soundLog);
			}
		}
		return screenResource;
	}
	
	public static void graphTraversalAddReference(ScenarioData scenarioData, Context context, ResourceManager resourceManager){
		List<String> allResources = getAllResources(scenarioData, context);
		
		for (String currentResource : allResources) {
			resourceManager.addReference(currentResource, context);
		}
	}
	
	public static Set<ScreenData> graphTraversalRemoveLink(ScenarioData scenarioData, LinkData removeLink){
		Set<ScreenData> allAccess = graphTraversalGetCurrentlyAccessable(scenarioData);
		Set<ScreenData> allAccessRemoveLink = graphTraversalGetCurrentlyAccessable(scenarioData, removeLink, null);
		allAccess.removeAll(allAccessRemoveLink);
		return allAccess;
	}
	
	public static Set<ScreenData> graphTraversalRemoveScreen(ScenarioData scenarioData, ScreenData removeScreen) {
		Set<ScreenData> allAccess = graphTraversalGetCurrentlyAccessable(scenarioData);
		Set<ScreenData> allAccessRemoveScreen = graphTraversalGetCurrentlyAccessable(scenarioData, null, removeScreen);
		allAccess.removeAll(allAccessRemoveScreen);
		return allAccess;
	}
	
	/**
	 * This method gets all of the screens that are accessible in the scenario if you remove the given missing link and the given missing screen.
	 * To wit, this will include the information for the 
	 * @param scenarioData
	 * @param missingLink
	 * @param missingScreen
	 * @return
	 */
	public static Set<ScreenData> graphTraversalGetCurrentlyAccessable(ScenarioData scenarioData, LinkData missingLink, ScreenData missingScreen) {
		
		Set<ScreenData> processedScreens = new HashSet<ScreenData>();
		
		if (scenarioData == null){
			return processedScreens;
		}
		ScreenData firstScreen = scenarioData.getStartScreen();
		Stack<ScreenData> awaitingScreenData = new Stack<ScreenData>();
		awaitingScreenData.push(firstScreen);
		while (!awaitingScreenData.empty()) {
			ScreenData currentScreen = awaitingScreenData.pop();
			if (!processedScreens.contains(currentScreen) && currentScreen != missingScreen){
				processedScreens.add(currentScreen);
				for (LinkData currentLink : currentScreen.getLinks()) {
					if (currentLink != missingLink && currentLink.getPairedLink() != missingLink) {
						awaitingScreenData.push(currentLink.getTargetedScreen());
					}
				}
			}
		}
		return processedScreens;
	}
	
	public static Set<ScreenData> graphTraversalGetCurrentlyAccessable(ScenarioData scenarioData){
		return graphTraversalGetCurrentlyAccessable(scenarioData, null, null);
	}


	public static void getRidOfLink(LinkData link) {
		Log.d("remove", "getting rid of link");
		LinkData pairedLink = link.getPairedLink();
		ScreenData fromScreen = pairedLink.getTargetedScreen();
		ScreenData toScreen = link.getTargetedScreen();
		
		fromScreen.getLinks().remove(link);
		toScreen.getLinks().remove(pairedLink);
		
		// not even sure that this is neccessary
		link.setTargetedScreen(null);
		pairedLink.setTargetedScreen(null);
		link.setPairedLink(null);
		pairedLink.setPairedLink(null);		
	}
	
	// TODO remove? It looks like we do not use this one
	public static void getRidOfScreen(ScreenData screenData, Context context, ResourceManager resourceManager) {
		List<String> resourcesFromDeletedScreen = getResourcesForScreen(screenData, context);
		for (String currentResource : resourcesFromDeletedScreen){
			resourceManager.removeReference(currentResource, context);
		}
	}
	
}

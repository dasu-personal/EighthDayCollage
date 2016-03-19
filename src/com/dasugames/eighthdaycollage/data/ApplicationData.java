package com.dasugames.eighthdaycollage.data;

/*
 * this is the data that the application will use. All of this needs to be 
 */
public class ApplicationData {
	private ScreenData currentScreen; // 
	private LinkData nextLink; // this is for when constructing new links or editing existing links. This should not be marked for persistence.
	private PointOfInterestData nextPoi; // this is for when constructing new POIs or edinging existing pois. This should not be marked for persistance
	private ScreenData originatingScreen;
	private SaveData saveData; 
	public ApplicationData(){
		saveData = new SaveData();
	}

	public ScreenData getCurrentScreen() {
		return currentScreen;
	}

	public void setCurrentScreen(ScreenData currentScreen) {
		this.currentScreen = currentScreen;
	}

	public LinkData getNextLink() {
		return nextLink;
	}

	public void setNextLink(LinkData nextLink) {
		this.nextLink = nextLink;
	}

	public ScreenData getOriginatingScreen() {
		return originatingScreen;
	}

	public void setOriginatingScreen(ScreenData origiatingScreen) {
		this.originatingScreen = origiatingScreen;
	}

	public PointOfInterestData getNextPoi() {
		return nextPoi;
	}

	public void setNextPoi(PointOfInterestData nextPoi) {
		this.nextPoi = nextPoi;
	}

	public SaveData getSaveData() {
		return saveData;
	}

	public void setSaveData(SaveData saveData) {
		this.saveData = saveData;
	}
}

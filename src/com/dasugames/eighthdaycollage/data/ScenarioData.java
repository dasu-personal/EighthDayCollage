package com.dasugames.eighthdaycollage.data;

import java.io.Serializable;

public class ScenarioData implements Serializable {
	private static final long serialVersionUID = 0L;
	private ScreenData startScreen;
	private String scenarioName;
	// TODO add timestamp
	// TODO add name

	public ScreenData getStartScreen() {
		return startScreen;
	}

	public void setStartScreen(ScreenData startScreen) {
		this.startScreen = startScreen;
	}

	public String getScenarioName() {
		return scenarioName;
	}

	public void setScenarioName(String scenarioName) {
		this.scenarioName = scenarioName;
	}
}

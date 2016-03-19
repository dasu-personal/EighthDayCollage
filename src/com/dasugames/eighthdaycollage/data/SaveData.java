package com.dasugames.eighthdaycollage.data;

import java.io.Serializable;

import com.dasugames.eighthdaycollage.resource.ResourceManager;

public class SaveData implements Serializable {
	private static final long serialVersionUID = 0L;
	
	private ResourceManager resourceManager;
	
	private ScenarioData currentScenario;
	
	private ScenarioData[] savedScenarios;
	
	public SaveData() {
		savedScenarios = new ScenarioData[5];
		resourceManager = new ResourceManager();
	}
	
	public ScenarioData[] getSavedScenarios() {
		return savedScenarios;
	}
	
	public void setSavedScenarios(ScenarioData[] savedScenarios) {
		this.savedScenarios = savedScenarios;
	}
	
	public ScenarioData getCurrentScenario() {
		return currentScenario;
	}
	
	public void setCurrentScenario(ScenarioData resumeScenario) {
		this.currentScenario = resumeScenario;
	}

	public ResourceManager getResourceManager() {
		return resourceManager;
	}

	public void setResourceManager(ResourceManager resourceManager) {
		this.resourceManager = resourceManager;
	}
}

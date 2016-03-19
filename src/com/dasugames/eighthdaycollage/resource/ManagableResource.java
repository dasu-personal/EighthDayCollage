package com.dasugames.eighthdaycollage.resource;

import java.io.Serializable;
import java.util.List;

import android.util.Log;

import com.dasugames.eighthdaycollage.data.ScreenData;

public abstract class ManagableResource implements Serializable {
	private static final long serialVersionUID = 0L;
	
	public boolean isEmpty(){
		return (references <= 0);
	}
	
	private int references = 0; // we always start with one
	public void removeReference() {
		references --;
		Log.d("manageResource", "removeReference; now have references: " + references);
		if (references <= 0){
			clearData();
		}
		
	}
	
	public void addReference(){
		references ++;
		Log.d("manageResource", "addReference; now have references: " + references);
	}
	
	
	// if there are absolutely no pointers towards this resource, then we should get rid
	// the resource in system memory to be safe
	protected abstract void clearData();

}

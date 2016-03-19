package com.dasugames.eighthdaycollage.data;

import java.io.Serializable;

import com.dasugames.eighthdaycollage.resource.MusicResource;

/**
 * Mostly going to ignore this one for now.
 * This one needs an x and y coordinates in image coordinates.
 * It also needs a music resource to play once when the icon
 * is tapped.
 * @author darren.sue
 *
 */
public class PointOfInterestData  implements Serializable {
	private static final long serialVersionUID = 0L;
	protected String soundLogStringKey;
	protected float x,y; // goes from -1 to 1
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public String getSoundLogStringKey() {
		return soundLogStringKey;
	}
	public void setSoundLogStringKey(String soundLog) {
		this.soundLogStringKey = soundLog;
	}
	
	

}

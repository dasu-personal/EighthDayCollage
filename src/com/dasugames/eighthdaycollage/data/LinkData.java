package com.dasugames.eighthdaycollage.data;

import java.io.Serializable;

import android.util.Log;


/**
 * Links are contained on each screen and they point to
 * another screen. Each link will have a direction, a
 * target screen, and a paired link.
 * @author darren.sue
 *
 */
public class LinkData implements Serializable {
	private static final long serialVersionUID = 0L;
	private ScreenData targetedScreen;
	private LinkData pairedLink;
	private double direction; // in degress from (1,0) counterclockwise
	public double getDirection() {
		return direction;
	}
	public void setDirection(double direction) {
		this.direction = direction;
	}
	public LinkData getPairedLink() {
		return pairedLink;
	}
	public void setPairedLink(LinkData pairedLink) {
		Log.d("remove", "set paired link");
		this.pairedLink = pairedLink;
	}
	public ScreenData getTargetedScreen() {
		return targetedScreen;
	}
	public void setTargetedScreen(ScreenData targetedScreen) {
		this.targetedScreen = targetedScreen;
	}
}

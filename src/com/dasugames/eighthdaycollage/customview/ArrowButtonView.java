package com.dasugames.eighthdaycollage.customview;

import java.util.ArrayList;
import java.util.List;

import com.dasugames.eighthdaycollage.data.LinkData;

import com.dasugames.eighthdaycollage.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
/**
 * These array links are created at each screen activity
 * and will not be persisted beyond that. This is so that
 * the application can dynamically consolidate links into
 * arrow buttons just in case.
 * 
 * Of course, for now, each arrow button will have one and
 * only one link.
 * 
 * TODO allow these arrow buttons to have multiple links.
 * @author darren.sue
 *
 */
public class ArrowButtonView extends ImageButton {
	
	private List<LinkData> linkData;
	private double direction; // in degrees from 0 to 360 measured from 1,0 clockwise in right handed coordinates
	public ArrowButtonView(Context context) {
		super(context);
		setLinkData(new ArrayList<LinkData>());
		
		// TODO put in an actual arrow button resource that points to the right
		this.setImageResource(R.drawable.ic_launcher);
		
	}
	public List<LinkData> getLinkData() {
		return linkData;
	}
	
	public void setLinkData(List<LinkData> linkData) {
		this.linkData = linkData;
	}
	
	public double getDirection() {
		return direction;
	}
	
	public void setDirection(double direction) {
		this.direction = direction;
	}
	

}

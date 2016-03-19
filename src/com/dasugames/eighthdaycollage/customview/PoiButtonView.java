package com.dasugames.eighthdaycollage.customview;

import java.util.ArrayList;
import java.util.List;

import com.dasugames.eighthdaycollage.data.LinkData;
import com.dasugames.eighthdaycollage.data.PointOfInterestData;

import com.dasugames.eighthdaycollage.R;
import android.content.Context;
import android.widget.ImageButton;

public class PoiButtonView extends ImageButton {

	private PointOfInterestData pointOfInterestData;
	public PoiButtonView(Context context) {
		super(context);
		
		// TODO put in an actual arrow button resource that points to the right
		this.setImageResource(R.drawable.ic_launcher);
		
	}
	public PointOfInterestData getPointOfInterestData() {
		return pointOfInterestData;
	}
	public void setPointOfInterestData(PointOfInterestData pointOfInterestData) {
		this.pointOfInterestData = pointOfInterestData;
	}

}

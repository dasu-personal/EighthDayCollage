package com.dasugames.eighthdaycollage.customview;

import com.dasugames.eighthdaycollage.customview.eventlistener.NewPathsListener;
import com.dasugames.eighthdaycollage.customview.eventlistener.NewPoisListener;
import com.dasugames.eighthdaycollage.data.PointOfInterestData;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PlacePoiView extends PoiView {
	
	protected TextView instructionsView;
	float x_component, y_component; // this from -1 to 1
	PoiButtonView guidePoi;
	boolean touching;
	private NewPoisListener newPoisListener;
	
	@Override
	protected void setupBackground() {
		super.setupBackground();
		instructionsView = new TextView(this.getContext());
		instructionsView.setText("Place POI");
		instructionsView.setGravity(Gravity.CENTER);
		this.addView(instructionsView);
	}
	
	
	
	@SuppressLint("NewApi")
	@Override
	protected void setupDisplayForBackground() {
		super.setupDisplayForBackground();
		
		instructionsView.setX(viewCenterX - viewWidth / 2);
		instructionsView.setY(viewCenterY - viewHeight / 2);
		instructionsView.setWidth(viewWidth);
		instructionsView.setHeight(viewHeight);
	}

	public PlacePoiView(Context context) {
		super(context);
		newPathsViewCreationHelper(context);
	}
	
	public PlacePoiView(Context context, AttributeSet attrs) {
        super(context, attrs);
		newPathsViewCreationHelper(context);
    }

    public PlacePoiView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
		newPathsViewCreationHelper(context);
    }


    

	

	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh){
		super.onSizeChanged(w, h, oldw, oldh);
	}
	
	private void newPathsViewCreationHelper(Context context){
		for (PoiButtonView poiButton : this.poiButtons) {
			poiButton.setEnabled(false);
		}
		guidePoi = new PoiButtonView(context);
		PointOfInterestData poiData = new PointOfInterestData();
		guidePoi.setPointOfInterestData(poiData);
		this.addView(guidePoi);
		guidePoi.setVisibility(INVISIBLE);
	}





	
	private void recordPosition(float x, float y){
		int effectiveWidthRadius = (viewWidth - poiButtonWidth) / 2;
		int effectiveHeightRadius = (viewHeight - poiButtonHeight) / 2;
		
		x_component = ((float) x - viewCenterX) / effectiveWidthRadius;
		y_component = ((float) y - viewCenterY) / effectiveHeightRadius;
		
		// setting bounds from -1 to 1
		x_component = Math.min(Math.max(x_component, -1), 1);
		y_component = Math.min(Math.max(y_component, -1), 1);
		
	}

	private boolean isValidPosition(float x, float y) {
		
		// simple check of margines
		if (x <= viewCenterX - viewWidth / 2 || x >= viewCenterX + viewWidth / 2) {
			// out of bounds
			return false;

		}
		if (y <= viewCenterY - viewHeight / 2 || y >=  viewCenterY + viewHeight / 2) {
			// out of bounds
			return false;
		}
		
		// TODO checks to make sure current button won't overlap any of the other buttons
		for (PoiButtonView poiButton : poiButtons){
			PointOfInterestData poiData = poiButton.getPointOfInterestData();
			float buttonXposition = poiData.getX() * (viewWidth - poiButtonWidth) / 2 + viewCenterX;
			float buttonYposition = - poiData.getY() * (viewHeight - poiButtonHeight) / 2 + viewCenterY; // y is upsidedown
			
			if (Math.abs(buttonXposition - x) < poiButtonWidth && Math.abs(buttonYposition - y) < poiButtonHeight){
				return false;
			}
			
		}

		return true;
	}

	@Override
	public boolean performClick() {
		// Calls the super implementation, which generates an AccessibilityEvent
		// and calls the onClick() listener on the view, if any
		super.performClick();

		// Handle the action for the custom click here
		return true;
	}

	@SuppressLint("NewApi")
	@Override
	public boolean onTouchEvent(MotionEvent motionEvent) {
		boolean doesHandle = false;

		if (motionEvent.getAction() == MotionEvent.ACTION_MOVE
				|| motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
			if (isValidPosition(motionEvent.getX(), motionEvent.getY())) {
				recordPosition(motionEvent.getX(), motionEvent.getY());
				// TODO add an arrow at the given position
				touching = true;
				guidePoi.getPointOfInterestData().setX(x_component);
				guidePoi.getPointOfInterestData().setY(y_component);
				setLocationPoiButton(guidePoi) ;
				guidePoi.setVisibility(VISIBLE);
				doesHandle = true;
			} else {
				// TODO potentially remove arrow at the given position
				touching = false;
				//guidePoi.setVisibility(INVISIBLE);
			}
		} else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
			if (touching) {
				// I'm sure this will do something important
				// Encouraged by Android to have this here,
				// at any rate
				this.performClick();

				touching = false;
				
				// create a new link
				generateLink();
				doesHandle = true;
			}
		} else {
			touching = false;
		}

		invalidate();
		return doesHandle;
	}
	

	private void generateLink() {
		// I want the calling activity to take care of this logic
		newPoisListener.onClick(x_component, y_component);
	}

	public NewPoisListener getNewPoisListener() {
		return newPoisListener;
	}

	public void setNewPoisListener(NewPoisListener newPoisListener) {
		this.newPoisListener = newPoisListener;
	}


}

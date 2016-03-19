package com.dasugames.eighthdaycollage.customview;

import com.dasugames.eighthdaycollage.customview.eventlistener.NewPathsListener;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PlacePathsView extends PathsView {

	boolean touching;
	protected TextView instructionsView;
	private NewPathsListener newPathsListener;
	
	@Override
	protected void setupBackground() {
		super.setupBackground();
		instructionsView = new TextView(this.getContext());
		instructionsView.setText("Place Path");
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
	
	
	public PlacePathsView(Context context) {
		super(context);

		// In this view, the arrow buttons will be inactive. This is a bit of a
		// giant button created to
		// add new links to the current screen
		newPathsViewCreationHelper(context);
	}
	
	public PlacePathsView(Context context, AttributeSet attrs) {
        super(context, attrs);
		newPathsViewCreationHelper(context);

    }

    public PlacePathsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
		newPathsViewCreationHelper(context);
    }
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh){
		super.onSizeChanged(w, h, oldw, oldh);
	}
	
	private void newPathsViewCreationHelper(Context context){
		for (ArrowButtonView arrowButton : this.arrowButtons) {
			arrowButton.setEnabled(false);
		}
		guideArrow = new ArrowButtonView(context);
		this.addView(guideArrow);
		guideArrow.setVisibility(INVISIBLE);
	}


	double fingerAngle; // this is in degrees
	ArrowButtonView guideArrow;



	private void recordRadius(float x, float y) {
		// calculating the real x and y remembering that y is actually
		// upsidedown for
		// the right hand rule
		float relativeX = (x - viewCenterX) / (viewWidth / 2);
		float relativeY = (viewCenterY - y) / (viewHeight / 2);
		double lengthRelative = Math.sqrt(relativeX * relativeX + relativeY
				* relativeY);

		// now I need to figure out what the actual angle of this is in degrees
		if (relativeY > 0) {
			fingerAngle = Math.acos(relativeX / lengthRelative) * 180 / Math.PI;
		} else {
			fingerAngle = 360 - Math.acos(relativeX / lengthRelative) * 180
					/ Math.PI;
		}

	}

	private boolean isValidPosition(float x, float y) {
		// for now just making sure that the position is within the bounds of
		// the pane.
		// TODO will probably want to make sure that this is within the tubish
		// circle as
		// well

		// A redundant check right now, I know, but what if I want to do
		// something crazy
		// with the margins
		if (x <= viewCenterX - viewWidth / 2 || x >= viewCenterX + viewWidth / 2) {
			// out of bounds
			return false;

		}
		if (y <= viewCenterY - viewHeight / 2 || y >=  viewCenterY + viewHeight / 2) {
			// out of bounds
			return false;
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

	@Override
	public boolean onTouchEvent(MotionEvent motionEvent) {
		boolean doesHandle = false;
		if (motionEvent.getAction() == MotionEvent.ACTION_DOWN || motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
			if (isValidPosition(motionEvent.getX(), motionEvent.getY())) {
				recordRadius(motionEvent.getX(), motionEvent.getY());
				// TODO add an arrow at the given position
				touching = true;
				guideArrow.setDirection(fingerAngle);
				
				setLocationArrowButton(guideArrow) ;

				//invalidate();
				requestLayout();
				guideArrow.setVisibility(VISIBLE);
				doesHandle = true;
			} else {
				// TODO potentially remove arrow at the given position
				touching = false;
				guideArrow.setVisibility(INVISIBLE);
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

		// TODO do I need this
		invalidate();
		return doesHandle;
	}
	

	private void generateLink() {
		// I want the calling activity to take care of this logic
		newPathsListener.onClick(fingerAngle);
	}

	public NewPathsListener getNewPathsListener() {
		return newPathsListener;
	}

	public void setNewPathsListener(NewPathsListener newPathsListener) {
		this.newPathsListener = newPathsListener;
	}




}

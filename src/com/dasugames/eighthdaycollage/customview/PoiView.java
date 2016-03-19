package com.dasugames.eighthdaycollage.customview;

import java.util.ArrayList;
import java.util.List;

import com.dasugames.eighthdaycollage.EighthDayCollageApplication;
import com.dasugames.eighthdaycollage.customview.eventlistener.EditPathsListener;
import com.dasugames.eighthdaycollage.data.LinkData;
import com.dasugames.eighthdaycollage.data.PointOfInterestData;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Style;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class PoiView extends RelativeLayout {
	
	protected List<PoiButtonView> poiButtons;

	protected int viewWidth, viewHeight;
	protected int viewCenterX, viewCenterY;
	protected int poiButtonWidth, poiButtonHeight;
	protected ShapeDrawable rectangleGuide;
	protected ImageView backgroundView;
	
	
	public PoiView(Context context) {
		super(context);
		setupBackground();
		setupPoiButtons();
		
	}
	public PoiView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupBackground();
        setupPoiButtons();
    }

    public PoiView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setupBackground();
        setupPoiButtons();
    }
	


	
	protected final float PERSPECTIVE_SQUISH = 2.7f;
	@SuppressLint("NewApi")
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh){
		
		// The assumptiion here is that the view here will be below the navigation buttons at the top.
		// 
		int heightOfNavView =(int) ( w/PERSPECTIVE_SQUISH); 
		viewWidth = w;
		viewHeight = h - heightOfNavView;
		viewCenterX = viewWidth/2;
		viewCenterY = h - heightOfNavView - viewHeight/2;
		
		setupDisplayForBackground();
		setupDisplayForButtons();

		

	}
	
	@SuppressLint("NewApi")
	protected void setupBackground() {
		backgroundView = new ImageView(this.getContext());
		rectangleGuide = new ShapeDrawable(new RectShape());
		rectangleGuide.getPaint().setColor(Color.GRAY);
		rectangleGuide.getPaint().setStyle(Style.STROKE);
		rectangleGuide.getPaint().setStrokeWidth(5);
		rectangleGuide.getPaint().setAntiAlias(false);
		backgroundView.setBackgroundDrawable(rectangleGuide);
		this.addView(backgroundView);
		
	}
	
	@SuppressLint("NewApi")
	protected void setupDisplayForBackground() {
		
		backgroundView.setX(viewCenterX-viewWidth/2);
		backgroundView.setY(viewCenterY-viewHeight/2);
		backgroundView.getLayoutParams().width = viewWidth;
		backgroundView.getLayoutParams().height = viewHeight;
	}

	protected void setupDisplayForButtons() {
		for (int i = 0; i < poiButtons.size(); i++){
			
			// TODO replace this with arrows objects rather than separate links and imageButtons
			PoiButtonView button = poiButtons.get(i);	
			setLocationPoiButton(button);
		}
		invalidate();
		requestLayout();
	}

	@SuppressLint("NewApi")
	protected void setLocationPoiButton(PoiButtonView button) {
		button.clearAnimation();
		
		PointOfInterestData buttonData = button.getPointOfInterestData();		
		
		// this is very arbitrary
		poiButtonWidth = viewWidth / 5;
		poiButtonHeight = viewHeight / 5;
		
		button.getLayoutParams().width = poiButtonWidth;
		button.getLayoutParams().height = poiButtonHeight;
	
		// taking out the margin for the size ofo the buttons themselves
		int effectiveWidthOfView = viewWidth - poiButtonWidth;
		int effectiveHeightOfView = viewHeight - poiButtonHeight;
		
		float buttonCenterX = buttonData.getX() * effectiveWidthOfView / 2 + viewCenterX;
		float buttonCenterY = buttonData.getY() * effectiveHeightOfView / 2 + viewCenterY;
		
		// This is the position
		button.setX(buttonCenterX - button.getLayoutParams().width / 2);
		button.setY(buttonCenterY - button.getLayoutParams().height / 2);
		
		// thank god I don't have to do any of that funky aniimation in this case
	}
	
	public void refreshArrows(){
		this.removeAllViews();
		setupBackground();
		setupPoiButtons();
		setupDisplayForBackground();
		setupDisplayForButtons();
	}
	

	
	/**
	 * Thank god that this is simpler than the links helper
	 * 
	 * @param links
	 */
	private void simplePoiSetupHelper(List<PointOfInterestData> poiDataList){
		poiButtons = new ArrayList <PoiButtonView>(); 
		
		for (PointOfInterestData poiData : poiDataList){
			PoiButtonView button = new PoiButtonView(getContext());
			
			button.setPointOfInterestData(poiData);;

			poiButtons.add(button);
			this.addView(button);
		}
	}
	
	/**
	 * This method is merely designed to refresh the links that are used for 
	 * TODO something about the api, but lets see whether this actually works anyways
	 */
	@SuppressLint("NewApi")
	protected void setupPoiButtons(){
		List <PointOfInterestData> pois = ((EighthDayCollageApplication) getContext().getApplicationContext()).getCurrentApplicationData().getCurrentScreen().getInteractions();		
		// TODO I just am debugging what is going on here
		//List <PointOfInterestData> pois = generateSamplePoiList();
		simplePoiSetupHelper(pois);

	}
	
	// TODO potentially remove this one
	private List<PointOfInterestData> generateSamplePoiList(){
		List <PointOfInterestData> pois = new ArrayList<PointOfInterestData>();
		
		PointOfInterestData poi = new PointOfInterestData();
		poi.setX(0.5f);
		poi.setY(0.5f);
		pois.add(poi);	
		
		PointOfInterestData poi2 = new PointOfInterestData();
		poi2.setX(0.0f);
		poi2.setY(0.0f);
		pois.add(poi2);	
		
		PointOfInterestData poi3 = new PointOfInterestData();
		poi3.setX(0.0f);
		poi3.setY(-1.0f);
		pois.add(poi3);	
		
		return pois;
	}
	
    public List<PoiButtonView> getPoiButtons(){
    	return poiButtons;
    }
	







}


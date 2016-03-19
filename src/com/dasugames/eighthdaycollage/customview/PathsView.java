package com.dasugames.eighthdaycollage.customview;

import java.util.ArrayList;
import java.util.List;

import com.dasugames.eighthdaycollage.EighthDayCollageApplication;
import com.dasugames.eighthdaycollage.customview.eventlistener.EditPathsListener;
import com.dasugames.eighthdaycollage.data.ApplicationData;
import com.dasugames.eighthdaycollage.data.LinkData;
import com.dasugames.eighthdaycollage.data.ScreenData;

import com.dasugames.eighthdaycollage.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Style;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PathsView extends RelativeLayout {
	

	protected TextView textView; // TODO this is a temporary sanity check. Get rid of it when possible.
	protected List<ArrowButtonView> arrowButtons;
	private EditPathsListener editPathsListener;
	protected int viewWidth;
	protected int viewHeight;
	protected int viewCenterX;
	protected int viewCenterY;
	protected ShapeDrawable circleGuide;
	protected ImageView backgroundView;

	public PathsView(Context context) {
		super(context);
		setupBackground();
		setupArrowButtons();
		
	}
	
	public PathsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupBackground();
        setupArrowButtons();
    }

    public PathsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setupBackground();
        setupArrowButtons();
    }
	


	@SuppressLint("NewApi")
	protected void setupBackground() {
		backgroundView = new ImageView(this.getContext());
		circleGuide = new ShapeDrawable(new OvalShape());
		circleGuide.getPaint().setColor(Color.GRAY);
		circleGuide.getPaint().setStyle(Style.STROKE);
		circleGuide.getPaint().setAntiAlias(false);
		backgroundView.setBackgroundDrawable(circleGuide);
		this.addView(backgroundView);
		
	}

	@SuppressLint("NewApi")
	protected void setupDisplayForBackground() {
		
		backgroundView.setX(viewCenterX-viewWidth/2);
		backgroundView.setY(viewCenterY-viewHeight/2);
		backgroundView.getLayoutParams().width = viewWidth;
		backgroundView.getLayoutParams().height = viewHeight;
	}


	protected final float PERSPECTIVE_SQUISH = 2.7f;
	@SuppressLint("NewApi")
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh){
		
		// one assumption here is that the phone is vertical. It is a good assumption that the
		// width in portrait mode will be less than three times the height
		
		viewWidth = w;
		viewHeight = (int) ( w/PERSPECTIVE_SQUISH);
		viewCenterX = viewWidth/2;
		viewCenterY = h - viewHeight/2;
		
		setupDisplayForBackground();
		setupDisplayForButtons();

		

	}




	protected void setupDisplayForButtons() {
		// adding the background to the view
		
		for (int i = 0; i < arrowButtons.size(); i++){
			
			ArrowButtonView button = arrowButtons.get(i);	
			setLocationArrowButton(button);
		}
		
		
		//invalidate();
		requestLayout();
	}
	
	protected void setupBackgroundOfView(){
		
	}

	@SuppressLint("NewApi")
	protected void setLocationArrowButton(ArrowButtonView button) {
		button.clearAnimation();
		
		button.getLayoutParams().width = viewWidth / 3;
		button.getLayoutParams().height = viewHeight / 3;
		
		double x_component = Math.cos(button.getDirection()/180*Math.PI);
		double y_component = Math.sin(button.getDirection()/180*Math.PI);
		
		int buttonCenterX = (int) (viewCenterX + x_component * (viewWidth / 3) );
		int buttonCenterY = (int) (viewCenterY - y_component * (viewHeight / 3) );
		

		
		
		
		button.setX(buttonCenterX - viewWidth / 6);
		button.setY(buttonCenterY - viewHeight / 6);
		
		//button.setPivotX(w/6);
		//button.setPivotY(h/6);
		
		
		ScaleAnimation a = new ScaleAnimation(1,1, 1, PERSPECTIVE_SQUISH, buttonCenterX, buttonCenterY);
		a.setFillAfter(true);
		a.setDuration(0);
		RotateAnimation b = new RotateAnimation(0, (float) -button.getDirection(),  buttonCenterX, buttonCenterY);
		b.setFillAfter(true);
		b.setDuration(0);
		//b.setStartOffset(10000);
		ScaleAnimation c = new ScaleAnimation(1,1, 1, (float) 1 /(float) PERSPECTIVE_SQUISH, buttonCenterX, buttonCenterY);
		//c.setFillAfter(true);
		//c.setDuration(0);
		//c.setStartOffset(20000);
		
		AnimationSet d = new AnimationSet(true);
		d.setFillAfter(true);
		d.addAnimation(a);
		d.addAnimation(b);
		d.addAnimation(c);
		//button.startAnimation(a);
		//button.startAnimation(b);
		//button.startAnimation(c);
		button.startAnimation(d);
	}
	
	public void refreshArrows(){
		this.removeAllViews();
        setupBackground();
		setupArrowButtons();
		setupDisplayForBackground();
		setupDisplayForButtons();
	}
	
	/**
	 * A temporary method that generates dummy links until I build out
	 * link generation in the application
	 * @return
	 */
	private List<LinkData> generateSampleLinks(){
		List<LinkData> links = new ArrayList<LinkData> ();
		
		LinkData link1 = new LinkData();
		link1.setDirection(0);
		links.add(link1);
		
		LinkData link2 = new LinkData();
		link2.setDirection(60);
		links.add(link2);
		
		LinkData link3 = new LinkData();
		link3.setDirection(90);
		links.add(link3);
		
		LinkData link4 = new LinkData();
		link4.setDirection(180);
		links.add(link4);
		
		return links;
	}
	
	/**
	 * This generates a single arrow per link. Obviously, this is a
	 * bit of a simplification. I will create one that properly
	 * consolidates multiple links into arrows later.
	 * 
	 * TODO create another arrowsetuphelper that can consolidate links.
	 * @param links
	 */
	private void simpleArrowSetupHelper(List<LinkData> links){
		arrowButtons = new ArrayList <ArrowButtonView>(); 
		
		for (LinkData link : links){
			List<LinkData> linkList = new ArrayList<LinkData>();
			linkList.add(link);
			ArrowButtonView button = new ArrowButtonView(getContext());
			
			button.setLinkData(linkList);
			button.setDirection(link.getDirection());

			arrowButtons.add(button);
			
			
			this.addView(button);
		}
	}
	
	/**
	 * This method is merely designed to refresh the links that are used for 
	 * TODO something about the api, but lets see whether this actually works anyways
	 */
	@SuppressLint("NewApi")
	protected void setupArrowButtons(){
		List <LinkData>links = ((EighthDayCollageApplication) getContext().getApplicationContext()).getCurrentApplicationData().getCurrentScreen().getLinks();
		simpleArrowSetupHelper(links);
	}

    public List<ArrowButtonView> getArrowButtons(){
    	return arrowButtons;
    }

	public EditPathsListener getEditPathsListener() {
		return editPathsListener;
	}

	public void setEditPathsListener(EditPathsListener editPathsListener) {
		this.editPathsListener = editPathsListener;
	}



}

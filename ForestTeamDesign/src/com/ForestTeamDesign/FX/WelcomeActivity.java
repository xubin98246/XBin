package com.ForestTeamDesign.FX;

import com.ForestTeamDesign.FX.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 *
 */
public class WelcomeActivity extends Activity implements AnimationListener {
	private ImageView  imageView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		imageView = (ImageView)findViewById(R.id.welcome_image_view);
//		alphaAnimation = AnimationUtils.loadAnimation(this, R.anim.welcome_alpha);
//		alphaAnimation.setFillEnabled(true); 
//		alphaAnimation.setFillAfter(true); 
//		alphaAnimation.setDuration(1000);
//		imageView.setAnimation(alphaAnimation);
//		alphaAnimation.setAnimationListener(this); 
		AnimationSet  animationset=new AnimationSet(true);  
	    AlphaAnimation alphaAnimation=new AlphaAnimation(1, 0);  
	    alphaAnimation.setDuration(5500);  
	    animationset.addAnimation(alphaAnimation);  
	    imageView.startAnimation(animationset);  
	    new Handler().postDelayed(new Runnable(){   
	            
	        public void run() {   
	        	Intent intent = new Intent(WelcomeActivity.this, Select.class);
	    		startActivity(intent);
	    		WelcomeActivity.this.finish();
	        }   
	             
	       }, 1000);   
 	}
	
	//@Override
	public void onAnimationStart(Animation animation) {
		
	}
	
	//@Override
	public void onAnimationEnd(Animation animation) {
	}
	
	//@Override
	public void onAnimationRepeat(Animation animation) {
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK) {
			return false;
		}
		return false;
	}
	
}

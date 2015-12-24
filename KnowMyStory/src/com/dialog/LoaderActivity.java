package com.dialog;

import net.onehope.mystory.R;

import android.app.Activity;
import android.app.ActivityGroup;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class LoaderActivity extends Activity {
	public static Activity loader;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		loader = this;
		
		setContentView(R.layout.loader);
		ImageView iv = (ImageView)findViewById(R.id.loading_image);
		RotateAnimation anim = new RotateAnimation(0.0f, 360.0f , Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
		anim.setInterpolator(new LinearInterpolator());
		anim.setRepeatCount(Animation.INFINITE);
		anim.setDuration(3000);
		iv.setAnimation(anim);
		iv.startAnimation(anim);
	}
@Override
public void onBackPressed() {
	
	//super.onBackPressed();
}
}

/*
 * Know My Story 
 */
package com.dialog;

import net.onehope.mystory.R;
import com.supportclasses.Constants;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Dialog activity class to display the loading screen
 */
public class TransparentProgressDialog extends Dialog
{
	// Declare the variables and the objects used
	private ImageView iv;
	public Typeface abel_ttf;

	/**
	 * Parameterized constructor for the class
	 */
	public TransparentProgressDialog(Context context, int resourceIdOfImage ,String text)
	{
		super(context, R.style.TransparentProgressDialog);
		WindowManager.LayoutParams wlmp = getWindow().getAttributes();
		wlmp.gravity = Gravity.CENTER;

		getWindow().setAttributes(wlmp);
		setTitle(null);
		setCancelable(false);
		setOnCancelListener(null);
		abel_ttf=Typeface.createFromAsset(context.getAssets(), Constants.FONT_ABEL_REGULAR);
		LinearLayout ll = new LinearLayout(context);
		ll.setOrientation(LinearLayout.VERTICAL);
		ll.setGravity(Gravity.CENTER);

		// Get the image view
		iv = new ImageView(context);
		iv.setImageResource(resourceIdOfImage);
		//iv.setPadding(5,15,15,5);
		LayoutParams ilp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		iv.setLayoutParams(ilp);

		// Create the caption view
		TextView cap = new TextView(context);
		cap.setTypeface(abel_ttf);
		cap.setText(text);
		cap.setPadding(5,10,5,5);
		cap.setTextSize(20);
		LayoutParams clp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		cap.setLayoutParams(clp);

		// Add views to the linear layout
		ll.addView(iv);
		ll.addView(cap);
		addContentView(ll, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	}

	@Override
	public void show() 
	{
		super.show();
		RotateAnimation anim = new RotateAnimation(0.0f, 360.0f , Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
		anim.setInterpolator(new LinearInterpolator());
		anim.setRepeatCount(Animation.INFINITE);
		anim.setDuration(3000);
		iv.setAnimation(anim);
		iv.startAnimation(anim);
	}
}

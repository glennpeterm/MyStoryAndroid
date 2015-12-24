package com.supportclasses;


import net.onehope.mystory.R;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/*
 *TextView_Normal class to implement custom normal 
 *fonts to be used in Textview layouts 
 */
public class TextView_Normal extends TextView {
	Context context;
	String ttfName;

	String TAG = getClass().getName();

	public TextView_Normal(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;

		init();
	}

	private void init() {
		Typeface font = Typeface.createFromAsset(context.getAssets(), Constants.FONT_ABEL_REGULAR);
		setTypeface(font,Typeface.NORMAL);
	}

	@Override
	public void setTypeface(Typeface tf) {
		super.setTypeface(tf);
	}
}

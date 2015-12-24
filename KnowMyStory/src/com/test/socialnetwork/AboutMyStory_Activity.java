package com.test.socialnetwork;

import net.onehope.mystory.R;
import com.supportclasses.menu.Constants;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AboutMyStory_Activity extends Activity implements OnClickListener
{
	private TextView tvMaterial;
	private ImageButton ibLogo;
	private LinearLayout llAbout;
	private TextView tvHeading,tvText;
	
	public Typeface abel_ttf;

	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		//getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		//getActionBar().hide();
		setContentView(R.layout.about_my_story);
		initializeComponents();
		setClicklisteners();
		setFontStyle();
	}

	private void initializeComponents()
	{
		ibLogo=(ImageButton)findViewById(R.id.ibLogo);
		llAbout=(LinearLayout)findViewById(R.id.llAbout);
		tvHeading=(TextView)findViewById(R.id.tvAbout);
		tvText=(TextView)findViewById(R.id.tvVerseIndex);
	}

	private void setClicklisteners()
	{
		ibLogo.setOnClickListener(this);
		llAbout.setOnClickListener(this);
	}
	
	private void setFontStyle()
	{
		abel_ttf=Typeface.createFromAsset(AboutMyStory_Activity.this.getAssets(), com.supportclasses.Constants.FONT_ABEL_REGULAR);
		tvHeading.setTypeface(abel_ttf);
		tvText.setTypeface(abel_ttf);
	}

	@Override
	public void onClick(View v) 
	{
		switch (v.getId())
		{
		case R.id.ibLogo:
			loadURL(Constants.URL_ONEHOPE);
			break;

		case R.id.llAbout:
			onBackPressed();
			break;

		default:
			break;
		}
	}

	public void loadURL(String url)
	{
		Uri uriUrl = Uri.parse(url);
		Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
		startActivity(launchBrowser);
	}

	@Override
	public void onBackPressed() 
	{
		super.onBackPressed();
	}

}

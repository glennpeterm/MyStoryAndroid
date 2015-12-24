package com.test.socialnetwork;

/*new coach master activity added, Hareesh Menon.
 * viewpager with sampled image, insample size=2;
 * */


import net.onehope.mystory.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.viewpager.indicator.CirclePageIndicator;

public class PhotoLaunchActivity extends Activity implements OnClickListener{

	
	private ImageView mBtnskip;
	private ViewPager  pager;
	private CirclePageIndicator mIndicator;
	static int[] mResources = {
        R.drawable.img1,
        R.drawable.img2,
        R.drawable.img3,
        R.drawable.img4,
        R.drawable.img5,
        R.drawable.img6,
        R.drawable.img7,
        R.drawable.img8,
        R.drawable.img9,
        R.drawable.img10,
        R.drawable.img11,
        R.drawable.img12
};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.launch_pic_activity);
		
		pager = (ViewPager)findViewById(R.id.pager);
		mBtnskip=(ImageView)findViewById(R.id.btnSkip);
		mBtnskip.setOnClickListener(this);
		
		CustomPagerAdapter  adapter = new CustomPagerAdapter(PhotoLaunchActivity.this);
		pager.setAdapter(adapter);
		
		 mIndicator = (CirclePageIndicator)findViewById(R.id.indicator);
	     mIndicator.setViewPager(pager);
		
	}
	
	
	
	
	
	
	class CustomPagerAdapter extends PagerAdapter {
		 
	    Context mContext;
	    LayoutInflater mLayoutInflater;
	    ImageLoader  loader;
	 
	    public CustomPagerAdapter(Context context) {
	        mContext = context;
	        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        loader = new ImageLoader(Volley.newRequestQueue(PhotoLaunchActivity.this), new LruBitmapCache());
	    }
	 
	    @Override
	    public int getCount() {
	        return mResources.length;
	    }
	 
	    @Override
	    public boolean isViewFromObject(View view, Object object) {
	        return view == ((LinearLayout) object);
	    }
	 
	    @Override
	    public Object instantiateItem(ViewGroup container, int position) {
	        View itemView = mLayoutInflater.inflate(R.layout.pageritem, container, false);
	 
	        NetworkImageView imgAvatar = (NetworkImageView)itemView.findViewById(R.id.imgAvatar);
	      
	        imgAvatar.setDefaultImageResId(mResources[position]);
	        imgAvatar.setImageUrl(null,loader);// show your drawable
	   
			
	        container.addView(itemView);
	 
	        return itemView;
	    }
	 
	    @Override
	    public void destroyItem(ViewGroup container, int position, Object object) {
	        container.removeView((LinearLayout) object);
	    }
	    
	    
	}




	@Override
	public void onClick(View v) {

		switch (v.getId()) 
		{
		case R.id.btnSkip:
		
			Intent i = new Intent(PhotoLaunchActivity.this, HomeScreen.class);
			startActivity(i);
			PhotoLaunchActivity.this.finish();
			break;

		default:
			break;
		}

	
	}
	
	
	
	
	
}

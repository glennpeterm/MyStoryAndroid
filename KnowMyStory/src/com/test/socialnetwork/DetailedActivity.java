/*
 * Know My Story 
 */
package com.test.socialnetwork;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import net.onehope.mystory.R;
import com.model.UserDetail;

public class DetailedActivity extends Activity {
	TextView txtvD;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detailed);
		txtvD = (TextView) findViewById(R.id.txtvData);
		UserDetail us=getIntent().getParcelableExtra("data");
		txtvD.setText(us.getFirstName()+"\n"+us.getLastName()+"\n"+us.getProfilepic()+"\n"+us.getEmail());

	}

}

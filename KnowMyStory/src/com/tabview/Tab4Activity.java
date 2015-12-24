/*
 * Know My Story 
 */
package com.tabview;

/*
 * Testing Activity for adding Bg music to Video .Need to use this activity..preeti
 */
import net.onehope.mystory.R;
import dogtim.android.videoeditor.util.FileUtils;
import dogtim.android.videoeditor.util.VideoEditorActvityLolipop;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Tab4Activity extends Activity {
	 public static final String PARAM_CREATE_PROJECT_NAME = "name";
	  public static final String PARAM_OPEN_PROJECT_PATH = "path";
	  private static final int REQUEST_CODE_CREATE_PROJECT = 0;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       // TextView textview = new TextView(this);
        //textview.setText("This is BlackBerry tab");
        setContentView(R.layout.title);
        
        Button btn = (Button)findViewById(R.id.tab1);
        btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				 try {
			            final Intent intent = new Intent(Tab4Activity.this, VideoEditorActvityLolipop.class);
			            intent.setAction(Intent.ACTION_INSERT);
			            intent.putExtra(PARAM_CREATE_PROJECT_NAME, "storyname");
			            final String projectPath = FileUtils.createNewProjectPath(Tab4Activity.this);
			            intent.putExtra(PARAM_OPEN_PROJECT_PATH, projectPath);
			            startActivityForResult(intent, REQUEST_CODE_CREATE_PROJECT);
			        } catch (Exception ex) {
			            ex.printStackTrace();
			            System.out.println("editor_storage_not_available");
			           // Toast.makeText(Tab4Activity.this, R.string.editor_storage_not_available,
			                 //   Toast.LENGTH_LONG).show();
			     //   }
			}
			}
		});
    }
}
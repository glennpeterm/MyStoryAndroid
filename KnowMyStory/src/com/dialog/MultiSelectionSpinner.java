/*
 * Know My Story 
 */
package com.dialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import com.database.DatabaseHelper;
import net.onehope.mystory.R;
import com.google.ytdl.Constants;
import com.tabview.Tab1Activity;
import com.tabview.TabViewActivity;
import com.test.socialnetwork.AboutMyStory_Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.DialogInterface.OnShowListener;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MultiSelectionSpinner extends Spinner implements
		OnMultiChoiceClickListener {
	String[] _items = null;
	boolean[] mSelection = null;
	DatabaseHelper db;

	ArrayAdapter<String> simple_adapter;
	Tab1Activity tabView= new Tab1Activity() ;

	//TextView tvText;
	Typeface font = Typeface.createFromAsset(getContext().getAssets(), com.supportclasses.Constants.FONT_ABEL_REGULAR);

	public MultiSelectionSpinner(Context context) {
		super(context);

		//simple_adapter = new ArrayAdapter<String>(context,
		//		R.layout.spinner_item);
		simple_adapter = new ArrayAdapter<String>(context,
				R.layout.topic_selector);
		//TopicAdapter adapter = new TopicAdapter(this);
		super.setAdapter(simple_adapter);
	}

	public MultiSelectionSpinner(Context context, AttributeSet attrs) {
		super(context, attrs);

		db = new DatabaseHelper(context);
		simple_adapter = new ArrayAdapter<String>(context,
				R.layout.topic_selector);
		super.setAdapter(simple_adapter);
	}

	public void onClick(DialogInterface dialog, int which, boolean isChecked) {
		if (mSelection != null && which < mSelection.length) {
			mSelection[which] = isChecked;

			simple_adapter.clear();
			System.out.println("buil44..");
			simple_adapter.add(buildSelectedItemString());
			System.out.println("position: "+which);
		} else {
			throw new IllegalArgumentException(
					"Argument 'which' is out of bounds.");
		}
	}

	@Override
	public boolean performClick() {
		//LayoutInflater inflater = LayoutInflater.from(getContext());
		//View dialoglayout = inflater.inflate(R.layout.errordialog, null);
		//ContextThemeWrapper cw = new ContextThemeWrapper( getContext(),R.style.AlertDialogTheme);
		AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.AlertDialogTheme);
		
		//builder.setView(dialoglayout);
		//builder.setTitle("Select a topic");
		/*builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				
			}
		});*/
		
		//Working code
		//db = new DatabaseHelper(context);
		
		/*
		 * Preselect the selected topics
		 */
		 String topicPos = db.getTopicPosition();
		 System.out.println("topicPos "+topicPos);
		 
		 String[] SplittopicPosition = null;
		 if(topicPos!=null){
			if(!(topicPos.equals(""))){
				if(topicPos.contains(",")){
					  SplittopicPosition = topicPos.split(",");
					 for(String position :SplittopicPosition){
						 int i = Integer.parseInt(position);
						 try {
							 if(mSelection!=null)
							mSelection[i]=true;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					 }
				}else{
					int i = Integer.parseInt(topicPos);
					 try {
						 if(mSelection!=null)
						mSelection[i]=true;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
		
		System.out.println("item index: "+mSelection);
		builder.setMultiChoiceItems(_items, mSelection, this);
		System.out.println("aaaa: "+mSelection);
		
		final AlertDialog alertDialog = builder.create();
		alertDialog.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss(DialogInterface dialog) {
				System.out.println("Dismiss...");
				String topics = db.getTopics();
				System.out.println("Topics in spinner: "+topics);
				if(topics!=null){
					if(topics.equals("")){
						//tabView.setTopicHolder();
						Tab1Activity.setTopicHolder();
					}
				}else {
					Tab1Activity.setTopicHolder();
				}
				
				//if(topics != null)
				//tabView.setInitialvalues();
				 
			}
		});
		alertDialog.setOnShowListener(new OnShowListener() {
			
			@Override
			public void onShow(DialogInterface dialog) {
				try {
					final ListView listView = ((AlertDialog)alertDialog).getListView();
					final ListAdapter originalAdapter = listView.getAdapter();
					listView.setAdapter(new ListAdapter() {
						
						@Override
						public void unregisterDataSetObserver(DataSetObserver observer) {
							originalAdapter.unregisterDataSetObserver(observer);
							
						}
						
						@Override
						public void registerDataSetObserver(DataSetObserver observer) {
							originalAdapter.registerDataSetObserver(observer);
							
						}
						
						@Override
						public boolean isEmpty() {
							
							return originalAdapter.isEmpty();
						}
						
						@Override
						public boolean hasStableIds() {
							
							return originalAdapter.hasStableIds();
						}
						
						@Override
						public int getViewTypeCount() {
							
							return originalAdapter.getViewTypeCount();
						}
						
						@Override
						public View getView(int position, View convertView, ViewGroup parent) {
							View view = originalAdapter.getView(position, convertView, parent);
							TextView textView = (TextView)view;
							textView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, 55 /* this is item height */));
							//textView.setTypeface(MyFontUtil.getTypeface(MyActivity,MY_DEFAULT_FONT));
							listView.setDividerHeight(0);

							textView.setBackgroundColor(Color.WHITE);
							textView.setTextColor(getResources().getColor(R.color.dark_grey));
							textView.setTextSize(15); // FIXIT - absolute size 
							textView.setTypeface(font);
							CheckedTextView checkedView = (CheckedTextView) view;
							checkedView.setCheckMarkDrawable(R.drawable.default_checkbox);
							return view;
						}
						
						@Override
						public int getItemViewType(int position) {
							
							 return originalAdapter.getItemViewType(position);
						}
						
						@Override
						public long getItemId(int position) {
							
							return originalAdapter.getItemId(position);
						}
						
						@Override
						public Object getItem(int position) {
							
							System.out.println("bbb: "+originalAdapter.getItem(position));
							return originalAdapter.getItem(position);
						}
						
						@Override
						public int getCount() {
							
							return originalAdapter.getCount();
						}
						
						@Override
						public boolean isEnabled(int position) {
							
							return originalAdapter.isEnabled(position);
						}
						
						@Override
						public boolean areAllItemsEnabled() {
							
							return originalAdapter.areAllItemsEnabled();
						}
					});
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		//Removes the background dimming of the activity
		//alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		//alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.ic_launcher_moviestudio);
		
		alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		alertDialog.setCanceledOnTouchOutside(true);
		if(mSelection!=null){
			if(!(mSelection.equals(""))){
		alertDialog.show();
			}
		}
		
		/*
		 * 
		 */
		DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
		int width = displayMetrics.widthPixels;
		int height = displayMetrics.heightPixels;
		float w = (float) (width*0.80);
		float h = (float) (height*0.75);
		int wid = (int)w;
		int hei = (int)h;
		
		alertDialog.getWindow().setLayout(wid, hei);
		
		return true;
	}

	@Override
	public void setAdapter(SpinnerAdapter adapter) {
		throw new RuntimeException(
				"setAdapter is not supported by MultiSelectSpinner.");
	}

	public void setItems(String[] items) {
		_items = items;
		mSelection = new boolean[_items.length];
		simple_adapter.clear();
		String PreSelectedTopics = db.getTopics();
	//	if(!(PreSelectedTopics.equals(""))||(PreSelectedTopics!=null)){
			if(PreSelectedTopics!=null){
				if(!(PreSelectedTopics.equals(""))){
			//simple_adapter.add(_items[0]);
			//simple_adapter.add(getResources().getString(R.string.select_topic));
			simple_adapter.add(PreSelectedTopics);
			System.out.println("buil555..");
			//simple_adapter.add(buildSelectedItemString());
				}
			
		}
			
		System.out.println("item2 : "+_items.length);
		
		Arrays.fill(mSelection, false);
		performClick();
	}

	public void setItems(List<String> items) {
		_items = items.toArray(new String[items.size()]);
		mSelection = new boolean[_items.length];
		simple_adapter.clear();
		simple_adapter.add(_items[0]);
		System.out.println("item1 : "+_items[0]);
		Arrays.fill(mSelection, false);
	}

	public void setSelection(String[] selection) {
		for (String cell : selection) {
			for (int j = 0; j < _items.length; ++j) {
				if (_items[j].equals(cell)) {
					mSelection[j] = true;
				}
			}
		}
	}

	public void setSelection(List<String> selection) {
		for (int i = 0; i < mSelection.length; i++) {
			mSelection[i] = false;
		}
		for (String sel : selection) {
			for (int j = 0; j < _items.length; ++j) {
				if (_items[j].equals(sel)) {
					mSelection[j] = true;
				}
			}
		}
		simple_adapter.clear();
		System.out.println("buil111..");
		simple_adapter.add(buildSelectedItemString());
	}

	public void setSelection(int index) {
		for (int i = 0; i < mSelection.length; i++) {
			mSelection[i] = false;
		}
		if (index >= 0 && index < mSelection.length) {
			mSelection[index] = true;
		} else {
			throw new IllegalArgumentException("Index " + index
					+ " is out of bounds.");
		}
		simple_adapter.clear();
		System.out.println("buil222..");
		simple_adapter.add(buildSelectedItemString());
		System.out.println("setSelection1: "+buildSelectedItemString());
	}

	public void setSelection(int[] selectedIndicies) {
		for (int i = 0; i < mSelection.length; i++) {
			mSelection[i] = false;
		}
		for (int index : selectedIndicies) {
			if (index >= 0 && index < mSelection.length) {
				mSelection[index] = true;
			} else {
				throw new IllegalArgumentException("Index " + index
						+ " is out of bounds.");
			}
		}
		simple_adapter.clear();
		System.out.println("buil333..");
		simple_adapter.add(buildSelectedItemString());
		System.out.println("setSelection2: "+buildSelectedItemString());
	}

	public List<String> getSelectedStrings() {
		List<String> selection = new LinkedList<String>();
		for (int i = 0; i < _items.length; ++i) {
			if (mSelection[i]) {
				selection.add(_items[i]);
			}
		}
		
		return selection;
	}

	public List<Integer> getSelectedIndicies() {
		List<Integer> selection = new LinkedList<Integer>();
		for (int i = 0; i < _items.length; ++i) {
			if (mSelection[i]) {
				selection.add(i);
			}
		}
		return selection;
	}

	public String buildSelectedItemString() {
		StringBuilder sb = new StringBuilder();
		StringBuilder pos = new StringBuilder();
		boolean foundOne = false;

		for (int i = 0; i < _items.length; ++i) {
			if (mSelection[i]) {
				if (foundOne) {
					sb.append(", ");
					pos.append(",");
				}
				foundOne = true;
				
				sb.append(_items[i]);
				pos.append(i);
			}
		}
		System.out.println("vvv: "+sb.toString());
		System.out.println("count : "+db.loginCount());
		db.updateTopics(sb.toString());
		db.updateTopicPosition(pos.toString());
		System.out.println("pos: "+pos.toString());
		System.out.println("getTopics: "+db.getTopics());
		
		return sb.toString();
	}

	public String getSelectedItemsAsString() {
		StringBuilder sb = new StringBuilder();
		boolean foundOne = false;

		for (int i = 0; i < _items.length; ++i) {
			if (mSelection[i]) {
				if (foundOne) {
					sb.append(", ");
				}
				foundOne = true;
				sb.append(_items[i]);
			}
		}
		return sb.toString();
	}
}

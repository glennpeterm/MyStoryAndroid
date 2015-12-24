/*
 * Know My Story 
 */
package com.menuadapter;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.onehope.mystory.R;
import com.model.Menu;

public class MenuAdapter extends CircularLoopAdapter {

	 private List<Menu> menus;
	 
	 
	public MenuAdapter(List<Menu> menus) {
		super();
		setMenu(menus);
		this.menus = menus;
	}

	@Override
	public int getCount() {
		
		return Integer.MAX_VALUE;
	}

	@Override
	public Object getItem(int position) {
		
		 return getMenu().get(getCircularPosition(position));
	/*if(position==0)
	{
		return getMenu().get(getMenu().size()-1);
	}
	else
		return getMenu().get(position % getCount());*/
	}

	@Override
	public long getItemId(int position) {
		
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		 ViewHolder holder;
	        if ( convertView == null ) {

	            convertView = LayoutInflater.from( parent.getContext() ).inflate( R.layout.menu_row, parent, false );
	            holder = new ViewHolder();
	            holder.MainText = (TextView) convertView.findViewById( R.id.txtvMain );
	            holder.SubText = (TextView) convertView.findViewById( R.id.txtvSub );
	            convertView.setTag( holder );
	        }
	        else {

	            holder = (ViewHolder) convertView.getTag();
	        }

	        holder.MainText.setText(getMenu().get(position % getMenu().size()).getMainText());
	        holder.SubText.setText(getMenu().get(position % getMenu().size()
	        		).getSubText());
	        
	      /*  holder.type.setText( getAlerts().get( position ).getType() );
	        holder.date.setText( getAlerts().get( position ).getDate() );
	        holder.time.setText( getAlerts().get( position ).getTime() );

	        holder.deleteButton.setOnClickListener( new View.OnClickListener() {

	            @Override
	            public void onClick( View v ) {

	                getCallback().deleteListItem( finalPosition );
	            }
	        } );
*/
	        return convertView;
	}

	
	 private List<Menu> getMenu() {

	        return menus;
	    }

	    private void setMenu( List<Menu> alerts ) {

	        this.menus = alerts;
	    }

	static class ViewHolder {

		TextView MainText;
		TextView SubText;

	}

	@Override
	protected int getCircularCount() {
		
		return getMenu().size();
	}


}

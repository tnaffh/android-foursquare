package org.android.olp.foursquare;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyListAdapter extends ArrayAdapter<JSONObject> {
	private final Activity context;
	private final List<JSONObject> names;

	public MyListAdapter(Activity context, List<JSONObject> names) {
		super(context, R.layout.rowlayout, names);
		this.context = context;
		this.names = names;
	}

	// static to save the reference to the outer class and to avoid access to
	// any members of the containing class
	static class ViewHolder {
		public ImageView imageView;
		public TextView textView;
		public TextView txtAddress;
		public TextView txtDistance;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// ViewHolder will buffer the assess to the individual fields of the row
		// layout

		ViewHolder holder;
		// Recycle existing view if passed as parameter
		// This will save memory and time on Android
		// This only works if the base layout for all classes are the same
		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.rowlayout, null, true);
			holder = new ViewHolder();
			holder.textView = (TextView) rowView.findViewById(R.id.label);
			holder.imageView = (ImageView) rowView.findViewById(R.id.icon);
			holder.txtDistance = (TextView) rowView.findViewById(R.id.distance);
			holder.txtAddress = (TextView) rowView.findViewById(R.id.address);
			rowView.setTag(holder);
		} else {
			holder = (ViewHolder) rowView.getTag();
		}

		try {
		
		Log.i("JSON", names.get(position).toString());
		
		ImageLoader drawable = new ImageLoader();
		holder.imageView.setImageDrawable(drawable.loadImageUrl(names.get(position).getString("icon")));
			
		holder.textView.setText(names.get(position).getString("name"));
		holder.txtAddress.setText(names.get(position).getString("address"));
		holder.txtDistance.setText("" + names.get(position).getInt("distance") + " feet");
		} catch (JSONException e) {
			Log.e("JSON", e.getMessage());
		}
		// Change the icon for Windows and iPhone
//		String s = names[position];
//		if (s.startsWith("Windows7") || s.startsWith("iPhone")
//				|| s.startsWith("Solaris")) {
//
//			holder.imageView.setImageResource(R.drawable.no);
//		} else {
//			holder.imageView.setImageResource(R.drawable.ok);
//		}

		return rowView;
	}
}

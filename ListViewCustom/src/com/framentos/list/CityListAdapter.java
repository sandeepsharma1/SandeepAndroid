package com.framentos.list;
import java.util.List;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CityListAdapter extends ArrayAdapter<City> {

	private int				resource;
	private LayoutInflater	inflater;
	private Context 		context;
	
	public CityListAdapter ( Context ctx, int resourceId, List<City> objects) {
		
		super( ctx, resourceId, objects );
		resource = resourceId;
		inflater = LayoutInflater.from( ctx );
		context=ctx;
	}


	@Override
	public View getView ( int position, View convertView, ViewGroup parent ) {

		/* create a new view of my layout and inflate it in the row */
		convertView = ( RelativeLayout ) inflater.inflate( resource, null );

		/* Extract the city's object to show */
		City city = getItem( position );
		
		/* Take the TextView from layout and set the city's name */
		TextView txtName = (TextView) convertView.findViewById(R.id.cityName);
		txtName.setText(city.getName());
		
		/* Take the TextView from layout and set the city's wiki link */
		TextView txtWiki = (TextView) convertView.findViewById(R.id.cityLinkWiki);
		txtWiki.setText(city.getUrlWiki());
		
		/* Take the ImageView from layout and set the city's image */
		ImageView imageCity = (ImageView) convertView.findViewById(R.id.ImageCity);
		String uri = "drawable/" + city.getImage();
	    int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
	    Drawable image = context.getResources().getDrawable(imageResource);
	    imageCity.setImageDrawable(image);

		return convertView;

	}
}


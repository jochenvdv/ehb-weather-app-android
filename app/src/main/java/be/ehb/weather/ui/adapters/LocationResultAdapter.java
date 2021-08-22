package be.ehb.weather.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import be.ehb.weather.R;
import be.ehb.weather.models.NamedLocation;
import be.ehb.weather.ui.activities.LocationDetailActivity;
import be.ehb.weather.ui.activities.SearchLocationActivity;

public class LocationResultAdapter extends ArrayAdapter<NamedLocation> {
    private Activity activity;

    public LocationResultAdapter(Activity activity, Context context, ArrayList<NamedLocation> locations) {
        super(context, 0, locations);
        this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NamedLocation location = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                                        .inflate(
                                                R.layout.listitem_locationresult,
                                                parent,
                                                false
                                        );
        }

        TextView locationName = (TextView) convertView.findViewById(R.id.locationresult_name);
        locationName.setText(location.getName());
        locationName.setTag(location);

        locationName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NamedLocation location = (NamedLocation) v.getTag();
                Intent intent = new Intent(getContext(), LocationDetailActivity.class);
                intent.putExtra(SearchLocationActivity.LOCATION_NAME, location.getName());
                intent.putExtra(SearchLocationActivity.PLACE_ID, location.getPlaceId());
                activity.startActivity(intent);
            }
        });
        return convertView;
    }
}

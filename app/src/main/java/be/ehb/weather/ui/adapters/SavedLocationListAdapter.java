package be.ehb.weather.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import be.ehb.weather.R;
import be.ehb.weather.database.entities.SavedLocation;
import be.ehb.weather.models.GeocodedNamedLocation;
import be.ehb.weather.models.NamedLocation;
import be.ehb.weather.ui.activities.HomeActivity;
import be.ehb.weather.ui.activities.LocationDetailActivity;
import be.ehb.weather.ui.activities.SearchLocationActivity;

public class SavedLocationListAdapter extends ArrayAdapter<SavedLocation> {
    private HomeActivity activity;

    public SavedLocationListAdapter(HomeActivity activity, Context context, ArrayList<SavedLocation> locations) {
        super(context, 0, locations);
        this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SavedLocation location = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                                        .inflate(
                                                R.layout.listitem_savedlocation,
                                                parent,
                                                false
                                        );
        }

        TextView locationName = (TextView) convertView.findViewById(R.id.savedlocation_name);
        locationName.setText(location.getName());
        locationName.setTag(location);

        locationName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SavedLocation location = (SavedLocation) locationName.getTag();
                activity.updateSummarizedLocation(location);
            }
        });

        Button button = (Button) convertView.findViewById(R.id.savedlocation_viewdetail);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SavedLocation location = (SavedLocation) locationName.getTag();
                Intent intent = new Intent(getContext(), LocationDetailActivity.class);
                intent.putExtra(LocationDetailActivity.LOCATION_NAME, location.getName());
                intent.putExtra(LocationDetailActivity.PLACE_ID, location.getPlaceId());
                activity.startActivity(intent);
            }
        });
        return convertView;
    }
}

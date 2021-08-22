package be.ehb.weather.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import be.ehb.weather.R;

public class LocationDetailFragment extends Fragment {
    private TextView locationName;

    public LocationDetailFragment() {
        super(R.layout.fragment_locationdetail);
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        final View rootView = inflater.inflate(R.layout.fragment_locationdetail, container, false);
        locationName = (TextView) rootView.findViewById(R.id.locationName);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        locationName.setText("d");
    }
}

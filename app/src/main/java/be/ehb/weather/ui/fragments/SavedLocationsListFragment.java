package be.ehb.weather.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;

import be.ehb.weather.R;
import be.ehb.weather.config.Credentials;
import be.ehb.weather.database.entities.SavedLocation;
import be.ehb.weather.models.GeocodedNamedLocation;
import be.ehb.weather.repositories.ForecastRepository;
import be.ehb.weather.repositories.LocationRepository;
import be.ehb.weather.repositories.SavedLocationRepository;
import be.ehb.weather.ui.adapters.LocationResultAdapter;
import be.ehb.weather.ui.adapters.SavedLocationListAdapter;
import be.ehb.weather.ui.viewModels.LocationDetailViewModel;

public class SavedLocationsListFragment extends Fragment {
    private SavedLocationRepository savedLocationRepository;
    private SavedLocationListAdapter adapter;
    private SavedLocation summarizedSavedLocation;
    private View rootView;

    public SavedLocationsListFragment() {
        super(R.layout.fragment_savedlocationslist);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        savedLocationRepository = new SavedLocationRepository(
                getActivity().getApplication(),
                Executors.newSingleThreadExecutor()
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_savedlocationslist, container, false);

        adapter = new SavedLocationListAdapter(
                getActivity(),
                getActivity().getApplicationContext(),
                new ArrayList<>()
        );

        ListView listView = (ListView) rootView.findViewById(R.id.savedlocationslist_list);
        listView.setAdapter(adapter);

        Context context = getActivity().getApplicationContext();

        TextView heading = (TextView) rootView.findViewById(R.id.savedlocationslist_heading);
        heading.setText(
                Html.fromHtml(
                        String.format(
                                "<h1>%s</h1>",
                                context.getResources().getString(R.string.savedlocations_heading)
                        )
                )
        );

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        Context context = getActivity().getApplicationContext();

        savedLocationRepository.getSavedLocations().observeForever(new Observer<List<SavedLocation>>() {
            @Override
            public void onChanged(List<SavedLocation> savedLocations) {
                if (savedLocations.size() > 0) {
                    summarizedSavedLocation = savedLocations.get(0);
                } else {
                    TextView heading = (TextView) rootView.findViewById(R.id.savedlocationslist_heading);
                    heading.setText(
                            Html.fromHtml(
                                    String.format(
                                            "<h1>%s</h1><em>%s</em>",
                                            context.getResources().getString(R.string.savedlocations_heading),
                                            context.getResources().getString(R.string.savedlocations_empty)
                                    )
                            )
                    );
                }
                adapter.clear();
                adapter.addAll(savedLocations);
            }
        });

    }
}

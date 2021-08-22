package be.ehb.weather.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.Locale;
import java.util.concurrent.Executors;

import be.ehb.weather.R;
import be.ehb.weather.config.Credentials;
import be.ehb.weather.models.GeocodedNamedLocation;
import be.ehb.weather.repositories.ForecastRepository;
import be.ehb.weather.repositories.LocationRepository;
import be.ehb.weather.repositories.SavedLocationRepository;
import be.ehb.weather.ui.viewModels.LocationDetailViewModel;

public class LocationSummaryFragment extends Fragment {
    private LocationRepository locationRepository;
    private SavedLocationRepository savedLocationRepository;
    private ForecastRepository forecastRepository;
    private LocationDetailViewModel viewModel;

    private LiveData<GeocodedNamedLocation> location;

    private TextView locationNameLabel;

    public LocationSummaryFragment() {
        super(R.layout.fragment_locationsummary);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_locationsummary, container, false);
        locationNameLabel = rootView.findViewById(R.id.locationSummary_summaryHeading);

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        locationRepository = new LocationRepository(Credentials.GOOGLE_PLACES_API_KEY);
        savedLocationRepository = new SavedLocationRepository(
                getActivity().getApplication(),
                Executors.newSingleThreadExecutor()
        );
        forecastRepository = new ForecastRepository(
                Credentials.OPENWEATHER_API_KEY,
                Locale.getDefault().getLanguage()
        );

        viewModel = new LocationDetailViewModel(
                getActivity().getApplication(),
                locationRepository,
                savedLocationRepository,
                forecastRepository
        );

        location = new MutableLiveData<>();
    }

    @Override
    public void onResume() {
        super.onResume();
        Context context = getActivity().getApplicationContext();

        location.observeForever(new Observer<GeocodedNamedLocation>() {
            @Override
            public void onChanged(GeocodedNamedLocation geocodedNamedLocation) {
                locationNameLabel.setText(
                        Html.fromHtml(
                                String.format(
                                        "<h1>%s: %s</h1>",
                                        context.getResources().getString(R.string.summary_heading),
                                        geocodedNamedLocation.getName()
                                )
                        )
                );
            }
        });

    }
}

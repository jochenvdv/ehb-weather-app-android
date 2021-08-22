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
import be.ehb.weather.database.entities.SavedLocation;
import be.ehb.weather.models.GeocodedNamedLocation;
import be.ehb.weather.network.resources.LocationForecast;
import be.ehb.weather.repositories.ForecastRepository;
import be.ehb.weather.repositories.LocationRepository;
import be.ehb.weather.repositories.SavedLocationRepository;
import be.ehb.weather.ui.viewModels.LocationDetailViewModel;

public class LocationSummaryFragment extends Fragment {
    private LocationRepository locationRepository;
    private SavedLocationRepository savedLocationRepository;
    private ForecastRepository forecastRepository;
    private LocationDetailViewModel viewModel;

    private MutableLiveData<GeocodedNamedLocation> location;

    private TextView locationNameLabel;

    public LocationSummaryFragment() {
        super(R.layout.fragment_locationsummary);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_locationsummary, container, false);
        locationNameLabel = rootView.findViewById(R.id.locationSummary_summaryHeading);

        Context context = getActivity().getApplicationContext();

        locationNameLabel.setText(
                Html.fromHtml(
                        String.format(
                                "<h1>%s</h1><em>%s</em>",
                                context.getResources().getString(R.string.summary_heading),
                                context.getResources().getString(R.string.savedlocations_empty)
                        )
                )
        );

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

        TextView temperature = (TextView) getActivity().findViewById(R.id.locationSummary_summaryText);
        TextView rain = (TextView) getActivity().findViewById(R.id.locationSummary_summaryText2);
        TextView condition = (TextView) getActivity().findViewById(R.id.locationSummary_summaryText3);


        location.observe(getViewLifecycleOwner(), new Observer<GeocodedNamedLocation>() {
            @Override
            public void onChanged(GeocodedNamedLocation geocodedNamedLocation) {
                if (geocodedNamedLocation == null) {
                    locationNameLabel.setText(
                            Html.fromHtml(
                                    String.format(
                                            "<h1>%s</h1><em>%s</em>",
                                            context.getResources().getString(R.string.summary_heading),
                                            context.getResources().getString(R.string.savedlocations_empty)
                                    )
                            )
                    );
                    temperature.setText("");
                    rain.setText("");
                    condition.setText("");
                } else {
                    locationNameLabel.setText(
                            Html.fromHtml(
                                    String.format(
                                            "<h1>%s: %s</h1>",
                                            context.getResources().getString(R.string.summary_heading),
                                            geocodedNamedLocation.getName()
                                    )
                            )
                    );

                    forecastRepository.getForecastForLocation(
                            geocodedNamedLocation.getLatitude(),
                            geocodedNamedLocation.getLongitude()
                    ).observe(getViewLifecycleOwner(), new Observer<LocationForecast>() {
                        @Override
                        public void onChanged(LocationForecast locationForecast) {
                            temperature.setText(
                                    Html.fromHtml(
                                            String.format(
                                                    Locale.getDefault(),
                                                    "<p><strong>%s</strong><br />%.0f °C</p>",
                                                    context.getResources().getString(R.string.temperature_label),
                                                    locationForecast.getToday().getTemp().getDay()
                                            )
                                    )
                            );

                            rain.setText(
                                    Html.fromHtml(
                                            String.format(
                                                    Locale.getDefault(),
                                                    "<p><strong>%s</strong><br />%.0f%%</p>",
                                                    context.getResources().getString(R.string.rain_label),
                                                    locationForecast.getToday().getRain()
                                            )
                                    )
                            );

                            condition.setText(
                                    Html.fromHtml(
                                            String.format(
                                                    Locale.getDefault(),
                                                    "<p><strong>%s</strong><br />%s</p>",
                                                    context.getResources().getString(R.string.condition_label),
                                                    locationForecast.getToday().getWeather().get(0).getDescription()
                                            )
                                    )
                            );
                        }
                    });
                }
            }
        });

    }

    public void updateLocation(SavedLocation newLocation) {
        if (newLocation == null) {
            location.setValue(null);
        } else {
            location.setValue(newLocation.toGeocodedNamedLocation());
        }
    }
}

package be.ehb.weather.ui.activities;

import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import java.util.Locale;
import java.util.concurrent.Executors;

import be.ehb.weather.R;
import be.ehb.weather.config.Credentials;
import be.ehb.weather.models.GeocodedNamedLocation;
import be.ehb.weather.models.NamedLocation;
import be.ehb.weather.network.resources.LocationForecast;
import be.ehb.weather.repositories.ForecastRepository;
import be.ehb.weather.repositories.LocationRepository;
import be.ehb.weather.repositories.SavedLocationRepository;
import be.ehb.weather.ui.viewModels.LocationDetailViewModel;

public class LocationDetailActivity extends AppCompatActivity {
    private LocationRepository locationRepository;
    private SavedLocationRepository savedLocationRepository;
    private ForecastRepository forecastRepository;
    private LocationDetailViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        locationRepository = new LocationRepository(Credentials.GOOGLE_PLACES_API_KEY);
        savedLocationRepository = new SavedLocationRepository(
                getApplication(),
                Executors.newSingleThreadExecutor()
        );
        forecastRepository = new ForecastRepository(Credentials.OPENWEATHER_API_KEY);

        viewModel = new LocationDetailViewModel(
                getApplication(),
                locationRepository,
                savedLocationRepository,
                forecastRepository
        );

        setContentView(R.layout.activity_locationdetail);
        TextView summaryHeading = (TextView) findViewById(R.id.locationDetail_summaryHeading);
        summaryHeading.setText(Html.fromHtml("<h1>Summary</h1>"));

        TextView detailsHeading = (TextView) findViewById(R.id.locationDetail_detailsHeading);
        detailsHeading.setText(Html.fromHtml("<h1>Details</h1>"));

        TextView forecastHeading = (TextView) findViewById(R.id.locationDetail_forecastHeading);
        forecastHeading.setText(Html.fromHtml("<h1>Forecast for tomorrow</h1>"));
    }

    @Override
    protected void onResume() {
        super.onResume();

        LifecycleOwner activity = this;

        viewModel.getNamedLocation(
                new NamedLocation("Zwijndrecht, Belgium", "ChIJfYjDv472w0cRuIqogoRErz4")
        ).observe(this, new Observer<GeocodedNamedLocation>() {
            @Override
            public void onChanged(GeocodedNamedLocation geocodedNamedLocation) {
                ActionBar actionBar = getSupportActionBar();
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setTitle(geocodedNamedLocation.getName());
                actionBar.setSubtitle("Location forecast detail");

                viewModel.getLocationForecast(geocodedNamedLocation)
                        .observe(activity, new Observer<LocationForecast>() {
                    @Override
                    public void onChanged(LocationForecast locationForecast) {;
                        showSummary(locationForecast);
                        showDetails(locationForecast);
                        showForecast(locationForecast);
                        showButton(geocodedNamedLocation);
                    }
                });
            }
        });
    }

    private void showSummary(LocationForecast locationForecast) {
        TextView summary1 = (TextView) findViewById(R.id.locationDetail_summaryText);
        summary1.setText(
                Html.fromHtml(
                        String.format(
                                Locale.GERMAN,
                                "<p><strong>Temperatuur</strong><br />%.0f °C</p>",
                                locationForecast.getToday().getTemp().getDay()
                        )
                )
        );

        TextView summary2 = (TextView) findViewById(R.id.locationDetail_summaryText2);
        summary2.setText(
                Html.fromHtml(
                        String.format(
                                Locale.GERMAN,
                                "<p><strong>Neerslagkans</strong><br />%.0f %%</p>",
                                locationForecast.getToday().getRain()
                        )
                )
        );

        TextView summary3 = (TextView) findViewById(R.id.locationDetail_summaryText3);
        summary3.setText(
                Html.fromHtml(
                        String.format(
                                Locale.GERMAN,
                                "<p><strong>Conditie</strong><br />%s</p>",
                                locationForecast.getToday().getWeather().get(0).getDescription()
                        )
                )
        );
    }

    private void showDetails(LocationForecast locationForecast) {
        TextView details = (TextView) findViewById(R.id.locationDetail_detailsText);
        details.setText(
                Html.fromHtml(
                        String.format(
                                Locale.GERMAN,
                                "<strong>Min. temperature:</strong> %.0f °C<br />"
                                + "<strong>Max. temperature:</strong> %.0f °C<br />"
                                + "<strong>Clouds:</strong> %d %%<br />"
                                + "<strong>Humidity:</strong> %d %%<br />"
                                + "<strong>Wind speed:</strong> %d km/h<br />",
                                locationForecast.getToday().getTemp().getMin(),
                                locationForecast.getToday().getTemp().getMax(),
                                locationForecast.getToday().getClouds(),
                                locationForecast.getToday().getHumidity(),
                                locationForecast.getToday().getWindSpeed()
                        )
                )
        );

        TextView details2 = (TextView) findViewById(R.id.locationDetail_detailsText2);
        details2.setText(
                Html.fromHtml(
                        String.format(
                                Locale.GERMAN,
                                        "<strong>UV index:</strong> %.0f<br />"
                                        + "<strong>Sunrise:</strong> %s<br />"
                                        + "<strong>Sunset:</strong> %s<br />"
                                        + "<strong>Visibility:</strong> %d meters<br />"
                                        + "<strong>Wind degree:</strong> %d<br />",
                                locationForecast.getToday().getUvi(),
                                locationForecast.getToday().getSunrise(),
                                locationForecast.getToday().getSunset(),
                                locationForecast.getToday().getVisibility(),
                                locationForecast.getToday().getWindDeg()
                        )
                )
        );
    }

    private void showForecast(LocationForecast locationForecast) {
        TextView tomorrow = (TextView) findViewById(R.id.locationDetail_forecastText);
        tomorrow.setText(
                Html.fromHtml(
                        String.format(
                                Locale.GERMAN,
                                "<strong>Temperature:</strong> min %.0f °C, max %.0f °C<br />"
                                + "<strong>Condition:</strong> %s<br />"
                                + "<strong>Neerslagkans:</strong> %.0f%%<br />"
                                + "<strong>Bewolking:</strong> %d%%<br />",
                                locationForecast.getDaily().get(1).getTemp().getMin(),
                                locationForecast.getDaily().get(1).getTemp().getMax(),
                                locationForecast.getDaily().get(1).getWeather().get(0).getDescription(),
                                locationForecast.getDaily().get(1).getRain(),
                                locationForecast.getDaily().get(1).getClouds()
                        )
                )
        );
    }

    private void configureButtonWhenUnsaved(Button button, GeocodedNamedLocation geocodedNamedLocation) {
        button.setText("Save this location");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.saveLocation(geocodedNamedLocation);
                configureButtonWhenSaved(button, geocodedNamedLocation);
            }
        });
    }

    private void configureButtonWhenSaved(Button button, GeocodedNamedLocation geocodedNamedLocation) {
        button.setText("Forget this location");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.forgetLocation(geocodedNamedLocation);
                configureButtonWhenUnsaved(button, geocodedNamedLocation);
            }
        });
    }

    private void showButton(GeocodedNamedLocation geocodedNamedLocation) {
        Button button = (Button) findViewById(R.id.locationDetail_saveButton);

        viewModel.isLocationSaved(geocodedNamedLocation).observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isSaved) {
                if (!isSaved) {
                    configureButtonWhenUnsaved(button, geocodedNamedLocation);
                } else {
                    configureButtonWhenSaved(button, geocodedNamedLocation);
                }
            }
        });
    }
}

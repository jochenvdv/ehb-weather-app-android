package be.ehb.weather.ui.activities;

import android.content.Context;
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

        Context context = getApplicationContext();

        locationRepository = new LocationRepository(Credentials.GOOGLE_PLACES_API_KEY);
        savedLocationRepository = new SavedLocationRepository(
                getApplication(),
                Executors.newSingleThreadExecutor()
        );
        forecastRepository = new ForecastRepository(
                Credentials.OPENWEATHER_API_KEY,
                Locale.getDefault().getLanguage()
        );

        viewModel = new LocationDetailViewModel(
                getApplication(),
                locationRepository,
                savedLocationRepository,
                forecastRepository
        );

        setContentView(R.layout.activity_locationdetail);
        TextView summaryHeading = (TextView) findViewById(R.id.locationDetail_summaryHeading);
        summaryHeading.setText(
                Html.fromHtml(
                        String.format(
                                "<h1>%s</h1>",
                                context.getResources().getString(R.string.summary_heading)
                        )
                )
        );

        TextView detailsHeading = (TextView) findViewById(R.id.locationDetail_detailsHeading);
        detailsHeading.setText(
                Html.fromHtml(
                        String.format(
                                "<h1>%s</h1>",
                                context.getResources().getString(R.string.details_heading)
                        )
                )
        );

        TextView forecastHeading = (TextView) findViewById(R.id.locationDetail_forecastHeading);
        forecastHeading.setText(
                Html.fromHtml(
                    String.format(
                            "<h1>%s</h1>",
                            context.getResources().getString(R.string.forecast_heading)
                    )
                )
        );
    }

    @Override
    protected void onResume() {
        super.onResume();

        Context context = getApplicationContext();
        LifecycleOwner activity = this;

        viewModel.getNamedLocation(
                new NamedLocation("Zwijndrecht, Belgium", "ChIJfYjDv472w0cRuIqogoRErz4")
        ).observe(this, new Observer<GeocodedNamedLocation>() {
            @Override
            public void onChanged(GeocodedNamedLocation geocodedNamedLocation) {
                ActionBar actionBar = getSupportActionBar();
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setTitle(geocodedNamedLocation.getName());
                actionBar.setSubtitle(context.getResources().getString(R.string.location_detail_subtitle));

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
        Context context = getApplicationContext();

        TextView summary1 = (TextView) findViewById(R.id.locationDetail_summaryText);
        summary1.setText(
                Html.fromHtml(
                        String.format(
                                Locale.GERMAN,
                                "<p><strong>%s</strong><br />%.0f °C</p>",
                                context.getResources().getString(R.string.temperature_label),
                                locationForecast.getToday().getTemp().getDay()
                        )
                )
        );

        TextView summary2 = (TextView) findViewById(R.id.locationDetail_summaryText2);
        summary2.setText(
                Html.fromHtml(
                        String.format(
                                Locale.GERMAN,
                                "<p><strong>%s</strong><br />%.0f %%</p>",
                                context.getResources().getString(R.string.rain_label),
                                locationForecast.getToday().getRain()
                        )
                )
        );

        TextView summary3 = (TextView) findViewById(R.id.locationDetail_summaryText3);
        summary3.setText(
                Html.fromHtml(
                        String.format(
                                Locale.GERMAN,
                                "<p><strong>%s</strong><br />%s</p>",
                                context.getResources().getString(R.string.condition_label),
                                locationForecast.getToday().getWeather().get(0).getDescription()
                        )
                )
        );
    }

    private void showDetails(LocationForecast locationForecast) {
        Context context = getApplicationContext();

        TextView details = (TextView) findViewById(R.id.locationDetail_detailsText);
        details.setText(
                Html.fromHtml(
                        String.format(
                                Locale.GERMAN,
                                "<strong>%s:</strong> %.0f °C<br />"
                                + "<strong>%s:</strong> %.0f °C<br />"
                                + "<strong>%s:</strong> %d %%<br />"
                                + "<strong>%s:</strong> %d %%<br />"
                                + "<strong>%s:</strong> %d km/h<br />",
                                context.getResources().getString(R.string.min_temperature_label),
                                locationForecast.getToday().getTemp().getMin(),
                                context.getResources().getString(R.string.max_temperature_label),
                                locationForecast.getToday().getTemp().getMax(),
                                context.getResources().getString(R.string.cloudiness_label),
                                locationForecast.getToday().getClouds(),
                                context.getResources().getString(R.string.humidity_label),
                                locationForecast.getToday().getHumidity(),
                                context.getResources().getString(R.string.wind_speed_label),
                                locationForecast.getToday().getWindSpeed()
                        )
                )
        );

        TextView details2 = (TextView) findViewById(R.id.locationDetail_detailsText2);
        details2.setText(
                Html.fromHtml(
                        String.format(
                                Locale.GERMAN,
                                        "<strong>%s:</strong> %.0f<br />"
                                        + "<strong>%s:</strong> %s<br />"
                                        + "<strong>%s:</strong> %s<br />"
                                        + "<strong>%s:</strong> %d %s<br />"
                                        + "<strong>%s:</strong> %d<br />",
                                context.getResources().getString(R.string.uv_index_label),
                                locationForecast.getToday().getUvi(),
                                context.getResources().getString(R.string.sunrise_label),
                                locationForecast.getToday().getSunrise(),
                                context.getResources().getString(R.string.sunset_label),
                                locationForecast.getToday().getSunset(),
                                context.getResources().getString(R.string.visibility_label),
                                locationForecast.getToday().getVisibility(),
                                context.getResources().getString(R.string.meters_label),
                                context.getResources().getString(R.string.wind_degree_label),
                                locationForecast.getToday().getWindDeg()
                                )
                )
        );
    }

    private void showForecast(LocationForecast locationForecast) {
        Context context = getApplicationContext();

        TextView tomorrow = (TextView) findViewById(R.id.locationDetail_forecastText);
        tomorrow.setText(
                Html.fromHtml(
                        String.format(
                                Locale.GERMAN,
                                "<strong>%s:</strong> %s %.0f °C, %s %.0f °C<br />"
                                + "<strong>%s:</strong> %s<br />"
                                + "<strong>%s:</strong> %.0f%%<br />"
                                + "<strong>%s:</strong> %d%%<br />",
                                context.getResources().getString(R.string.condition_label),
                                context.getResources().getString(R.string.min_label),
                                locationForecast.getDaily().get(1).getTemp().getMin(),
                                context.getResources().getString(R.string.max_label),
                                locationForecast.getDaily().get(1).getTemp().getMax(),
                                context.getResources().getString(R.string.wind_degree_label),
                                locationForecast.getDaily().get(1).getWeather().get(0).getDescription(),
                                context.getResources().getString(R.string.rain_label),
                                locationForecast.getDaily().get(1).getRain(),
                                context.getResources().getString(R.string.cloudiness_label),
                                locationForecast.getDaily().get(1).getClouds()
                        )
                )
        );
    }

    private void configureButtonWhenUnsaved(Button button, GeocodedNamedLocation geocodedNamedLocation) {
        Context context = getApplicationContext();

        button.setText(context.getResources().getString(R.string.save_this_location_button));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.saveLocation(geocodedNamedLocation);
                configureButtonWhenSaved(button, geocodedNamedLocation);
            }
        });
    }

    private void configureButtonWhenSaved(Button button, GeocodedNamedLocation geocodedNamedLocation) {
        Context context = getApplicationContext();

        button.setText(context.getResources().getString(R.string.forget_this_location_button));
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

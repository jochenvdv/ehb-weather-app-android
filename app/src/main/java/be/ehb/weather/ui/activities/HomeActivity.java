package be.ehb.weather.ui.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import be.ehb.weather.LocationsMain;
import be.ehb.weather.R;
import be.ehb.weather.models.GeocodedNamedLocation;
import be.ehb.weather.models.NamedLocation;
import be.ehb.weather.repositories.LocationRepository;
import be.ehb.weather.repositories.SavedLocationRepository;
import be.ehb.weather.ui.viewModels.LocationDetailViewModel;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locationdetail);
    }
}
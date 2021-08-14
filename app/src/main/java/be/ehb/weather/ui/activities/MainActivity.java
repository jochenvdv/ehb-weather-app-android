package be.ehb.weather.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import be.ehb.weather.LocationsMain;
import be.ehb.weather.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LocationsMain.main(new String[0]);
    }
}
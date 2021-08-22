package be.ehb.weather.ui.activities;

import android.app.Activity;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import java.util.ArrayList;
import java.util.List;

import be.ehb.weather.R;
import be.ehb.weather.config.Credentials;
import be.ehb.weather.models.NamedLocation;
import be.ehb.weather.repositories.LocationRepository;
import be.ehb.weather.ui.adapters.LocationResultAdapter;
import be.ehb.weather.ui.viewModels.SearchViewModel;

public class SearchLocationActivity extends AppCompatActivity {
    public static final String LOCATION_NAME = "locationName";
    public static final String PLACE_ID = "placeId";
    private SearchViewModel viewModel;

    public SearchLocationActivity() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchlocation);

        LocationRepository locationRepository = new LocationRepository(
                Credentials.GOOGLE_PLACES_API_KEY
        );

        viewModel = new SearchViewModel(getApplication(), locationRepository);

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            ActionBar actionBar = getSupportActionBar();

            actionBar.setTitle(
                    getApplicationContext().getResources().getString(R.string.searchresults_title)
            );
            actionBar.setSubtitle(
                    String.format(
                            "\"%s\"",
                            query
                    )
            );
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);

            search(query);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void search(String query) {
        Activity activity = this;
        LocationResultAdapter adapter = new LocationResultAdapter(
                activity,
                getApplicationContext(),
                new ArrayList<>()
        );
        ListView listView = (ListView) findViewById(R.id.locationresults_list);
        listView.setAdapter(adapter);

        viewModel.searchLocation(query).observeForever(new Observer<List<NamedLocation>>() {
            @Override
            public void onChanged(List<NamedLocation> namedLocations) {
                adapter.addAll(namedLocations);
            }
        });
    }
}

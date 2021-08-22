package be.ehb.weather.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import be.ehb.weather.R;
import be.ehb.weather.database.entities.SavedLocation;
import be.ehb.weather.ui.fragments.LocationSummaryFragment;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true);
        searchView.setSubmitButtonEnabled(true);

        return true;
    }

    public void updateSummarizedLocation(SavedLocation location) {
        FragmentContainerView summaryView =
                (FragmentContainerView) findViewById(R.id.locationSummary_fragment);

        FragmentManager fragmentManager = (FragmentManager) getSupportFragmentManager();
        LocationSummaryFragment summaryFragment =
                (LocationSummaryFragment) fragmentManager.findFragmentById(summaryView.getId());
        summaryFragment.updateLocation(location);
    }

/*    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_button:
                // navigate to search location activity
                Intent intent = new Intent(this, SearchLocationActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }*/
}
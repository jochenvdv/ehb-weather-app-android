package be.ehb.weather.ui.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import be.ehb.weather.models.NamedLocation;
import be.ehb.weather.repositories.LocationRepository;

public class SearchViewModel {
    private LocationRepository locationRepository;
    private LiveData<List<NamedLocation>> locationResults;

    public SearchViewModel(
            @NonNull Application application,
            LocationRepository locationRepository
    ) {
        this.locationRepository = locationRepository;
    }

    public LiveData<List<NamedLocation>> searchLocation(String query) {
        locationResults = locationRepository.searchLocations(query);
        return locationResults;
    }
}

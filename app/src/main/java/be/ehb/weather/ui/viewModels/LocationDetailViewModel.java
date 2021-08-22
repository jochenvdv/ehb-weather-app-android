package be.ehb.weather.ui.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import be.ehb.weather.database.entities.SavedLocation;
import be.ehb.weather.models.GeocodedNamedLocation;
import be.ehb.weather.models.NamedLocation;
import be.ehb.weather.network.resources.LocationForecast;
import be.ehb.weather.repositories.ForecastRepository;
import be.ehb.weather.repositories.LocationRepository;
import be.ehb.weather.repositories.SavedLocationRepository;

public class LocationDetailViewModel {
    private LocationRepository locationRepository;
    private SavedLocationRepository savedLocationRepository;
    private ForecastRepository forecastRepository;
    private LiveData<GeocodedNamedLocation> location;
    private LiveData<SavedLocation> savedLocation;
    private LiveData<LocationForecast> locationForecast;

    public LocationDetailViewModel(
            @NonNull Application application,
            LocationRepository locationRepository,
            SavedLocationRepository savedLocationRepository,
            ForecastRepository forecastRepository
    ) {
        this.locationRepository = locationRepository;
        this.savedLocationRepository = savedLocationRepository;
        this.forecastRepository = forecastRepository;
    }

    public LiveData<GeocodedNamedLocation> getNamedLocation(NamedLocation namedLocation) {
        location = locationRepository.getLocationDetail(namedLocation);
        return location;
    }

    public LiveData<GeocodedNamedLocation> getSavedLocation(SavedLocation savedLocation) {
        this.savedLocation = new MutableLiveData<>(savedLocation);
        return getNamedLocation(savedLocation.toNamedLocation());
    }

    public LiveData<LocationForecast> getLocationForecast(GeocodedNamedLocation geocodedNamedLocation) {
        locationForecast = forecastRepository.getForecastForLocation(
                geocodedNamedLocation.getLatitude(),
                geocodedNamedLocation.getLongitude()
        );

        return locationForecast;
    }

    public LiveData<Boolean> isLocationSaved(GeocodedNamedLocation geocodedNamedLocation) {
        return savedLocationRepository.isSavedLocation(geocodedNamedLocation);
    }

    public void saveLocation(GeocodedNamedLocation geocodedNamedLocation) {
        savedLocationRepository.createSavedLocation(SavedLocation.fromGeocodedNamedLocation(geocodedNamedLocation));
    }

    public void forgetLocation(GeocodedNamedLocation geocodedNamedLocation) {
        savedLocationRepository.deleteSavedLocation(geocodedNamedLocation);
    }
}

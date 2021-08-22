package be.ehb.weather.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;

import be.ehb.weather.database.WeatherDatabase;
import be.ehb.weather.database.daos.SavedLocationDao;
import be.ehb.weather.database.entities.SavedLocation;
import be.ehb.weather.models.GeocodedNamedLocation;

public class SavedLocationRepository {
    private Executor executor;
    private SavedLocationDao savedLocationDao;
    private LiveData<List<SavedLocation>> savedLocations;

    public SavedLocationRepository(Application application, Executor executor) {
        this.executor = executor;
        this.savedLocationDao = WeatherDatabase.getDatabase(application).savedLocationDao();
        this.savedLocations = savedLocationDao.getAll();
    }

    public void createSavedLocation(SavedLocation savedLocation) {
        executor.execute(() -> {
            savedLocationDao.create(savedLocation);
            savedLocations = savedLocationDao.getAll();
        });
    }

    public void deleteSavedLocation(GeocodedNamedLocation geocodedNamedLocation) {
        executor.execute(() -> {
            savedLocationDao.deleteByPlaceId(geocodedNamedLocation.getPlaceId());
            savedLocations = savedLocationDao.getAll();
        });
    }

    public LiveData<List<SavedLocation>> getSavedLocations() {
        return savedLocationDao.getAll();
    }

    public LiveData<Boolean> isSavedLocation(GeocodedNamedLocation geocodedNamedLocation) {
        return savedLocationDao.existsByPlaceId(geocodedNamedLocation.getPlaceId());
    }
}

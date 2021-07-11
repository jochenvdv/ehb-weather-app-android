package be.ehb.weather.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;

import be.ehb.weather.database.WeatherDatabase;
import be.ehb.weather.database.daos.SavedLocationDao;
import be.ehb.weather.database.entities.SavedLocation;

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
        executor.execute(() -> savedLocationDao.create(savedLocation));
    }

    public void deleteSavedLocation(SavedLocation savedLocation) {
        executor.execute(() -> savedLocationDao.delete(savedLocation));
    }

    public LiveData<List<SavedLocation>> getSavedLocations() {
        return savedLocations;
    }
}

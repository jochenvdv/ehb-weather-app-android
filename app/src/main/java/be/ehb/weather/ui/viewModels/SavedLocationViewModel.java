package be.ehb.weather.ui.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import be.ehb.weather.database.entities.SavedLocation;
import be.ehb.weather.repositories.SavedLocationRepository;

public class SavedLocationViewModel extends ViewModel {
    private final SavedLocationRepository savedLocationRepository;
    private LiveData<List<SavedLocation>> savedLocations;

    public SavedLocationViewModel(
            @NonNull Application application,
            SavedLocationRepository savedLocationRepository
    ) {
        super();
        this.savedLocationRepository = savedLocationRepository;
        savedLocations = savedLocationRepository.getSavedLocations();
    }

    public void createSavedLocation(SavedLocation location) {
        savedLocationRepository.createSavedLocation(location);
    }

    public void deleteSavedLocation(SavedLocation savedLocation) {
        savedLocationRepository.deleteSavedLocation(savedLocation);
    }

    public LiveData<List<SavedLocation>> getSavedLocations() {
        return savedLocations;
    }
}

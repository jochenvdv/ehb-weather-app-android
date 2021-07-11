package be.ehb.weather.repositories;

import androidx.lifecycle.LiveData;

import be.ehb.weather.database.entities.SavedLocation;
import be.ehb.weather.network.clients.OpenWeatherApiClient;
import be.ehb.weather.network.resources.LocationForecast;

public class ForecastRepository {
    private OpenWeatherApiClient apiClient;

    public ForecastRepository() {
        apiClient = new OpenWeatherApiClient("");
    }

    public LiveData<LocationForecast> getForecastForLocation(SavedLocation location) {
        return apiClient.getForecastForLocation(
                location.getLatitude(),
                location.getLongitude()
        );
    }
}

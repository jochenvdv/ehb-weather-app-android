package be.ehb.weather.repositories;

import androidx.lifecycle.LiveData;

import be.ehb.weather.database.entities.SavedLocation;
import be.ehb.weather.network.clients.OpenWeatherApiClient;
import be.ehb.weather.network.resources.LocationForecast;

public class ForecastRepository {
    private OpenWeatherApiClient apiClient;

    public ForecastRepository(String appId, String language) {
        apiClient = new OpenWeatherApiClient(appId, language);
    }

    public LiveData<LocationForecast> getForecastForLocation(double latitude, double longitude) {
        return apiClient.getForecastForLocation(latitude, longitude);
    }
}

package be.ehb.weather.repositories;

import androidx.lifecycle.LiveData;

import be.ehb.weather.database.entities.SavedLocation;
import be.ehb.weather.network.clients.OpenWeatherApiClient;
import be.ehb.weather.network.resources.LocationForecast;

public class ForecastRepository {
    private OpenWeatherApiClient apiClient;

    public ForecastRepository(String appId) {
        apiClient = new OpenWeatherApiClient(appId);
    }

    public LiveData<LocationForecast> getForecastForLocation(double latitude, double longitude) {
        return apiClient.getForecastForLocation(latitude, longitude);
    }
}

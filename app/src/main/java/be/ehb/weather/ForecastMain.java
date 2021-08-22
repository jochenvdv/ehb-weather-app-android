package be.ehb.weather;

import androidx.lifecycle.LiveData;

import be.ehb.weather.network.resources.LocationForecast;
import be.ehb.weather.repositories.ForecastRepository;

public class ForecastMain {
    public static void main(String[] args) {
        ForecastRepository forecastRepository = new ForecastRepository(
                ""
        );
        LiveData<LocationForecast> forecast = forecastRepository.getForecastForLocation(
                51.816929,
                4.648220
        );
    }
}

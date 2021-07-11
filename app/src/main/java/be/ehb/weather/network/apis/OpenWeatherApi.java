package be.ehb.weather.network.apis;

import be.ehb.weather.network.resources.LocationForecast;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherApi {
    String BASE_URL = "https://api.openweathermap.org/data/2.5";

    @GET("/onecall")
    Call<LocationForecast> getForecastForLocation(
            @Query("lat") double latitude,
            @Query("lon") double longitude,
            @Query("appid") String appId,
            @Query("lang") String language,
            @Query("units") String units
    );
}

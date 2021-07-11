package be.ehb.weather.network.clients;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import be.ehb.weather.network.apis.OpenWeatherApi;
import be.ehb.weather.network.resources.LocationForecast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OpenWeatherApiClient {
    private static final String DEFAULT_LANGUAGE = "en";
    private static final String DEFAULT_UNITS = "metric";

    private final String appId;
    private final OpenWeatherApi api;

    public OpenWeatherApiClient(String appId) {
        this.appId = appId;

        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(OpenWeatherApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        this.api = retrofit.create(OpenWeatherApi.class);
    }

    public LiveData<LocationForecast> getForecastForLocation(double latitude, double longitude) {
        MutableLiveData<LocationForecast> forecast = new MutableLiveData<>();
        Call<LocationForecast> call = api.getForecastForLocation(
                latitude,
                longitude,
                appId,
                DEFAULT_LANGUAGE,
                DEFAULT_UNITS
        );

        call.enqueue(new Callback<LocationForecast>() {
            @Override
            public void onResponse(Call<LocationForecast> call, Response<LocationForecast> response) {
                forecast.setValue(response.body());
            }

            @Override
            public void onFailure(Call<LocationForecast> call, Throwable t) {
                // TODO: handle error
            }
        });

        return forecast;
    }
}

package be.ehb.weather.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.maps.FindPlaceFromTextRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.PlaceAutocompleteRequest;
import com.google.maps.PlaceDetailsRequest;
import com.google.maps.PlacesApi;
import com.google.maps.QueryAutocompleteRequest;
import com.google.maps.model.AutocompletePrediction;
import com.google.maps.model.PlaceAutocompleteType;
import com.google.maps.model.PlaceDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import be.ehb.weather.models.GeocodedNamedLocation;
import be.ehb.weather.models.NamedLocation;
import be.ehb.weather.network.resources.LocationForecast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationRepository {
    private GeoApiContext geoApiContext;

    public LocationRepository(String apiKey) {
        geoApiContext = new GeoApiContext.Builder()
                .apiKey(apiKey)
                .build();
    }

    public LiveData<List<NamedLocation>> searchLocations(String query) {
        MutableLiveData<List<NamedLocation>> results = new MutableLiveData<>();

        PlaceAutocompleteRequest request = PlacesApi.placeAutocomplete(
                geoApiContext,
                query,
                new PlaceAutocompleteRequest.SessionToken()
        );

        request.types(PlaceAutocompleteType.CITIES);

        request.setCallback(new PendingResult.Callback<AutocompletePrediction[]>() {
            @Override
            public void onResult(AutocompletePrediction[] result) {
                List<NamedLocation> locations = new ArrayList<>();

                for (AutocompletePrediction placeResult : result) {
                    locations.add(new NamedLocation(
                                    placeResult.description,
                                    placeResult.placeId
                    ));
                }

                results.postValue(locations);
                System.out.println("ok");
            }

            @Override
            public void onFailure(Throwable e) {
                // TODO: handle error
                System.out.println("error");
            }
        });

        return results;
    }

    public LiveData<GeocodedNamedLocation> getLocationDetail(NamedLocation location) {
        MutableLiveData<GeocodedNamedLocation> result = new MutableLiveData<>();

        PlaceDetailsRequest request = PlacesApi.placeDetails(
                geoApiContext,
                location.getPlaceId()
        );

        request.setCallback(new PendingResult.Callback<PlaceDetails>() {
            @Override
            public void onResult(PlaceDetails placeResult) {
                result.postValue(new GeocodedNamedLocation(
                        location.getName(),
                        location.getPlaceId(),
                        placeResult.geometry.location.lat,
                        placeResult.geometry.location.lng
                ));

                System.out.println("ok");
            }

            @Override
            public void onFailure(Throwable e) {
                // TODO: handle error
                System.out.println("error");
            }
        });

        return result;
    }
}

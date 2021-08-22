package be.ehb.weather.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import be.ehb.weather.models.GeocodedNamedLocation;
import be.ehb.weather.models.NamedLocation;

@Entity(tableName = "saved_locations")
public class SavedLocation {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private String name;
    private double latitude;
    private double longitude;
    private String placeId;

    public SavedLocation(
            @NonNull String name,
            @NonNull double latitude,
            @NonNull double longitude,
            @NonNull String placeId
    ) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.placeId = placeId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public static SavedLocation fromGeocodedNamedLocation(GeocodedNamedLocation location) {
        return new SavedLocation(
                location.getName(),
                location.getLatitude(),
                location.getLongitude(),
                location.getPlaceId()
        );
    }

    public NamedLocation toNamedLocation() {
        return new NamedLocation(name, placeId);
    }
}

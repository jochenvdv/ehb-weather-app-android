package be.ehb.weather.models;

public class GeocodedNamedLocation extends NamedLocation {
    private double latitude;
    private double longitude;

    public GeocodedNamedLocation(String name, String placeId, double latitude, double longitude) {
        super(name, placeId);
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}

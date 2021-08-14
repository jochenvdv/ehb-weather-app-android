package be.ehb.weather.models;

public class NamedLocation {
    private String name;
    private String placeId;

    public NamedLocation(String name, String placeId) {
        this.name = name;
        this.placeId = placeId;
    }

    public String getName() {
        return name;
    }

    public String getPlaceId() {
        return placeId;
    }
}

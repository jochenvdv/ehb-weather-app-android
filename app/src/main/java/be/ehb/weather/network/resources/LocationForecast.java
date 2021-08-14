package be.ehb.weather.network.resources;

import java.util.List;

public class LocationForecast {
    private double lat;
    private double lon;
    private String timezone;
    private List<ForecastData> daily;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public List<ForecastData> getDaily() {
        return daily;
    }

    public void setDaily(List<ForecastData> daily) {
        this.daily = daily;
    }
}

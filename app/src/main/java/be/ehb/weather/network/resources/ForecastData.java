package be.ehb.weather.network.resources;

import java.util.List;

public class ForecastData {
    private List<WeatherConditionData> weather;
    private TemperatureDataResponse temp;
    private long dt;
    private long sunrise;
    private long sunset;
    private int clouds;
    private double rain;
    private double uvi;
    private double windSpeed;
    private int windDeg;
    private int humidity;

    public List<WeatherConditionData> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherConditionData> weather) {
        this.weather = weather;
    }

    public TemperatureDataResponse getTemp() {
        return temp;
    }

    public void setTemp(TemperatureDataResponse temp) {
        this.temp = temp;
    }

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public long getSunrise() {
        return sunrise;
    }

    public void setSunrise(long sunrise) {
        this.sunrise = sunrise;
    }

    public long getSunset() {
        return sunset;
    }

    public void setSunset(long sunset) {
        this.sunset = sunset;
    }

    public int getClouds() {
        return clouds;
    }

    public void setClouds(int clouds) {
        this.clouds = clouds;
    }

    public double getRain() {
        return rain;
    }

    public void setRain(double rain) {
        this.rain = rain;
    }

    public double getUvi() {
        return uvi;
    }

    public void setUvi(double uvi) {
        this.uvi = uvi;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public int getWindDeg() {
        return windDeg;
    }

    public void setWindDeg(int windDeg) {
        this.windDeg = windDeg;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }
}

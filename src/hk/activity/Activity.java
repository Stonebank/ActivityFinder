package hk.activity;

import hk.activity.coordinate.Coordinate;
import hk.activity.weather.WeatherType;

import java.util.Arrays;

public class Activity {

    private final String name;
    private final String city;

    private final Coordinate coordinate;

    private final WeatherType[] weatherTypes;

    public Activity(String name, String city, Coordinate coordinate, WeatherType... weatherTypes) {
        this.name = name;
        this.city = city;
        this.coordinate = coordinate;
        this.weatherTypes = weatherTypes;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public WeatherType[] getWeatherTypes() {
        return weatherTypes;
    }

    @Override
    public String toString() {
        return "[Name: " + getName() + " - City: " + getCity() + " - Coordinates: " + getCoordinate() + " - WeatherType " + Arrays.toString(getWeatherTypes()) + "]";
    }

}

package hk.activity;

import hk.activity.coordinate.Coordinate;
import hk.activity.weather.WeatherType;

import java.util.ArrayList;

public class Activity {

    public static final ArrayList<Activity> activities = new ArrayList<>();

    private final String name;
    private final String city;

    private final Coordinate coordinate;

    private final WeatherType[] weatherTypes;

    private int points;

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

    public WeatherType getBestWeather() {
        return weatherTypes[0];
    }

    public WeatherType getWorstWeather() {
        if (weatherTypes.length > 0)
            return weatherTypes[weatherTypes.length - 1];
        return null;
    }

    public void addPoints(int amount) {
        this.points += amount;
    }

    public void removePoints(int amount) {
        this.points -= amount;
    }

    public int getPoints() {
        return points;
    }

    public static Activity getActivity(String name) {
        for (Activity activity : activities) {
            if (activity.getName().equalsIgnoreCase(name))
                return activity;
        }
        return null;
    }

    @Override
    public String toString() {
        return "[Name: " + getName() + " - City: " + getCity() + " - Coordinates: " + getCoordinate() + " - Best weather: " + getBestWeather() + " " + (getWorstWeather() == null ? "" : "- Worst weather: " + getWorstWeather()) + " - Points: " + points + "]";
    }

}

package hk.location;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import hk.activity.weather.WeatherType;
import hk.settings.Settings;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;

public class UserLocation {

    private final double latitude;
    private final double longitude;

    private BigDecimal current_temperature;
    private BigDecimal maximum_temperature;
    private BigDecimal minimum_temperature;

    private BigDecimal feels_like;

    private WeatherType weatherType;

    private JSONObject jsonObject;

    public UserLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void fetch() {
        try {
            URL url = new URL("https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=" + Settings.WEATHER_API_KEY + "&units=" + Settings.WEATHER_UNIT_OUTPUT);
            JSONTokener jsonTokener = new JSONTokener(url.openStream());
            jsonObject = new JSONObject(jsonTokener);

            if (Settings.DEBUG)
                System.out.println("https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=" + Settings.WEATHER_API_KEY + "&units=" + Settings.WEATHER_UNIT_OUTPUT);

        } catch (IOException e) {
            System.err.println("ERROR! Could not fetch data from OpenWeatherAPI");
            e.printStackTrace();
        }

        if (Settings.DEBUG) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            System.out.println(gson.toJson(jsonObject));
        }
    }

    public void parse() {
        current_temperature = (BigDecimal) jsonObject.getJSONObject("main").get("temp");
        maximum_temperature = (BigDecimal) jsonObject.getJSONObject("main").get("temp_max");
        minimum_temperature = (BigDecimal) jsonObject.getJSONObject("main").get("temp_min");
        feels_like = (BigDecimal) jsonObject.getJSONObject("main").get("feels_like");

        String weather = (String) jsonObject.getJSONArray("weather").getJSONObject(0).get("description");
        switch (weather) {
            case "clear sky", "sunny", "sun" -> weatherType = WeatherType.SUNNY;
            case "overcast clouds", "few clouds", "scattered clouds", "broken clouds" -> weatherType = WeatherType.CLOUD;
            case "shower rain", "rain", "mist", "drizzle" -> weatherType = WeatherType.RAIN;
            case "thunderstorm", "tornado" -> weatherType = WeatherType.STORM;
            case "snow" -> weatherType = WeatherType.SNOW;
            default -> System.err.println("ERROR! WeatherType not detected");
        }

    }

    public double calculateDistance(double latitude, double longitude) {
        int earth_radius = 6371;

        double lat1 = Math.min(this.latitude, latitude);
        double lat2 = Math.max(this.latitude, latitude);

        double lon1 = Math.min(this.longitude, longitude);
        double lon2 = Math.max(this.longitude, longitude);

        double deltaLat = Math.toRadians(lat2 - lat1);
        double deltaLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) + Math.cos(lat1) * Math.cos(lat2) * Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return earth_radius * c;
    }

    public BigDecimal getCurrent_temperature() {
        return current_temperature;
    }

    public BigDecimal getMaximum_temperature() {
        return maximum_temperature;
    }

    public BigDecimal getMinimum_temperature() {
        return minimum_temperature;
    }

    public BigDecimal getFeels_like() {
        return feels_like;
    }

    public WeatherType getWeatherType() {
        return weatherType;
    }

}

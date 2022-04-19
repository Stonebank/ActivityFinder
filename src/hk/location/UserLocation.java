package hk.location;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import hk.settings.Settings;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.net.URL;

public class UserLocation {

    public static void main(String[] args) throws IOException {
        new UserLocation(Settings.DEFAULT_LATITUDE, Settings.DEFAULT_LONGITUDE);
    }

    private final double latitude;
    private final double longitude;

    private final URL url;

    public UserLocation(double latitude, double longitude) throws IOException {
        this.latitude = latitude;
        this.longitude = longitude;

        this.url = new URL("https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=" + Settings.WEATHER_API_KEY + "&unit=" + Settings.WEATHER_UNIT_OUTPUT);

        JSONTokener jsonTokener = new JSONTokener(url.openStream());
        JSONObject jsonObject = new JSONObject(jsonTokener);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        System.out.println(gson.toJson(jsonObject));

    }


}

package hk.location;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import hk.settings.Settings;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.net.URL;

public class UserLocation {

    public static void main(String[] args) {
        UserLocation userLocation = new UserLocation(Settings.DEFAULT_LATITUDE, Settings.DEFAULT_LONGITUDE);
        userLocation.fetch();
    }

    private final double latitude;
    private final double longitude;

    private URL url;

    private JSONTokener jsonTokener;
    private JSONObject jsonObject;

    public UserLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void fetch() {

        try {
            url = new URL("https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=" + Settings.WEATHER_API_KEY + "&units=" + Settings.WEATHER_UNIT_OUTPUT);
            jsonTokener = new JSONTokener(url.openStream());
            jsonObject = new JSONObject(jsonTokener);
        } catch (IOException e) {
            System.err.println("ERROR! Could not fetch data from OpenWeatherAPI");
            e.printStackTrace();
        }

        if (Settings.DEBUG) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            System.out.println(gson.toJson(jsonObject));
        }

    }


}

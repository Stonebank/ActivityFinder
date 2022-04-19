package hk.utility.loader;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import hk.activity.Activity;
import hk.activity.coordinate.Coordinate;
import hk.activity.weather.WeatherType;
import hk.utility.JSONLoader;

public class ActivityLoader {

    private final String path;

    public ActivityLoader(String path) {
        this.path = path;
    }

    public void init() {

        JSONLoader jsonLoader = new JSONLoader() {
            @Override
            public String json_file() {
                return path;
            }

            @Override
            public void load(JsonObject reader, Gson gson) {

                String name = reader.get("name").getAsString();

                double latitude = reader.get("coordinate").getAsJsonObject().get("latitude").getAsDouble();
                double longitude = reader.get("coordinate").getAsJsonObject().get("longitude").getAsDouble();

                Activity.activities.add(new Activity(name, null, new Coordinate(latitude, longitude), WeatherType.PLACEHOLDER));

            }
        };
        jsonLoader.load();

        System.out.println(Activity.activities.size() + " activities parsed");

    }

}

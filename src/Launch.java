import com.google.common.base.Stopwatch;
import hk.activity.Activity;
import hk.activity.category.Category;
import hk.activity.category.container.BestDistance;
import hk.activity.category.container.BestWeather;
import hk.location.UserLocation;
import hk.settings.Settings;
import hk.utility.Geolocation;
import hk.utility.JSONConverter;
import hk.utility.loader.ActivityLoader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;

public class Launch {

    public static void main(String[] args) throws IOException {

        JSONConverter jsonConverter = new JSONConverter(new File("./activity.txt"), Settings.ACTIVITY_JSON_PATH);
        jsonConverter.convert();

        Stopwatch stopwatch = Stopwatch.createStarted();

        Geolocation geolocation = new Geolocation();
        geolocation.loadDB();

        System.out.println(geolocation);

        double lat = Settings.DEBUG ? Settings.DEFAULT_LATITUDE : geolocation.getCoordinate()[0];
        double lon = Settings.DEBUG ? Settings.DEFAULT_LONGITUDE : geolocation.getCoordinate()[1];

        UserLocation userLocation = new UserLocation(lat, lon);
        userLocation.fetch();
        userLocation.parse();

        System.out.println("Current weather: " + userLocation.getWeatherType());
        System.out.println("Current temperature: " + userLocation.getCurrent_temperature() + " °C");
        System.out.println("Maximum and minimum temperature: " + userLocation.getMaximum_temperature() + " °C / " + userLocation.getMinimum_temperature() + " °C");
        System.out.println("Currently feels like: " + userLocation.getFeels_like() + " °C");

        ActivityLoader activityLoader = new ActivityLoader(Settings.ACTIVITY_JSON_PATH);
        activityLoader.init();

        ArrayList<Category> categories = new ArrayList<>();
        categories.add(new BestDistance());
        categories.add(new BestWeather());

        for (Category category : categories) {
            category.compare(userLocation);
            category.addPoints();

            if (category.bestCandidate() != null)
                System.out.println("Best candidate for category '" + category.getClass().getSimpleName() + "' " + category.bestCandidate().getName());

        }

        System.out.println();

        Activity.activities.sort(Comparator.comparing(Activity::getPoints).reversed());

        System.out.println("Displaying list sorted by points!");
        for (Activity activity : Activity.activities)
            System.out.println(activity + ", distance: " + Math.round(userLocation.calculateDistance(activity.getCoordinate().getLatitude(), activity.getCoordinate().getLongitude())) + " km");

        System.out.println("Application finished in " + stopwatch.stop().elapsed(TimeUnit.MILLISECONDS) + " ms");

    }

}

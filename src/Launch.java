import com.google.common.base.Stopwatch;
import hk.activity.Activity;
import hk.activity.category.Category;
import hk.activity.category.container.BestDistance;
import hk.activity.category.container.BestWeather;
import hk.location.UserLocation;
import hk.settings.Settings;
import hk.utility.Geolocation;
import hk.utility.loader.ActivityLoader;

import java.util.concurrent.TimeUnit;

public class Launch {

    public static void main(String[] args) {

        Stopwatch stopwatch = Stopwatch.createStarted();

        Geolocation geolocation = new Geolocation();
        geolocation.loadDB();

        System.out.println("You're expected to be in " + geolocation.getCity()+ ", " + geolocation.getCountry());

        double lat = Settings.DEBUG ? Settings.DEFAULT_LATITUDE : geolocation.getCoordinate()[0];
        double lon = Settings.DEBUG ? Settings.DEFAULT_LONGITUDE : geolocation.getCoordinate()[1];

        UserLocation userLocation = new UserLocation(lon, lat);
        userLocation.fetch();
        userLocation.parse();

        System.out.println("Current weather: " + userLocation.getWeatherType());

        ActivityLoader activityLoader = new ActivityLoader(Settings.ACTIVITY_JSON_PATH);
        activityLoader.init();

        Category bestDistance = new BestDistance();
        bestDistance.compare(userLocation);
        bestDistance.addPoints();
        System.out.println();

        Category bestWeather = new BestWeather();
        bestWeather.compare(userLocation);

        for (Activity activity : Activity.activities)
            System.out.println(activity + ", distance: " + userLocation.calculateDistance(activity.getCoordinate().getLongitude(), activity.getCoordinate().getLatitude()) + " km");

        System.out.println("Application finished in " + stopwatch.stop().elapsed(TimeUnit.MILLISECONDS) + " ms");

    }

}

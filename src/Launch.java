import hk.activity.Activity;
import hk.activity.category.Category;
import hk.activity.category.container.BestDistance;
import hk.activity.category.container.BestWeather;
import hk.location.UserLocation;
import hk.settings.Settings;
import hk.utility.loader.ActivityLoader;

public class Launch {

    public static void main(String[] args) {

        UserLocation userLocation = new UserLocation(Settings.DEFAULT_LATITUDE, Settings.DEFAULT_LONGITUDE);
        userLocation.fetch();
        userLocation.parse();

        System.out.println("Current weather: " + userLocation.getWeatherType());

        ActivityLoader activityLoader = new ActivityLoader(Settings.ACTIVITY_JSON_PATH);
        activityLoader.init();

        Category bestDistance = new BestDistance();
        bestDistance.compare();
        bestDistance.addPoints();
        System.out.println("Best candidate: " + bestDistance.bestCandidate());
        System.out.println();

        Category bestWeather = new BestWeather();
        bestWeather.compare();

        for (Activity activity : Activity.activities)
            System.out.println(activity + ", distance: " + userLocation.calculateDistance(activity.getCoordinate().getLatitude(), activity.getCoordinate().getLongitude()) + " km");

    }

}

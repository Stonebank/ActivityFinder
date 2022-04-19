import hk.activity.Activity;
import hk.location.UserLocation;
import hk.settings.Settings;
import hk.utility.loader.ActivityLoader;

public class Launch {

    public static void main(String[] args) {

        UserLocation userLocation = new UserLocation(Settings.DEFAULT_LATITUDE, Settings.DEFAULT_LONGITUDE);
        userLocation.fetch();
        userLocation.parse();

        ActivityLoader activityLoader = new ActivityLoader(Settings.ACTIVITY_JSON_PATH);
        activityLoader.init();

        for (Activity activity : Activity.activities)
            System.out.println(activity + ", distance: " + userLocation.calculateDistance(activity.getCoordinate().getLatitude(), activity.getCoordinate().getLongitude()) + " km");

    }

}

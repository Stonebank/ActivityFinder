import hk.activity.Activity;
import hk.activity.category.Category;
import hk.activity.category.container.BestDistance;
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

        Category bestDistance = new BestDistance();
        bestDistance.compare();
        bestDistance.addPoints();
        System.out.println("Best candidate: " + bestDistance.bestCandidate());
        System.out.println();

        for (Activity activity : Activity.activities)
            System.out.println(activity + ", distance: " + userLocation.calculateDistance(activity.getCoordinate().getLatitude(), activity.getCoordinate().getLongitude()) + " km");

    }

}

package hk.activity.category.container;

import hk.activity.Activity;
import hk.activity.category.Category;
import hk.location.UserLocation;
import hk.settings.Settings;

public class BestDistance extends Category {

    private final Activity[] activities = new Activity[Activity.activities.size()];
    
    @Override
    public void compare() {

        for (int i = 0; i < activities.length; i++)
            activities[i] = Activity.activities.get(i);

        UserLocation userLocation = new UserLocation(Settings.DEFAULT_LATITUDE, Settings.DEFAULT_LONGITUDE);

        for (int i = 0; i < activities.length; i++) {
            double distance_i = userLocation.calculateDistance(activities[i].getCoordinate().getLatitude(), activities[i].getCoordinate().getLongitude());
            for (int j = 0; j < activities.length; j++) {
                double distance_j = userLocation.calculateDistance(activities[j].getCoordinate().getLatitude(), activities[j].getCoordinate().getLongitude());

                if (distance_i == distance_j)
                    continue;

                if (distance_i > distance_j) {
                    Activity temp = activities[i];
                    activities[i] = activities[j];
                    activities[j] = temp;
                }

            }
        }

    }

    @Override
    public void addPoints() {
        for (int i = 0; i < activities.length; i++) {
            int points = 1000 / (i + 1);
            Activity activity = Activity.getActivity(activities[i].getName());
            if (activity == null) {
                System.err.println("ERROR! Something went wrong at index: " + i + " whilst adding points.");
                break;
            }
            activity.addPoints(points);
        }
    }

    @Override
    public Activity bestCandidate() {
        return activities[0];
    }

}

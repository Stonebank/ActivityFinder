package hk.activity.category.container;

import hk.activity.Activity;
import hk.activity.category.Category;
import hk.location.UserLocation;

public class BestDistance extends Category {

    private final Activity[] activities = new Activity[Activity.activities.size()];
    
    @Override
    public void compare(UserLocation userLocation) {

        for (int i = 0; i < activities.length; i++)
            activities[i] = Activity.activities.get(i);

        for (int i = 0; i < activities.length; i++) {
            double distance_i = userLocation.calculateDistance(activities[i].getCoordinate().getLongitude(), activities[i].getCoordinate().getLatitude());
            for (int j = 0; j < activities.length; j++) {
                double distance_j = userLocation.calculateDistance(activities[j].getCoordinate().getLongitude(), activities[j].getCoordinate().getLatitude());

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

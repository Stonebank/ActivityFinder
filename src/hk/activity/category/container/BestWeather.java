package hk.activity.category.container;

import hk.activity.Activity;
import hk.activity.category.Category;
import hk.location.UserLocation;
import hk.settings.Settings;

public class BestWeather extends Category {

    @Override
    public void compare() {

        UserLocation userLocation = new UserLocation(Settings.DEFAULT_LATITUDE, Settings.DEFAULT_LONGITUDE);
        userLocation.fetch();
        userLocation.parse();

        for (Activity activity : Activity.activities) {
            if (activity.getBestWeather() == userLocation.getWeatherType())
                activity.addPoints(100);
            else
                activity.removePoints(200);
        }
    }

    @Override
    public void addPoints() {

    }

    @Override
    public Activity bestCandidate() {
        return null;
    }

}
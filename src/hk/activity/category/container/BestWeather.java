package hk.activity.category.container;

import hk.activity.Activity;
import hk.activity.category.Category;
import hk.activity.weather.WeatherType;
import hk.location.UserLocation;

public class BestWeather extends Category {

    @Override
    public void compare(UserLocation userLocation) {

        userLocation.fetch();
        userLocation.parse();

        for (Activity activity : Activity.activities) {
            if (activity.getBestWeather() == userLocation.getWeatherType() || activity.getBestWeather() == WeatherType.NONE)
                activity.addPoints(100);
            if (activity.getWorstWeather() == userLocation.getWeatherType())
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

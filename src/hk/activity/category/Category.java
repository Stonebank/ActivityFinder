package hk.activity.category;

import hk.activity.Activity;
import hk.location.UserLocation;

public abstract class Category {

    public abstract void compare(UserLocation userLocation);

    public abstract void addPoints();

    public abstract Activity bestCandidate();

}

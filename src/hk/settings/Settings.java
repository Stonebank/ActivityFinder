package hk.settings;

import java.io.File;

public class Settings {

    public static final boolean DEBUG = false;

    public static final File GEO_DATABASE = new File("./resources/lib/geo-lib/GeoLite2-City.mmdb");

    public static final String WEATHER_API_KEY = "a0a4ba68146a9c6451e13417ea962641";
    public static final String WEATHER_UNIT_OUTPUT = "metric";

    public static final double DEFAULT_LATITUDE = 55.871984303779705;
    public static final double DEFAULT_LONGITUDE = 12.357251514307459;

    public static final String ACTIVITY_JSON_PATH = "./resources/json/activity.json";

}

package hk.utility;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import hk.settings.Settings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.util.Arrays;

public class Geolocation {

    private String ip;
    private CityResponse response;

    public Geolocation() {
        System.out.println("Grabbing IP Address...");

        try {
            URL url = new URL("https://ip-fast.com/api/ip/");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
            ip = bufferedReader.readLine();
        } catch (IOException e) {
            System.err.println("ERROR! Could not parse IP Address");
            e.printStackTrace();
        }

        System.out.println("Done!");

    }

    public void loadDB() {
        System.out.println("Loading database: " + Settings.GEO_DATABASE);
        try {
            DatabaseReader databaseReader = new DatabaseReader.Builder(Settings.GEO_DATABASE).build();
            response = databaseReader.city(InetAddress.getByName(ip));
        } catch (IOException | GeoIp2Exception e) {
            System.err.println("ERROR! Could not load " + Settings.GEO_DATABASE);
            e.printStackTrace();
        }
        System.out.println("Done!");
    }

    public String getCountry() {
        return response.getCountry().getName();
    }

    public String getZipCode() {
        return response.getPostal().getCode();
    }

    public String getCity() {
        return response.getCity().getName();
    }

    public double[] getCoordinate() {
        return new double[] { response.getLocation().getLatitude(), response.getLocation().getLongitude() };
    }

    public String getRadius() {
        return "accuracy radius=" + response.getLocation().getAccuracyRadius() + " km";
    }

    @Override
    public String toString() {
        return "Expected to be in: [city=" + getCity() + ", country=" + getCountry() + ", postal code: " + getZipCode() + ", coords=" + Arrays.toString(getCoordinate()) + "]\n" +
                getRadius();
    }

}

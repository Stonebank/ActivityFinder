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
    private DatabaseReader databaseReader;

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
            databaseReader = new DatabaseReader.Builder(Settings.GEO_DATABASE).build();
        } catch (IOException e) {
            System.err.println("ERROR! Could not load " + Settings.GEO_DATABASE);
            e.printStackTrace();
        }
        System.out.println("Done!");
    }

    public String getCountry() {
        try {
            CityResponse response = databaseReader.city(InetAddress.getByName(ip));
            return response.getCountry().getName();
        } catch (IOException | GeoIp2Exception e) {
            System.err.println("ERROR! Something went wrong getting response from Geoip2");
            e.printStackTrace();
            return null;
        }
    }

    public String getZipCode() {
        try {
            CityResponse response = databaseReader.city(InetAddress.getByName(ip));
            return response.getPostal().getCode();
        } catch (IOException | GeoIp2Exception e) {
            System.err.println("ERROR! Something went wrong getting response from Geoip2");
            e.printStackTrace();
            return null;
        }
    }

    public String getCity() {
        try {
            CityResponse response = databaseReader.city(InetAddress.getByName(ip));
            return response.getCity().getName();
        } catch (IOException | GeoIp2Exception e) {
            System.err.println("ERROR! Something went wrong getting response from Geoip2");
            e.printStackTrace();
            return null;
        }
    }

    public double[] getCoordinate() {
        try {
            CityResponse response = databaseReader.city(InetAddress.getByName(ip));
            return new double[] { response.getLocation().getLatitude(), response.getLocation().getLongitude() };
        } catch (IOException | GeoIp2Exception e) {
            System.err.println("ERROR! Something went wrong getting response from Geoip2");
            e.printStackTrace();
            return null;
        }
    }

    public String getRadius() {
        try {
            CityResponse response = databaseReader.city(InetAddress.getByName(ip));
            return "accuracy radius=" + response.getLocation().getAccuracyRadius() + " km";
        } catch (IOException | GeoIp2Exception e) {
            System.err.println("ERROR! Something went wrong getting response from Geoip2");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String toString() {
        return "Expected to be in: [city=" + getCity() + ", country=" + getCountry() + ", postal code: " + getZipCode() + ", coords=" + Arrays.toString(getCoordinate()) + "]\n" +
                getRadius();
    }

}

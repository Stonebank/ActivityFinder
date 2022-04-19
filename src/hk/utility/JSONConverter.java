package hk.utility;

import com.google.common.base.Stopwatch;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import hk.activity.Activity;
import hk.activity.coordinate.Coordinate;
import hk.activity.weather.WeatherType;
import hk.settings.Settings;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class JSONConverter {

    public static void main(String[] args) throws IOException {
        JSONConverter jsonConvert = new JSONConverter(new File("./activity.txt"), Settings.ACTIVITY_JSON_PATH);
        jsonConvert.convert();
    }

    private final File textFile;
    private final String destination;

    public JSONConverter(File file, String destination) throws FileNotFoundException {
        this.textFile = file;
        this.destination = destination;
        if (!textFile.exists())
            throw new FileNotFoundException(textFile.getPath() + " was not found");

        if (new File(destination).delete())
            System.out.println("Deleted " + destination);

    }

    public void convert() throws IOException {

        Stopwatch stopwatch = Stopwatch.createStarted();

        System.out.println("Converting " + textFile.getName() + "...");

        ArrayList<Activity> activities = new ArrayList<>();

        int index = 0;

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(destination));

        Scanner scanner = new Scanner(textFile);
        while (scanner.hasNext()) {

            String[] splitter = scanner.nextLine().replaceAll(" ", "").split("-");

            String name = splitter[0];
            String city = splitter[1];

            double latitude = Double.parseDouble(splitter[2]);
            double longitude = Double.parseDouble(splitter[3]);

            WeatherType bestWeather = WeatherType.valueOf(splitter[4]);
            WeatherType worstWeather = WeatherType.valueOf(splitter[5]);

            activities.add(new Activity(name, city, new Coordinate(latitude, longitude), bestWeather, worstWeather));
            bufferedWriter.append(String.valueOf(activities.get(index)));

            index++;

        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        gson.toJson(activities, bufferedWriter);

        bufferedWriter.flush();
        bufferedWriter.close();

        System.out.println("Converted " + textFile + " to " + destination + ". [Objects: " + activities.size() + " in " + stopwatch.stop().elapsed(TimeUnit.MILLISECONDS) + " ms]");

    }

}

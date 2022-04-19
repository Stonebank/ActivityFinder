package hk.utility;

import com.google.gson.*;

import java.io.FileReader;
import java.nio.file.Paths;

public abstract class JSONLoader {

    public static void main(String[] args) {
        new JSONLoader() {
            @Override
            public String json_file() {
                return "./resources/json/activity.json";
            }

            @Override
            public void load(JsonObject reader, Gson gson) {
                String name = reader.get("name").getAsString();
                System.out.println(name);
            }
        };
    }

    public abstract String json_file();

    public abstract void load(JsonObject reader, Gson gson);

    public JSONLoader load() {
        try (FileReader reader = new FileReader(Paths.get(json_file()).toFile())) {

            JsonArray array = (JsonArray) JsonParser.parseReader(reader);
            Gson builder = new GsonBuilder().create();

            for (int i = 0; i < array.size(); i++) {
                JsonObject jsonObject = (JsonObject) array.get(i);
                load(jsonObject, builder);
            }

        } catch (Exception e) {
            System.err.println("Fault parsing json file: " + json_file());
            e.printStackTrace();
        }
        return this;
    }

}

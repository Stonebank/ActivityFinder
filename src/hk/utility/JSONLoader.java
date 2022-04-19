package hk.utility;

import com.google.gson.*;

import java.io.FileReader;
import java.nio.file.Paths;

public abstract class JSONLoader {

    public abstract String json_file();

    public abstract void load(JsonObject reader, Gson gson);

    public void load() {
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
    }

}

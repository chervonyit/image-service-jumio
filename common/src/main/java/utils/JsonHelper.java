package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.Image;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JsonHelper {

    public static Gson writeToJson(Image image, String filePath) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter fw = new FileWriter(filePath)) {
            gson.toJson(image, fw);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gson;
    }

    public static Object readFromJson(String filePath){
            Gson gson = new Gson();
            Image imageObject = null;
            try {
                imageObject = gson.fromJson(new FileReader(filePath), Image.class);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return imageObject;
    }
}

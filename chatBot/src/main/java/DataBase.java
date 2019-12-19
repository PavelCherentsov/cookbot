import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataBase {
    private static String path = "DB.txt";

    public static void setInDB(Food food) {
        Gson master = new Gson();
        String jsonFood = master.toJson(food) + "\n";
        try {
            Files.write(Paths.get(path),
                    jsonFood.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.fillInStackTrace();
        }
    }

    public static Food searchInDB(String name) throws IOException {
        Gson master = new Gson();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path),
                    StandardCharsets.UTF_8));
            String jsonFood;
            while ((jsonFood = reader.readLine()) != null) {
                Pattern pattern = Pattern.compile("\"name\":\".+?\"", Pattern.MULTILINE);
                Matcher matcher = pattern.matcher(jsonFood);
                if (!(matcher.find())) {
                    continue;
                }
                String sName = jsonFood.substring(matcher.start() + 8, matcher.end() - 1);
                if (name.equals(sName)) {
                    return master.fromJson(jsonFood, Food.class);
                }
            }
        } catch (IOException e) {
            File file = new File("DB.txt");
            file.createNewFile();
        }
        return null;
    }
}
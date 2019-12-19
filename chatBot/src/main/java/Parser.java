import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    public static String getDescriptionFromInternet(String food, Locale locale){
        ResourceBundle res = ResourceBundle.getBundle("ProgramResources", locale);
        StringBuilder description = new StringBuilder();
        String page = getPageWithDescription(food, locale);
        Pattern pattern = Pattern.compile("<p>.*?<b>.+?</p>");
        Matcher matcher = pattern.matcher(page);
        if (!(matcher.find())){
            return res.getString("nfInf");
        }
        description.append(page.substring(matcher.start(), matcher.end())
                .replaceAll("<.+?>", "")
                .replaceAll("&#91.*?#93;", "")
                .replaceAll(".#.*?;", "")
                .replaceAll("\\?+?", ""));
        description.append("\n");
        description.append(res.getString("findR"));
        food = URLEncoder.encode(food, StandardCharsets.UTF_8);
        String recURL = res.getString("url");
        description.append(recURL).append(food.replaceAll(" ", "%20"));
        return description.toString();
    }

    private static String getPageWithDescription(String food, Locale locale) {
        URL url;
        String page = "";
        ResourceBundle res = ResourceBundle.getBundle("ProgramResources", locale);
        try {
            food = URLEncoder.encode(food, "utf-8");
            food = food.replace('+', '_');
            String wiki = res.getString("wiki");
            url = new URL(wiki + food.replace(' ', '_'));
            URLConnection connection = url.openConnection();
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder pageBuffer = new StringBuilder();
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                pageBuffer.append(inputLine);
            }
            br.close();
            page = pageBuffer.toString();
        } catch (IOException e) {
            return "";
        }
        return page;
    }
}

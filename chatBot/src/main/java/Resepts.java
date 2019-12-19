import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Resepts {
    public static Food thisFood;
    public static String init(String name) {
        try {
            String page = getPage("https://www.edimdoma.ru/retsepty?" +
                    "with_ingredient=&without_ingredient=&user_ids=&page=1&field=&direction=&query="+name.replace(' ', '+'));
            ArrayList<String> resepts = getParseElements(page, "<article class=\"card\"", "</article>");
            ArrayList<Food> foods = getFood(resepts);
            for (Food food : foods) {
                String page_food = getPage(food.description);
                ArrayList<String> engred = getParseElements(page_food, "data-intredient-title=\"", " data-unit-id");
                String p = "class=\"definition-list-table__td definition-list-table__td_value\"";
                ArrayList<String> sums = getParseElements(page_food, p, "</td>");
                for (int i = 0; i < engred.size(); i++) {
                    String e = engred.get(0);
                    engred.add(e.substring(23, e.length() - 1) + " " + sums.get(i + 3).substring(p.length() + 1)
                            .replace("&#189;", "1/2"));
                    engred.remove(0);
                }
                food.engreds = engred;
                ArrayList<String> steps = getParseElements(page_food, "plain-text recipe_step_text\">", "</div>");
                for (int i = 0; i < steps.size(); i++) {
                    String e = steps.get(0);
                    e = e.substring(29);
                    e = e.replaceAll("&quot;", "\"");
                    e = e.replaceAll("&ndash;", "-");
                    e = e.replace("&deg", " градусов Цельсия ");
                    e = e.replace("&laquo;", " ");
                    e = e.replace("&raquo;", " ");
                    steps.add(e);
                    steps.remove(0);
                }
                food.steps = steps;
            }
            try {
                thisFood = foods.get(0);
            }
            catch (Exception e) {
                return "К сожалению, я не могу найти рецепт " + name;
            }

            return thisFood.name;
        } catch (IOException e) {
            return "";
        }
    }

    public static String getPage(String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    public static ArrayList<String> getParseElements(String page, String pattent_start, String pattern_end) {
        ArrayList<String> resepts = new ArrayList<>();
        int i_start = 0;
        int i_finish = 0;
        while (true) {
            i_start = page.indexOf(pattent_start);
            if (i_start == -1)
                break;
            page = page.substring(i_start);
            i_finish = page.indexOf(pattern_end);
            resepts.add(page.substring(0, i_finish));
            page = page.substring(i_finish + 1);
        }
        return resepts;
    }

    public static ArrayList<Food> getFood(ArrayList<String> resepts) {
        ArrayList<Food> foods = new ArrayList<>();
        for (String r : resepts) {
            int i_start_name = r.indexOf("title=");
            int i_finish_name = r.indexOf("src=\"");
            String name = r.substring(i_start_name + 7, i_finish_name - 2);
            int i_start_url = r.indexOf("href=\"");
            r = r.substring(i_start_url + 6);
            int i_finish_url = r.indexOf("\">");
            String url = r.substring(0, i_finish_url);
            foods.add(new Food(name,
                    "https://www.edimdoma.ru/" + url));
        }
        return foods;
    }
}

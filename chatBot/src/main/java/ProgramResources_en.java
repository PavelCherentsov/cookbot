import java.io.IOException;
import java.util.HashMap;
import java.util.ListResourceBundle;

public class ProgramResources_en extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        HashMap holidayFood = new HashMap<>();
        try {
            holidayFood.put("new year", new Food("Olivier salad"));
            holidayFood.put("birthday", new Food("Mashed potato"));
            holidayFood.put("valentine's day", new Food("Spaghetti"));
            holidayFood.put("1st september", new Food("Cake"));
            holidayFood.put("christmas", new Food("Pelmeni"));
            holidayFood.put("thanksgiving day", new Food("Turkey as food"));
            holidayFood.put("maslenitsa", new Food("Pancake"));
            holidayFood.put("1st may", new Food("Pie"));
            holidayFood.put("9th may", new Food("Porridge"));
            holidayFood.put("1st april", new Food("Pie"));
            holidayFood.put("russia day", new Food("Borscht"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Object[][] {{"hashM", holidayFood},
                {"nfInf", "information about this food is not found"},
                {"findR", "you can find recipes here: "},
                {"url", "https://recipebook.io/recipes?key="},
                {"wiki", "https://en.wikipedia.org/wiki/"},
                {"avVar", "Available variants:\n"},
                {"sum", "summary "},
                {"var", " variants"}};
    }
}

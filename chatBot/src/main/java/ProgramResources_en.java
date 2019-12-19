import java.io.IOException;
import java.util.*;

public class ProgramResources_en extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        HashMap holidayFood = new HashMap<>();
        try {
            Food pie = new Food("Pie");
            Food potato = new Food("Mashed potato");
            Food spaghetti = new Food("Spaghetti");
            Food cake = new Food("Cake");
            Food pelmeni = new Food("Pelmeni");
            Food pancake = new Food("Pancake");
            Food olivier = new Food("Olivier salad");
            Food porridge = new Food("Porridge");
            Food borscht = new Food("Borscht");
            Food turkey = new Food("Turkey as food");
            Food fish = new Food("Fish as food");
            Food mandarin = new Food("Mandarin orange");
            Food pork = new Food("Pork");
            Food cobb = new Food("Cobb salad");
            holidayFood.put("new year", new ArrayList<>(Arrays.asList(
                    olivier, fish, mandarin)));
            holidayFood.put("birthday", new ArrayList<>(Arrays.asList(
                    potato, cobb, fish)));
            holidayFood.put("valentine's day", new ArrayList<>(Arrays.asList(
                    spaghetti, pork, fish)));
            holidayFood.put("1st september", new ArrayList<>(Arrays.asList(
                    pancake, pork, fish)));
            holidayFood.put("christmas", new ArrayList<>(Arrays.asList(
                    pelmeni, fish, cake)));
            holidayFood.put("thanksgiving day", new ArrayList<>(Arrays.asList(
                    turkey, fish, mandarin)));
            holidayFood.put("maslenitsa", new ArrayList<>(Collections.singletonList(
                    pancake)));
            holidayFood.put("1st may", new ArrayList<>(Arrays.asList(
                    pie, fish, pork)));
            holidayFood.put("9th may", new ArrayList<>(Collections.singletonList(
                    porridge)));
            holidayFood.put("1st april", new ArrayList<>(Arrays.asList(
                    pie, pork, fish)));
            holidayFood.put("russia day", new ArrayList<>(Arrays.asList(
                    borscht, pancake, pork)));
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

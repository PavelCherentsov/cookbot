import java.io.IOException;
import java.util.*;

public class ProgramResources_ru extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        HashMap holidayFood = new HashMap<>();
        try{
            Food pie = new Food("Пирог");
            Food potato = new Food("Картофельное пюре");
            Food spaghetti = new Food("Спагетти");
            Food cake = new Food("Торт");
            Food pelmeni = new Food("Пельмени");
            Food pancake = new Food("Блины");
            Food olivier = new Food("Оливье(салат)");
            Food porridge = new Food("Пудинг");
            Food borscht = new Food("Борщ");
            Food turkey = new Food("Индюшатина");
            Food fish = new Food("Рыба (пища)");
            Food mandarin = new Food("Мандарин");
            Food pork = new Food("Свинина");
            Food cobb = new Food("Кобб (салат)");
            holidayFood.put("новый год", new ArrayList<>(Arrays.asList(
                    olivier, fish, mandarin)));
            holidayFood.put("день рождения", new ArrayList<>(Arrays.asList(
                    potato, cobb, fish)));
            holidayFood.put("день святого Валентина", new ArrayList<>(Arrays.asList(
                    spaghetti, pork, fish)));
            holidayFood.put("первое сентября", new ArrayList<>(Arrays.asList(
                    pancake, pork, fish)));
            holidayFood.put("рождество", new ArrayList<>(Arrays.asList(
                    pelmeni, fish, cake)));
            holidayFood.put("день благодарения", new ArrayList<>(Arrays.asList(
                    turkey, fish, mandarin)));
            holidayFood.put("масленица", new ArrayList<>(Collections.singletonList(
                    pancake)));
            holidayFood.put("первое мая", new ArrayList<>(Arrays.asList(
                    pie, fish, pork)));
            holidayFood.put("девятое мая", new ArrayList<>(Collections.singletonList(
                    porridge)));
            holidayFood.put("первое апреля", new ArrayList<>(Arrays.asList(
                    pie, pork, fish)));
            holidayFood.put("день России", new ArrayList<Food>(Arrays.asList(
                    borscht, pancake, pork)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Object[][] {{"hashM", holidayFood},
                {"nfInf", "Информация не найдена"},
                {"findR", "вы можете найти рецепты здесь: "},
                {"url", "https://eda.ru/recipesearch?q="},
                {"wiki", "https://ru.wikipedia.org/wiki/"},
                {"avVar", "Возможные варианты:\n"},
                {"sum", "итого "},
                {"var", " вариантов"}};
    }
}
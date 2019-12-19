import java.io.IOException;
import java.util.HashMap;
import java.util.ListResourceBundle;

public class ProgramResources_ru extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        HashMap<String, Food> holidayFood = new HashMap<String, Food>();
        try{
            holidayFood.put("новый год", new Food("Оливье (салат)"));
            holidayFood.put("день рождения", new Food("Картофельное пюре"));
            holidayFood.put("день святого Валентина", new Food("Спагетти"));
            holidayFood.put("первое сентября", new Food("Торт"));
            holidayFood.put("рождество", new Food("пельмени"));
            holidayFood.put("день благодарения", new Food("Индюшатина"));
            holidayFood.put("масленица", new Food("Блины"));
            holidayFood.put("первое мая", new Food("Пирог"));
            holidayFood.put("девятое мая", new Food("Пудинг"));
            holidayFood.put("первое апреля", new Food("Пирог"));
            holidayFood.put("день России", new Food("Борщ"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Object[][] {{"hashM", holidayFood},
                {"nfInf", "Информация не найдена"},
                {"findR", "вы можете найти рецепты здесь: "},
                {"url", "https://www.edimdoma.ru/retsepty?\" +\n" +
                        "                \"with_ingredient=&without_ingredient=&user_ids=&page=1&field=&direction=&query="},
                {"wiki", "https://ru.wikipedia.org/wiki/"},
                {"avVar", "Возможные варианты:\n"},
                {"sum", "итого "},
                {"var", " вариантов"}};
    }
}

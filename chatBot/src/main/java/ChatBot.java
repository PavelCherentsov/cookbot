import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.*;

public class ChatBot {
    private String name;
    private String info = "Developed by Kirill and Nikolay\nversion: 0.2b";
    private boolean alive;
    //private static HashMap<String, Food> holidayFood;
    //private Languages language = Languages.ENGLISH;
    private Locale locale = new Locale("en");
    //private DataBase dataBase;

    public HashMap<String, Command> commands = new HashMap<>();

    /*static  {
        try{
            holidayFood = new HashMap<>();
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
            //holidayFood.put("test", new Food("non123"));
        }catch (IOException e) {
            System.exit(1);
        }
    }*/
    /*static {
        commands = new HashMap<>();
        commands.put("echo", Command("repeat your text", echo);
        commands.put("getName", getName);
        commands.put("help", help);
        commands.put("holiday", getHolidayFood);
    }*/

    public String echo(String txt) {
        return txt;
    }

    public String getName(String txt) {
        return name;
    }

    public String getInfo(String txt) {
        return info;
    }

    public String help(String txt) {
        StringBuilder result = new StringBuilder();
        for (String key : commands.keySet()) {
            result.append(key);
            result.append("- ");
            result.append(commands.get(key).description);
            result.append("\n");
        }
        return result.toString();
    }

    public String getHolidayFood(String arg) { //also we can do Livenstein distance support
        StringBuilder str = new StringBuilder();
        ResourceBundle res = ResourceBundle.getBundle("ProgramResources", this.locale);
        HashMap<String, ArrayList<Food>> holidayFood =
                (HashMap<String, ArrayList<Food>>)res.getObject("hashM");
        String targ = arg.split(" /")[0];
        System.out.println(targ);
        if (holidayFood.get(targ) == null) {
            str.append(res.getString("avVar"));
            int counter = 0;
            for (String holiday : holidayFood.keySet()) {
                if (holiday.contains(arg)) {
                    str.append(holiday);
                    str.append("\n");
                    counter += 1;
                }
            }
            str.append(res.getString("sum")).append(counter).append(res.getString("var"));
        } else {
            try {
                Class.forName("org.sqlite.JDBC");
                Connection con = DriverManager.getConnection(
                        "jdbc:sqlite:food.db");
                ArrayList<String> args = new ArrayList<>(
                        Arrays.asList(arg.split(" /")));
                ArrayList<Food> foods = holidayFood.get(args.get(0));
                args.remove(0);
                HashMap<String, Boolean> ings = new HashMap<>();
                for (var argum : args) {
                    name = "";
                    if (locale.equals(new Locale("ru")))
                        name = changeArg(argum.substring(1));
                    else
                        name = argum.substring(1);
                    if(name.equals(""))
                        continue;
                    if (argum.substring(0, 1).equals("+"))
                        ings.put(name, Boolean.TRUE);
                    else
                        ings.put(name, Boolean.FALSE);
                }
                for (var food : foods) {
                    String name = food.name;
                    String sql =  "SELECT * FROM food WHERE ru_name = '" + name + "' OR en_name = '" + name + "'";
                    Statement stay = con.createStatement();
                    ResultSet result = stay.executeQuery(sql);
                    Boolean flag = Boolean.TRUE;
                    while(result.next()) {
                        for (var ned : ings.keySet()) {
                            if (!ings.get(ned).equals(Boolean.valueOf(result.getString(ned))))
                                flag = Boolean.FALSE;
                        }
                    }
                    if (flag) {
                        str.append(name);
                        str.append('\n');
                        str.append(getDescription(food));
                        str.append('\n');
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return str.toString();
    }

    private String changeArg(String arg){
        if (arg.equals("мясо"))
            return "meat";
        if (arg.equals("рыба"))
            return "fish";
        if (arg.equals("молоко"))
            return "milk";
        if (arg.equals("вегетарианский"))
            return "vegetarian";
        return "";
    }

    public String changeLanguage(String arg){
        this.locale = (this.locale.equals(new Locale("en")))
                ? new Locale("ru")
                : new Locale("en");
        return this.locale.toString();
    }

    public boolean isAlive() {
        return this.alive;
    }

    public synchronized String getDescription(Food food){ // if we have description then ok
        // if we don't have then find it in wiki
        if (food.description.equals("")){
            food.description = Parser.getDescriptionFromInternet(food.name, this.locale);
            DataBase.setInDB(food);
        }
        return food.description;
    }

    ChatBot(String name) {
        this.name = name;
        this.alive = true;
        commands.put("echo", new Command("repeat your text", this::echo));
        commands.put("name", new Command("get name of bot", this::getName));
        commands.put("info", new Command("get information about bot", this::getInfo));
        commands.put("help", new Command("get information about command", this::help));
        commands.put("holiday", new Command("gives you information about food for holidays", this::getHolidayFood));
        commands.put("changelanguage", new Command("change language", this::changeLanguage));
    }
}
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class ChatBot {
    private String name;
    private String info = "Developed by Kirill and Nikolay\nversion: 0.2b";
    private boolean alive;
    private Locale locale = new Locale("en");
    public GregorianCalendar thisdate;
    public boolean isRunTimer;
    private int i_step = 0;

    public HashMap<String, Command> commands = new HashMap<>();

    public String echo(String txt) {
        return txt;
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

    public String getResept(String txt) {
        i_step = 0;
        return Resepts.init(txt);
    }

    public String next(String txt) {
        try {
            String res = Resepts.thisFood.steps.get(i_step);
            i_step++;
            return res;
        } catch (Exception e){
            return "End recipe";
        }
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
        HashMap<String, Food> holidayFood = (HashMap<String, Food>) res.getObject("hashM");
        if (holidayFood.get(arg) == null) {
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
            str.append(holidayFood.get(arg).name);
            str.append('\n');
            str.append(getDescription(holidayFood.get(arg)));
        }
        return str.toString();
    }

    public String changeLanguage(String arg) {
        this.locale = (this.locale.equals(new Locale("en")))
                ? new Locale("ru")
                : new Locale("en");
        return this.locale.toString();
    }

    public boolean isAlive() {
        return this.alive;
    }

    public synchronized String getDescription(Food food) { // if we have description then ok
        // if we don't have then find it in wiki
        if (food.description.equals("")) {
            food.description = Parser.getDescriptionFromInternet(food.name, this.locale);
            DataBase.setInDB(food);
        }
        return food.description;
    }

    public String getIngredients(String txt) {
        String res = "";
        for (String e : Resepts.thisFood.engreds){
            System.out.println(e);
            res += e + "\n";
        }
        return res;
    }

    public String setTime(String txt) {
        String[] date = txt.split(":");
        int h = Integer.parseInt(date[0]);
        int m = Integer.parseInt(date[1]);
        int s = Integer.parseInt(date[2]);
        GregorianCalendar now = new GregorianCalendar();
        now.add(Calendar.MINUTE, m);
        now.add(Calendar.HOUR, h);
        now.add(Calendar.SECOND, s);
        thisdate = now;
        isRunTimer = true;
        return "Таймер запущен.";
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
        commands.put("recipe", new Command("get recipe", this::getResept));
        commands.put("next", new Command("change language", this::next));
        commands.put("ingredients", new Command("change language", this::getIngredients));
        commands.put("timer", new Command("change language", this::setTime));
    }
}
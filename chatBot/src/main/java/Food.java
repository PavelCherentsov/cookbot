import java.io.IOException;
import java.util.ArrayList;

class Food {
    public String name;
    public String description;
    public ArrayList<String> steps = new ArrayList<>();
    public ArrayList<String> engreds = new ArrayList<>();

    Food(String name, String description) {
        this.description = description;
        this.name = name.replaceAll("&quot;","\"" );
    }

    Food(String name) throws IOException {
        this.description = "";
        Food food = DataBase.searchInDB(name);
        this.name = name;
        if(food != null) {
            this.description = food.description;
        }
    }
}
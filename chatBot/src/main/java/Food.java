import javax.imageio.IIOException;
import java.io.IOException;

public class Food {
    public String name;
    public String description;

    Food(String name) throws IOException {
        this.description = "";
        Food food = DataBase.searchInDB(name);
        this.name = name;
        if(food != null) {
            this.description = food.description;
        }
    }

    //public ArrayList<string> ingridients;
}
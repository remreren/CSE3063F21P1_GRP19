package coursetracking;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Arrays;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import coursetracking.models.Course;
import coursetracking.utils.Utils;

/**
 * App
 */
public class App {
    public static void main(String[] args) throws Exception {
        App app = new App();
        app.start();
    }

    public void start() throws Exception {

        File myObj = Utils.getInstance().getResource("input.json");
        String data = "";
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            data += myReader.nextLine();
        }
        myReader.close();
        Config c = new Gson().fromJson(data, Config.class);
        System.out.println(c);
    }
}

class Config { 
    public String registrationTerm;
    public Course[] courses ;
    @Override
    public String toString() {
        return "Config [courses=" + Arrays.toString(courses) + ", semester=" + registrationTerm + "]";
    }
    
}
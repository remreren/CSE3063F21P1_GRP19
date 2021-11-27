package coursetracking;

import java.io.File;
import java.util.Scanner;

import com.google.gson.Gson;

import coursetracking.models.Config;
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

        File input = Utils.getInstance().getResource("input.json");
        String data = "";
        Scanner myReader = new Scanner(input);
        while (myReader.hasNextLine()) {
            data += myReader.nextLine();
        }
        myReader.close();
        Config c = new Gson().fromJson(data, Config.class);
        System.out.println(c);
    }
}

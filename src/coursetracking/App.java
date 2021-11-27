package coursetracking;

import java.io.File;
import java.util.Scanner;

import coursetracking.models.Student;
import coursetracking.models.Transcript;
import coursetracking.models.Config;
import coursetracking.utils.Utils;

import com.google.gson.Gson;

/**
 * App
 */
public class App { 
    public static void main(String[] args) throws Exception {
        App app = new App();
        app.start();
    }

    public void start() throws Exception {
        readInput();
    }
    
    public void createStudents(){//Creates Students
        int num = 150121001;
        for(int sem = 1; sem <= 8; sem += 2){
            for(int id = num; id < num + 70; id++){
                //Student will creates
                //String student name = jsonfile, studentSurname = jsonfile;
                //Student st = new Student();
                //st.Student(id, "", "", sem);
                //st.Serialization();
            }
            num = num - 1069;
        }
    }

    public void readInput() throws Exception {
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

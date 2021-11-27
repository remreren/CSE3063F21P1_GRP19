package coursetracking;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import coursetracking.models.Student;
import coursetracking.models.Transcript;
import coursetracking.models.Config;
import coursetracking.utils.Utils;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * App
 */
public class App { 

    Config config;
    Data data;

    public static void main(String[] args) throws Exception {
        App app = new App();
        app.start();
    }

    public void start() throws Exception {
        readInput();
        readNames();
        System.out.println(data.students.get(2).name);
    }

    public void readNames() throws Exception {
        File input = Utils.getInstance().getResource("names.json");
        String data = "";
        Scanner myReader = new Scanner(input);
        while (myReader.hasNextLine()) {
            data += myReader.nextLine();
        }
        myReader.close();
        this.data = new Gson().fromJson(data, Data.class);
        System.out.println(config);
    }

    public void readInput() throws Exception {
        File input = Utils.getInstance().getResource("input.json");
        String data = "";
        Scanner myReader = new Scanner(input);
        while (myReader.hasNextLine()) {
            data += myReader.nextLine();
        }
        myReader.close();
        config = new Gson().fromJson(data, Config.class);
        System.out.println(config);
    }
    
    public void createStudents(){//Creates Students
        int num = 150121001, index = 0;
        for(int sem = 1; sem <= 8; sem += 2){
            for(int id = num; id < num + 70; id++){
                data.students.get(index).id = id;
                data.students.get(index).Serialization();
                //Gives all lesson according to sem parameter
                //data.students.get(index).addCourse(course); //its could be in loop
                index++;
            }
            num = num - 1069;
        }
    }
}


class Data {
    @SerializedName("fullnames")
    public ArrayList<Student> students;
}
package coursetracking;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import coursetracking.models.Student;
import coursetracking.models.TakenCourse;
import coursetracking.models.Transcript;
import coursetracking.models.Config;
import coursetracking.models.Course;
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
        System.out.println(Utils.getInstance().getOutputPath().list().length);
        if (Utils.getInstance().getOutputPath().list().length == 0)
            createStudents();
    }

    public void readNames() throws Exception {
        File input = Utils.getInstance().getResource("allStudentNames.json");
        String data = "";
        Scanner myReader = new Scanner(input);
        while (myReader.hasNextLine()) {
            data += myReader.nextLine();
        }
        myReader.close();
        this.data = new Gson().fromJson(data, Data.class);
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
    }

    public void createStudents() {// Creates Students
        int num = 150121001, index = 0;
        for (int sem = 1; sem <= 8; sem += 2) {
            for (int id = num; id < num + 70; id++) {
                data.students.get(index).id = id;
                getCoursesBySemester(sem, data.students.get(index));
                data.students.get(index).save();
                index++;
            }
            num = num - 1000;
        }
    }

    private Random random = new Random(54364564);
    private String[] letterNotes = { "AA", "BA", "BB", "CB", "CC" };

    void getCoursesBySemester(int semester, Student st) {
        for (int i = 1; i <= semester; i++) {
            Transcript trscript = new Transcript();
            for (Course c : config.curriculum) {
                if (semester == c.getSemester()) {
                    int rnd = random.nextInt(5);
                    TakenCourse tc = new TakenCourse(c, letterNotes[rnd]);
                    trscript.addCourse(tc);
                }
            }
            trscript.setSemester(i);
            trscript.calculate();
            st.addTranscript(trscript);
        }
        st.calculate();
    }
}

class Data {
    @SerializedName("fullnames")
    public ArrayList<Student> students;
}
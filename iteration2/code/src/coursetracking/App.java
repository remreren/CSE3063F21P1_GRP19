package coursetracking;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
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

    private Config config;
    private Data data;
    private Gson gson = new Gson();
    private ArrayList<Student> students = new ArrayList<>();
    private FilenameFilter transcriptFilter = new FilenameFilter() {

        @Override
        public boolean accept(File dir, String name) {
            return name.startsWith("150") && name.endsWith(".json");
        }

    };

    public static void main(String[] args) throws Exception {
        App app = new App();
        app.start();
    }

    public void start() throws Exception {
        readInput();
        readNames();
        if (Utils.getInstance().getOutputPath().listFiles(transcriptFilter).length == 0)
            createStudents();
        else {
            readStudents();
            gradesOfLastSemester();
            newTermRegistration();
        }
        writeOutputJson();
    }

    // assigns grades to the last registered (non-graded yet) courses before new
    // registration
    public boolean gradesOfLastSemester() {
        for (Student st : data.students) {
            int numberOfTrc = st.getTranscripts().size();
            // if the term parameter isn't changed, no grades will be assigned
            if ((config.registrationTerm.toLowerCase().equals("fall") && numberOfTrc % 2 != 0) ||
                    (config.registrationTerm.toLowerCase().equals("spring") && numberOfTrc % 2 == 0))
                return false;

            Transcript trscript = st.getTranscripts().get(numberOfTrc - 1); // last semesterCourses in transcript
            for (TakenCourse tkc : trscript.getSemesterCourses()) {
                int rnd = random.nextInt(9);
                tkc.setLetterGrade(letterNotes[rnd]);
            }
            trscript.calculate();
            st.calculate();
            st.save();
        }
        return true;
    }

    // students register new term (term parameter changed in input.json)
    public boolean newTermRegistration() {
        for (Student student : data.students) {
            int lastRegisteredSem = student.getTranscripts().size();
            int newSem = lastRegisteredSem + 1;

            // if the term parameter isn't changed, no new registration will be done
            if ((config.registrationTerm.toLowerCase().equals("fall") && lastRegisteredSem % 2 != 0) ||
                    (config.registrationTerm.toLowerCase().equals("spring") && lastRegisteredSem % 2 == 0))
                return false;

            if (lastRegisteredSem == 8)
                continue; // (for now) there will be no new registration for students who took all courses

            Transcript trscript = new Transcript();
            for (Course c : config.curriculum) {
                if (newSem == c.getSemester() && student.canTakeCourse(c)) {
                    TakenCourse tc = new TakenCourse(c); // newly registered courses has no grade attribute
                    trscript.addCourse(tc);
                }
            }
            trscript.setSemester(newSem);
            trscript.calculate();
            student.addTranscript(trscript);
            student.calculate();
            student.save();
        }
        return true;

    }

    /*
     * public void exams() {
     * for (Student st : data.students) {
     * Transcript transcript = new Transcript();
     * if (st.getCurrentCourses() != null) {
     * for (Course c : st.getCurrentCourses()) {
     * int rnd = random.nextInt(5);
     * transcript.addCourse(new TakenCourse(c, letterNotes[rnd]));
     * }
     * transcript.calculate();
     * st.setCurrentTrancript(transcript);
     * st.calculate();
     * st.save();
     * }
     * }
     * }
     */

    /*
     * public void chooseCourses() {
     * for (Student st : data.students) {
     * for (Course c : config.curriculum) {
     * if (st.canTakeCourse(c) && ((c.getSemester() % 2 == 1) ==
     * (config.registrationTerm.toLowerCase().equals("fall"))))
     * st.addCourse(c);
     * }
     * }
     * }
     */

    public void readStudents() throws Exception {
        data.students = new ArrayList<>();
        for (File f : Utils.getInstance().getOutputPath().listFiles(transcriptFilter)) {
            if (f.getName().endsWith(".json")) {
                String data = "";
                Scanner myReader = new Scanner(f);
                while (myReader.hasNextLine()) {
                    data += myReader.nextLine();
                }
                myReader.close();
                Student st = gson.fromJson(data, Student.class);
                st.calculate();
                this.data.students.add(st);
            }
        }
    }

    public void readNames() throws Exception {
        File input = Utils.getInstance().getResource("allStudentNames.json");
        String data = "";
        Scanner myReader = new Scanner(input);
        while (myReader.hasNextLine()) {
            data += myReader.nextLine();
        }
        myReader.close();
        this.data = gson.fromJson(data, Data.class);
    }

    public void readInput() throws Exception {
        File input = Utils.getInstance().getResource("input.json");
        String data = "";
        Scanner myReader = new Scanner(input);
        while (myReader.hasNextLine()) {
            data += myReader.nextLine();
        }
        myReader.close();
        config = gson.fromJson(data, Config.class);
    }

    public void createStudents() {// Creates Students
        int num = 150121001, index = 0;
        int sem = (config.registrationTerm.toLowerCase().equals("spring")) ? 2 : 1;
        for (; sem <= 8; sem += 2) {
            for (int id = num; id < num + 70; id++) {
                Student std = new Student(data.students.get(index));
                std.id = id;
                getCoursesBySemester(sem, std);
                students.add(std);
                std.save();
                index++;
            }
            num = num - 1000;
        }
    }

    private Random random = new Random(54364564);
    private String[] letterNotes = { "AA", "BA", "BB", "CB", "CC", "DC", "DD", "FD", "FF" };

    void getCoursesBySemester(int semester, Student st) {
        TakenCourse tc;
        for (int i = 1; i <= semester; i++) {
            Transcript trscript = new Transcript();
            for (Course c : config.curriculum) {
                if (i == c.getSemester()) {
                    int rnd = random.nextInt(9);
                    if (i == semester)
                        tc = new TakenCourse(c); // newly registered courses has no grade attribute
                    else
                        tc = new TakenCourse(c, letterNotes[rnd]);
                    if (st.canTakeCourse(c))
                        trscript.addCourse(tc);
                }
            }
            trscript.setSemester(i);
            trscript.calculate();
            st.addTranscript(trscript);
        }
        st.calculate();
    }

    // writes a general feedback for all students on the screen and into OUTPUT.json
    // file under output folder
    public void writeOutputJson() {
        Output outputObj = new Output();
        for (Course c : config.curriculum) {
            for (String s : c.getFeedback()) {
                System.out.println(s);
                outputObj.addFeedback(s);
            }
        }
        try {
            File output = new File(Utils.getInstance().getOutputPath(), "OUTPUT.json");
            if (!output.exists())
                output.createNewFile();
            FileWriter writer = new FileWriter(output, false);
            writer.write(gson.toJson(outputObj));
            writer.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

class Output {
    @SerializedName("feedback")
    ArrayList<String> feedbackList;

    public void addFeedback(String feedback) {
        if (feedbackList == null)
            feedbackList = new ArrayList<String>();
        feedbackList.add(feedback);
    }
}

class Data {
    @SerializedName("fullnames")
    public ArrayList<Student> students;
}
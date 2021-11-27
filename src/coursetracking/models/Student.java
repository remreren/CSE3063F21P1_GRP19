package coursetracking.models;

import java.util.ArrayList;
import java.util.Collections;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;

import com.google.gson.annotations.SerializedName;

import coursetracking.utils.Utils;

public class Student {

    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String name;

    @SerializedName("surname")
    public String surname;

    @SerializedName("transcripts")
    public ArrayList<Transcript> transcripts;

    private float gpa;
    private int semester;
    public ArrayList<Course> currentCourses;
    private Advisor advisor;

    private boolean addCourse(Course course) {
        return true;
    }

    public ArrayList<Transcript> getTranscripts() {
        return transcripts;
    }

    public boolean readTranscript() {
        return true;
    }

    public void sortTranscripts() {
        Collections.sort(transcripts);
    }

    public boolean canTakeCourse(Course course) {
        if (semester < course.getSemester())
            return false;

        nextpr: for (Course pr : course.getPrerequisites()) {
            for (Transcript tr : transcripts) {
                for (Course c : tr.getCourses()) {
                    if (c.getCourseName() == pr.getCourseName())
                        continue nextpr;
                }
                return false;
            }
        }
        return true;
    }

    public void calculateSemester() {

    }

    public void calculateCumutlativeGPA(int semester) {
        this.gpa = 4.0f;
    }

    public float getGPA() {
        return gpa;
    }

    public void save() {
        Gson gson = new Gson();

        try {
            File std = new File(Utils.getInstance().getOutputPath(), this.id + ".json");
            std.createNewFile();
            // if (std.createNewFile()) throw new IOException("Tükürrr");
            FileWriter writer = new FileWriter(std);
            gson.toJson(this, writer);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

}

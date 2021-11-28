package coursetracking.models;

import java.util.ArrayList;
import java.util.Collections;
import java.io.File;
import java.io.FileWriter;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import coursetracking.utils.Utils;

public class Student {

    @SerializedName("id")
    public int id;

    @SerializedName(value = "name", alternate = {"studentName"})
    public String name;

    @SerializedName(value = "surname", alternate = {"studentSurname"})
    public String surname;

    @SerializedName("transcripts")
    private ArrayList<Transcript> transcripts;

    private float gpa;
    private int semester;
    private ArrayList<Course> currentCourses;

    private int totalCredit;

    public Student(){
        // empty constructor.
    }

    public Student(int id, String name, String surname, int semester){
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.semester = semester;
        this.currentCourses = null;
        this.transcripts = null;
    }
  
    public void calculate() {
        sortTranscripts();
        calculateTotalCredit();
        calculateSemester();
    }

    public boolean addCourse(Course course) {
        if (currentCourses.contains(course))
            return false;
        if (!canTakeCourse(course))
            return false;
        currentCourses.add(course);
        return true;
    }

    public boolean register() {
        return true;
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
        float cumulative = 0f;
        int cumulativeCourseCredit = 0;
        for (Transcript t : transcripts) {
            cumulative += t.getSemesterGPA() * t.getTotalCredit();
            cumulativeCourseCredit += t.getTotalCredit();
            if (cumulative / cumulativeCourseCredit < 1.8f)
                break;
            semester = t.getSemester();
        }
    }

    public void calculateTotalCredit() {
        int sumOfCredits = 0;
        for (Transcript t : transcripts) {
            for (TakenCourse c : t.getCourses()) {
                if (Utils.getInstance().getGPAOfLetterGrade(c.getLetterGrade()) > 0.1f)
                    sumOfCredits += c.getCredit();
            }
        }
        totalCredit = sumOfCredits;
    }

    // TODO: read transcript ??
    public boolean readTranscript() {
        return true;
    }

    public int getTotalCredit() {
        return totalCredit;
    }

    public void sortTranscripts() {
        Collections.sort(transcripts);
    }

    public ArrayList<Transcript> getTranscripts() {
        return transcripts;
    }

    public void calculateCumutlativeGPA(int semester) {//this will implement at register course (transcript) part.
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
            FileWriter writer = new FileWriter(std);
            gson.toJson(this, writer);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void addTranscript(Transcript tr){
        if (this.transcripts == null) this.transcripts = new ArrayList<>();
        this.transcripts.add(tr);
    }
}

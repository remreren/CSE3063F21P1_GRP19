package coursetracking.models;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

import coursetracking.utils.Utils;

public class Transcript implements Comparable<Transcript> {
    private int id;

    @SerializedName("semester")
    private int semester;

    private ArrayList<TakenCourse> semesterCourses;
    private float semesterGPA;
    private int totalCredit;

    public void calculate() {
        calculateTotalCredit();
        calculateSemesterGPA();
    }

    public void calculateSemesterGPA() {
        int creditCount = 0;
        Utils utils = Utils.getInstance();
        float sum = 0.0f;
        for (TakenCourse c : semesterCourses) {
            creditCount += c.getCredit();
            sum += c.getCredit() * utils.getGPAOfLetterGrade(c.getLetterGrade());
        }

        this.semesterGPA = sum/creditCount;
    }

    public void calculateTotalCredit() {
        int sum = 0;
        for (TakenCourse course: semesterCourses) {
            sum += course.getCredit();
        }
        totalCredit = sum;
    }

    public ArrayList<TakenCourse> getCourses() {
        return semesterCourses;
    }

    public int getSemester() {
        return semester;
    }

    public float getSemesterGPA() {
        return semesterGPA;
    }

    public int getTotalCredit() {
        return totalCredit;
    }

    @Override
    public int compareTo(Transcript o) {
        return o.semester;
    }   

    public void addCourse(TakenCourse tc){
        this.semesterCourses.add(tc);
    }
}

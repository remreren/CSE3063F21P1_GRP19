package coursetracking.models;

import com.google.gson.annotations.SerializedName;

import coursetracking.utils.Utils;

public class Transcript implements Comparable {
    private int id;

    @SerializedName("semester")
    private int semester;

    private TakenCourse[] semesterCourses;

    // private Course[] takenCourses;
    // private Course[] failedCourses;
    // private Course[] passedCourses;

    private float semesterGPA;

    public TakenCourse[] getCourses() {
        return semesterCourses;
    }

    public int getSemester() {
        return semester;
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

    public float getSemesterGPA() {
        return semesterGPA;
    }

    @Override
    public int compareTo(Object o) {
        return ((Transcript) o).semester;
    }   
}

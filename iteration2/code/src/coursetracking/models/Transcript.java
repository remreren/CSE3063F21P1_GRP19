package coursetracking.models;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import coursetracking.utils.Utils;

public class Transcript implements Comparable<Transcript> {
    
    @SerializedName("semester")
    @Expose
    private int semester;

    @SerializedName("semesterCourses")
    @Expose
    private ArrayList<TakenCourse> semesterCourses;

    @SerializedName("semesterGPA")
    @Expose
	private float semesterGPA;

    @SerializedName("totalCredit")
    @Expose
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

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public float getSemesterGPA() {
        return semesterGPA;
    }

    public int getTotalCredit() {
        return totalCredit;
    }
    
    public ArrayList<TakenCourse> getSemesterCourses() {
		return semesterCourses;
	}

    @Override
    public int compareTo(Transcript o) {
        if (o.semester == this.semester) return 0;
        else if (o.semester > this.semester) return -1;
        else return o.semester;
    }   

    public void addCourse(TakenCourse tc){
        if (this.semesterCourses == null) this.semesterCourses = new ArrayList<>();
        this.semesterCourses.add(tc);
    }

    public void addCourse(ArrayList<TakenCourse> tcs) {
        if (this.semesterCourses == null) this.semesterCourses = new ArrayList<>();
        this.semesterCourses.addAll(tcs);
    }
}

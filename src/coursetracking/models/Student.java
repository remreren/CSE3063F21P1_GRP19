package coursetracking.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.google.gson.annotations.SerializedName;

public class Student {

    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String name;

    @SerializedName("surname")
    public String surname;

    @SerializedName("transcripts")
    private ArrayList<Transcript> transcripts;
    
    private float gpa;
    private int semester;
    private Course[] currentCourses;
    private String advisor;

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
        if (semester < course.getSemester()) return false;

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
}

package coursetracking.models;

import java.util.ArrayList;
import java.util.Collections;
import java.io.File;
import java.io.FileWriter;
import java.security.AlgorithmParametersSpi;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import coursetracking.utils.Utils;

public class Student {

    @SerializedName("id")
    public int id;

    @SerializedName(value = "name", alternate = { "studentName" })
    public String name;

    @SerializedName(value = "surname", alternate = { "studentSurname" })
    public String surname;

    @SerializedName("transcripts")
    private ArrayList<Transcript> transcripts;

    private Transcript currentTranscript;

    private float gpa;
    private int semester;
    private ArrayList<Course> currentCourses;
    private ArrayList<String> passedCourses;
    private ArrayList<String> feedback;

    private int totalCredit;

    public Student() {
        // empty constructor.
    }

    public Student(Student student) {
        this.id = student.id;
        this.name = student.name;
        this.surname = student.surname;
        this.semester = student.semester;
        this.currentCourses = null;
        this.transcripts = null;
    }

    public Student(int id, String name, String surname, int semester) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.semester = semester;
        this.currentCourses = null;
        this.transcripts = null;
    }

    public void calculate() {
        sortTranscripts();
        calculatePassedCourses();
        calculateTotalCredit();
        calculateSemester();
    }

    public boolean addCourse(Course course) {
        if (currentCourses == null)
            currentCourses = new ArrayList<>();
        if (currentCourses.contains(course))
            return false;
        if (!canTakeCourse(course))
            return false;
        currentCourses.add(course);
        return true;
    }

    public boolean register() {
        Transcript tr = new Transcript();
        for (Course c : currentCourses)
            tr.addCourse(new TakenCourse(c, null));
        this.currentTranscript = tr;
        this.save();
        return true;
    }

    /* checks whether the student passed prerequisites of the course
     and adds feedback into student's json if necessary */
    public boolean canTakeCourse(Course course) {
    	if (feedback == null) feedback = new ArrayList<>();	
    	if (course.getPrerequisites() != null) {
    		
    	nextpr: for (Course pr : course.getPrerequisites()) {
    	    		for (Transcript tr : transcripts) {
    					for (TakenCourse c : tr.getCourses()) {
    						if ((c.getCourseCode().equals(pr.getCourseCode()) && c.getLetterGrade().compareTo("DD") <= 0)) {
    							continue nextpr; // switches other prereq. if there is
    						}
    					}
    				}
    				// if the prereq. isn't passed adds a related feedback and returns false
    	    		if(course.prereqProblemStd == null) course.prereqProblemStd = new ArrayList<>();
    	    		course.prereqProblemStd.add(this);
    				feedback.add("The system did not allow " + course.courseCode + " because student failed prereq. " + pr.courseCode);
    				return false; 
    			}
    			return true; // if there is no prereq. problem returns true
    		}
    	return true; // if the course has no prereq. returns true 	
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

    public void calculatePassedCourses() {
        if (passedCourses == null)
            passedCourses = new ArrayList<>();
        for (Transcript t : transcripts) {
            for (TakenCourse c : t.getCourses()) {
                if (Utils.getInstance().getGPAOfLetterGrade(c.getLetterGrade()) - 0.01 > Utils.getInstance()
                        .getGPAOfLetterGrade("FF") && !passedCourses.contains(c.courseCode)) {
                    passedCourses.add(c.courseCode);
                }
            }
        }
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

    public void calculateCumutlativeGPA(int semester) {// this will implement at register course (transcript) part.
        this.gpa = 4.0f;
    }

    public float getGPA() {
        return gpa;
    }

    public ArrayList<Course> getCurrentCourses() {
        return currentCourses;
    }

    public Transcript getCurrenTranscript() {
        return currentTranscript;
    }

    public void setCurrentTrancript(Transcript currentTranscript) {
        this.currentTranscript = currentTranscript;
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

    public void addTranscript(Transcript tr) {
        if (this.transcripts == null)
            this.transcripts = new ArrayList<>();
        this.transcripts.add(tr);
    }
}

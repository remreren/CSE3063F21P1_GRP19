package coursetracking.models;

import java.util.ArrayList;
import com.google.gson.annotations.SerializedName;

public class Course {
    protected String courseCode;
    protected String courseName;
    protected int credit;
    protected ArrayList<Student> prereqProblemStd;
    protected ArrayList<Student> quotaProblemStd; //Quota Problemi yaşayanlar
    protected ArrayList<Student> enrolledList;
    protected ArrayList<String> feedback;
    
    @SerializedName("prerequisites")
    protected Course[] prerequisities;
    
    protected int semester;
    private String type;

    public Course(Course c) {
        this.courseCode = c.courseCode;
        this.courseName = c.courseName;
        this.credit = c.credit;
        this.prerequisities = c.prerequisities;
        this.semester = c.semester;
        this.type = c.type;
    }
    
    public ArrayList<String> getFeedback() {
    	if(feedback == null) feedback = new ArrayList<>();
        String fb ="";
    	if(prereqProblemStd != null) {
            fb += prereqProblemStd.size() + " STUDENTS COULD NOT REGISTER FOR " + courseCode + " DUE TO THE PREREQ. PROBLEMS";
            fb += "( ";
            for(Student s: prereqProblemStd){
                fb += s.id+" ";
            }
            fb += ")";
    		feedback.add(fb);
    	}
        fb="";
        if(getQuotaProblemAmount() != 0){
            fb += getQuotaProblemAmount()+" STUDENTS COULD NOT REGISTER FOR "+courseCode+" DUE TO THE QUOTA PROBLEMS";
            fb += "( ";
            for(Student st: getQuotaStudent()){
                fb+=st.id + " ";
            }
            fb += ")";
            feedback.add(fb);
            System.out.println(fb);
        }
    	return feedback;
    }

    public String getType(){
        return this.type;
    }
    public Course[] getPrerequisites() {
        return prerequisities;
    }

    public String getCourseName() {
        return courseCode;
    }
    
    public void enrollStudent(Student s){        
        if (this.enrolledList == null) this.enrolledList = new ArrayList<>();
        this.enrolledList.add(s);
    }

    public String getCourseCode() {
		return courseCode;
	}

    public ArrayList<Student> getQuotaStudent(){
        return this.quotaProblemStd;
    }

    public int getSemester() {
        return semester;
    }

    public int getCredit() {
        return credit;
    }

    public int getEnrolledSudentsSize(){ 
        if (this.enrolledList == null) return 0;
        return this.enrolledList.size();
    }

    public void addQuotaProblem(Student s){
        if (this.quotaProblemStd == null) this.quotaProblemStd = new ArrayList<>();
        this.quotaProblemStd.add(s);
    }

    public int getQuotaProblemAmount(){   
        if (this.quotaProblemStd == null) return 0;
        return quotaProblemStd.size();
    }

    public void setType(String type){
        this.type = type;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((courseCode == null) ? 0 : courseCode.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Course other = (Course) obj;
        if (courseCode == null) {
            if (other.courseCode != null)
                return false;
        } else if (!courseCode.equals(other.courseCode))
            return false;
        return true;
    }    
    
}

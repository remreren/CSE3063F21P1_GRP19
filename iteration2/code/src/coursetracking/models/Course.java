package coursetracking.models;

import com.google.gson.annotations.SerializedName;

public class Course {
    protected String courseCode;
    protected String courseName;
    protected int credit;
    
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

    public Course[] getPrerequisites() {
        return prerequisities;
    }

    public String getCourseName() {
        return courseCode;
    }
    

    public String getCourseCode() {
		return courseCode;
	}


    public int getSemester() {
        return semester;
    }

    public int getCredit() {
        return credit;
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

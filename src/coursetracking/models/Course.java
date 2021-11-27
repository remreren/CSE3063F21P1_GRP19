package coursetracking.models;

public class Course {
    protected String courseCode;
    protected String courseName;
    protected int credit;
    private Object[] sections;
    private Student[] students;
    protected Course[] prerequisities;
    private Department department;
    protected int semester;
    private String type;

    public Course[] getPrerequisites() {
        return prerequisities;
    }

    public String getCourseName() {
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

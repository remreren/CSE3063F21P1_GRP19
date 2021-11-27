package coursetracking.models;

public class Course {
    private int id;
    protected String name;
    protected int credit;
    protected Course[] prerequisities;
    protected int semester;

    public Course[] getPrerequisites() {
        return prerequisities;
    }

    public String getCourseName() {
        return name;
    }

    public int getSemester() {
        return semester;
    }

    public int getCredit() {
        return credit;
    }
}

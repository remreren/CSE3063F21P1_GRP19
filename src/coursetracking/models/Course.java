package coursetracking.models;

public class Course {
    private int id;
    protected String name;
    protected int credit;
    private Object[] sections;
    private Object lecturer;
    protected Course[] prerequisities;
    private String department; //Tutulmayabilir
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

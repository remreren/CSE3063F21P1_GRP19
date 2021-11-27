package coursetracking.models;

public class Course {
    private int id;
    protected String name;
    protected int credit;
    protected Course[] prerequisities;
    protected int semester;
    private String type;

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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }    
}

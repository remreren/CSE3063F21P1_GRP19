package coursetracking.models;

public class TakenCourse extends Course {
    private String letterGrade = null;

    public TakenCourse(Course c, String letterGrade) {
        super(c);
        this.letterGrade = letterGrade;
    }

    public String getLetterGrade() {
        return letterGrade;
    }

    public void setLetterGrade(String letterGrade) {
       this.letterGrade = letterGrade;
    }
}

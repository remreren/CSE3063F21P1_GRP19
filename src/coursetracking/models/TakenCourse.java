package coursetracking.models;

public class TakenCourse extends Course {
    private String letterGrade = null;

    public String getLetterGrade() {
        return letterGrade;
    }

    public void setLetterGrade(String letterGrade) {
       this.letterGrade = letterGrade;
    }
}

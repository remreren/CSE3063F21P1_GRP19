package coursetracking.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TakenCourse extends Course {

    @SerializedName(value = "letterGrade")
    @Expose
    private String letterGrade = null;

    public TakenCourse(Course c, String letterGrade) {
        super(c);
        this.letterGrade = letterGrade;
    }

    public TakenCourse(Course c) {
		super(c);
	}
    
    public String getLetterGrade() {
        return letterGrade;
    }

    public void setLetterGrade(String letterGrade) {
       this.letterGrade = letterGrade;
    }
}

package coursetracking.models;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Elective {

    @SerializedName("type")
    @Expose
    public String type;

    @SerializedName("creeditRequirement")
    @Expose
    public int creditRequirement;

    @SerializedName("quota")
    @Expose
    public int quota;

    @SerializedName("courses")
    @Expose
    public ArrayList<Course> courses;
    
    public int getElectiveQuantity(){
        int quantity = courses.size();
        return quantity;
    }

    public int getQuota(){
        return this.quota;
    }

    public boolean isQuotaFull(Course c){
        for(Course course: courses){
            if(course.getCourseCode().equals(c.getCourseCode()) ){
                if(this.quota <= c.getEnrolledSudentsSize()){
                    return true;
                }
            }
        }
        return false;
    }

    public void setSemester(Course c, int semester){
        c.semester = semester;
    }

    public void setNewTerm(){
        for(Course c: courses){
            c.quotaProblemStd = null;
            c.enrolledList = null;
        }
    }
}

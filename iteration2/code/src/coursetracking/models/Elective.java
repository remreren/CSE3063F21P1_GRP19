package coursetracking.models;

import java.util.ArrayList;

public class Elective {

    public String type;
    public int creditRequirement;
    public int quota;

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
}

package coursetracking.models;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Config {
    
    @SerializedName("registrationTerm")
    @Expose
    public String registrationTerm;
    
    @SerializedName("curriculum")
    @Expose
    public ArrayList<Course> curriculum;
    
    @SerializedName("electives")
    @Expose
    public ArrayList<Course> electives;
}

class Elective {

    @SerializedName("type")
    @Expose
    public String type;
    
    @SerializedName("creditRequirement")
    @Expose
    public int creditRequirement;
    
    @SerializedName("courses")
    @Expose
    public ArrayList<Course> courses;
}

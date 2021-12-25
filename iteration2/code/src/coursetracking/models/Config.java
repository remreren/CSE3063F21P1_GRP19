package coursetracking.models;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Config {
	
	@SerializedName("studentGeneration")
	@Expose
	public boolean studentGeneration;
    
    @SerializedName("registrationTerm")
    @Expose
    public String registrationTerm;
    
    @SerializedName("curriculum")
    @Expose
    public ArrayList<Course> curriculum;
    
    @SerializedName("electives")
    @Expose
    public ArrayList<Elective> electives;
}

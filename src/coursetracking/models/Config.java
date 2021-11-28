package coursetracking.models;

import java.util.ArrayList;

public class Config {
    public String registrationTerm;
    public ArrayList<Course> courses;
    public ArrayList<Course> electives;
}

class Elective {
    public String type;
    public int creditRequirement;
    public ArrayList<Course> courses;
}

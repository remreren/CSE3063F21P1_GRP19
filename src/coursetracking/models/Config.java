package coursetracking.models;

import java.util.ArrayList;

public class Config {
    public String registrationTerm;
    public ArrayList<Curriculum> courses;
    public ArrayList<Course> electives;
}

class Curriculum {
    public String semester;
    public ArrayList<Course> courses;
}

class Elective {
    public String type;
    public int creditRequired;
    public ArrayList<Course> courses;
}

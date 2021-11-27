package coursetracking.models;

import java.util.ArrayList;

public class Config {
    public String registrationTerm;
    public ArrayList<Curriculum> courses;
}

class Curriculum {
    public String semester;
    public ArrayList<Course> courses;
}

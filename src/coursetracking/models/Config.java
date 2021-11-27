package coursetracking.models;

import java.util.Arrays;

public class Config { 
    public String registrationTerm;
    public Course[] courses ;
    @Override
    public String toString() {
        return "Config [courses=" + Arrays.toString(courses) + ", semester=" + registrationTerm + "]";
    }
    
}

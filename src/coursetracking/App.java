package coursetracking;

import com.google.gson.Gson;

import coursetracking.models.Student;
import coursetracking.models.Transcript;



public class App {
    public static void main(String[] args) {

    }
    
    void CreateStudents(){//Creates Students
        int num = 150121001;
        for(int sem = 1; sem <= 8; sem += 2){
            for(int id = num; id < num + 70; id++){
                //Student will creates
                //String student name = jsonfile, studentSurname = jsonfile;
                //Student st = new Student();
                //st.Student(id, "", "", sem);
                //st.Serialization();
            }
            num = num - 1069;
        }
    }
}
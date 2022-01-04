package coursetracking.utils;


import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import coursetracking.models.Student;
import coursetracking.models.TakenCourse;
import coursetracking.models.Transcript;
import coursetracking.models.Config;
import coursetracking.models.Course;
import coursetracking.models.Elective;


import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ControlCenter {
	
	    private Config config;
	    private Data data;
	    private Gson gson = Utils.getInstance().getGson();
	    private ArrayList<Student> students = new ArrayList<>();
	    private FilenameFilter transcriptFilter = new FilenameFilter() {

	        @Override
	        public boolean accept(File dir, String name) {
	            return name.startsWith("150") && name.endsWith(".json");
	        }

	    };

	   public void start() throws Exception {
	        readInput();
	        readNames();
	        if (Utils.getInstance().getOutputPath().listFiles(transcriptFilter).length == 0 || config.studentGeneration)
	            createStudents();
	        else {
	            readStudents();
	            gradesOfLastSemester();
	            newTermRegistration();
	        }
	        writeOutputJson();
	    }

	    // assigns grades to the last registered (non-graded yet) courses before new
	    // registration
	    public boolean gradesOfLastSemester() {
	        for (Student st : data.students) {
	            int numberOfTrc = st.getTranscripts().size();
	            // if the term parameter isn't changed, no grades will be assigned
	            if ((config.registrationTerm.toLowerCase().equals("fall") && numberOfTrc % 2 != 0 && numberOfTrc!=8) ||
	                    (config.registrationTerm.toLowerCase().equals("spring") && numberOfTrc % 2 == 0 && numberOfTrc!=8))
	                return false;

	            Transcript trscript = st.getTranscripts().get(numberOfTrc - 1); // last semesterCourses in transcript
	            for (TakenCourse tkc : trscript.getSemesterCourses()) {
	                int rnd = random.nextInt(9);
	                tkc.setLetterGrade(letterNotes[rnd]);
	            }
	            trscript.calculate();
	            st.calculate();
	            st.save();
	        }
	        return true;
	    }

	    // students register new term (term parameter changed in input.json)
	    public boolean newTermRegistration() {
	        //setElectivesNull();//Electives new term
	        for (Student student : data.students) {
	            int lastRegisteredSem = student.getTranscripts().size();
	            int newSem = lastRegisteredSem + 1;

	            // if the term parameter isn't changed, no new registration will be done
	            if ((config.registrationTerm.toLowerCase().equals("fall") && lastRegisteredSem % 2 != 0 && lastRegisteredSem!=8 ) ||
	                    (config.registrationTerm.toLowerCase().equals("spring") && lastRegisteredSem % 2 == 0 && lastRegisteredSem!=8))
	                return false;

	            if (lastRegisteredSem == 8)
	                continue; // (for now) there will be no new registration for students who took all courses

	            Transcript trscript = new Transcript();
	            for (Course c : config.curriculum)   {
	                Boolean quota = true;
	                Random rand = new Random();
	                if (newSem == c.getSemester() && student.canTakeCourse(c)) {
	                    quota = true;
	                    //Elective Part Starts
	                    if(c.getType() != null){//checks for elective course
	                        for( Elective e: config.electives ){
	                            if( e.type.equals(c.getType()) ){
	                            	int electiveRandom = rand.nextInt(getElectiveQuota(e.type));
	                                while( student.isStudentEnrolled(e.courses.get(electiveRandom)) ){
	                                    electiveRandom = rand.nextInt(getElectiveQuota(e.type));//creates for random course inside electives 
	                                }
	                                if(e.isQuotaFull(e.courses.get(electiveRandom))){
										e.setSemester(e.courses.get(electiveRandom), c.getSemester());
	                                    e.courses.get(electiveRandom).addQuotaProblem(student);
	                                    student.addFeedbackQuota(e.courses.get(electiveRandom));
	                                    quota = false;
	                                } 
	                                else {
	                                    c = e.courses.get(electiveRandom);
	                                    c.setType(e.type);
	                                }
	                            }
	                        }
	                    }
	                    //Elective Part Ends
	                    TakenCourse tc;
	                    if(quota){
	                        tc = new TakenCourse(c); // newly registered courses has no grade attribute
	                        trscript.addCourse(tc);
	                        c.enrollStudent(student);
	                    }
	                }
	            }
	            trscript.setSemester(newSem);
	            trscript.calculate();
	            student.addTranscript(trscript);
	            student.calculate();
	            student.save();
	            
	        }
	        return true;
	    }
	    public void setElectivesNull(){
	        for(Elective e: config.electives){
	            e.setNewTerm();
	        }
	    }

	    public void readStudents() throws Exception {
	        data.students = new ArrayList<>();
	        for (File f : Utils.getInstance().getOutputPath().listFiles(transcriptFilter)) {
	            if (f.getName().endsWith(".json")) {
	                String data = "";
	                Scanner myReader = new Scanner(f);
	                while (myReader.hasNextLine()) {
	                    data += myReader.nextLine();
	                }
	                myReader.close();
	                Student st = gson.fromJson(data, Student.class);
	                st.calculate();
	                this.data.students.add(st);
	            }
	        }
	    }

	    public void readNames() throws Exception {
	        File input = Utils.getInstance().getResource("allStudentNames.json");
	        String data = "";
	        Scanner myReader = new Scanner(input);
	        while (myReader.hasNextLine()) {
	            data += myReader.nextLine();
	        }
	        myReader.close();
	        this.data = gson.fromJson(data, Data.class);
	    }

	    public void readInput() throws Exception {
	        File input = Utils.getInstance().getResource("input.json");
	        String data = "";
	        Scanner myReader = new Scanner(input);
	        while (myReader.hasNextLine()) {
	            data += myReader.nextLine();
	        }
	        myReader.close();
	        config = gson.fromJson(data, Config.class);
	    }

	    public void createStudents() {// Creates Students
	        int num = 150121001, index = 0;
	        int sem = (config.registrationTerm.toLowerCase().equals("spring")) ? 2 : 1;
	        for (; sem <= 8; sem += 2) {
	            for (int id = num; id < num + 70; id++) {
	                Student std = new Student(data.students.get(index));
	                std.setId(id);
	                getCoursesBySemester(sem, std);
	                students.add(std);
	                std.save();
	                index++;
	            }
	            num = num - 1000;
	        }
	    }

	    private Random random = new Random(54364564);
	    private String[] letterNotes = { "AA", "BA", "BB", "CB", "CC", "DC", "DD", "FD", "FF" };

	    void getCoursesBySemester(int semester, Student st) {
	        TakenCourse tc;
	        for (int i = 1; i <= semester; i++) {
	            Transcript trscript = new Transcript();
	            for (Course c : config.curriculum) {
	                //Checks for semester
	                if (i == c.getSemester()) {
	                	int rnd = random.nextInt(9);
	                    boolean newEnrollment = false;
	                    boolean quota = true;
						//Elective Part Starts
						if(c.getType() != null){//checks for elective course
							Random rand = new Random();
							for( Elective e: config.electives ){
								if( e.type.equals(c.getType()) ){
									int electiveRandom = rand.nextInt(getElectiveQuota(c.getType()));//creates for random course inside electives
									while( st.isStudentEnrolled(e.courses.get(electiveRandom)) ){
										electiveRandom = rand.nextInt(getElectiveQuota(e.type));//creates for random course inside electives 
									}
									e.setSemester(e.courses.get(electiveRandom), c.getSemester());
									if(e.isQuotaFull(e.courses.get(electiveRandom))){
										e.courses.get(electiveRandom).addQuotaProblem(st);
										st.addFeedbackQuota(e.courses.get(electiveRandom));
										quota = false;
									} 
									else c = e.courses.get(electiveRandom);
								}
							}
						}
						//Elective Part Ends
	                	if (i == semester){
							tc = new TakenCourse(c); // newly registered courses has no grade attribute
	                        newEnrollment = true;
	                    }
						else
							tc = new TakenCourse(c, letterNotes[rnd]);

	                    if (st.canTakeCourse(c))
	                    {
	                        if(quota) trscript.addCourse(tc);
	                        if(newEnrollment && quota) c.enrollStudent(st);
	                    }
	                }
	            }
	            trscript.setSemester(i);
	            trscript.calculate();
	            st.addTranscript(trscript);
	        }
	        st.calculate();
	    }

	    public int getElectiveQuota(String type){
	        int ret = 0;
	        for(Elective e: config.electives){
	            if(type.equals(e.type)){
	                ret = e.getElectiveQuantity();
	            }
	        }
	        return ret;
	    }

	    // writes a general feedback for all students on the screen and into OUTPUT.json
	    // file under output folder
	    public void writeOutputJson() {
	        Output outputObj = new Output();
	        for (Course c : config.curriculum) {
	            for (String s : c.getFeedback()) {
	                System.out.println(s);
	                outputObj.addFeedback(s);
	            }
	        }
	        for(Elective e: config.electives)
	        {
	            for(Course c: e.courses){
	                for(String s: c.getFeedback()){
	                    System.out.println(s);
	                    outputObj.addFeedback(s);
	                }
	            }
	        }
	        try {
	            File output = new File(Utils.getInstance().getOutputPath(), "OUTPUT.json");
	            if (!output.exists())
	                output.createNewFile();
	            FileWriter writer = new FileWriter(output, false);
	            writer.write(gson.toJson(outputObj));
	            writer.close();
	        } catch (Exception e) {
	            System.out.println(e);
	        }
	    }
	}

	class Output {
	    @SerializedName("feedback")
	    @Expose
	    ArrayList<String> feedbackList;

	    public void addFeedback(String feedback) {
	        if (feedbackList == null)
	            feedbackList = new ArrayList<String>();
	        feedbackList.add(feedback);
	    }
	}

	class Data {
	    @SerializedName("fullnames")
	    @Expose
	    public ArrayList<Student> students;
	}
	


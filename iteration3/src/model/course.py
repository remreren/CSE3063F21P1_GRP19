from typing import List
from model.student import Student


class Course(object):
    def __init__(self, course_code: str, course_name: str, credit: int, prerequisites: List[object], semester: int, course_type: str):
        self.__courseCode = course_code
        self.__courseName = course_name
        self.__credit = credit
        self.__semester = semester
        self.__courseType = course_type
        self.__prerequisites: List[Course] = prerequisites

        self.__enrolledList: List[Student] = []
        self.__stdQuotaProblem: List[Student] = []
        self.__stdPrereqProblem: List[Student] = []
        self.__feedback: List[str] = []

    def getFeedback(self) -> List[str]:
        if(self.__feedback is None):
            self.__feedback = []
        
        fb = ""
        if(self.__stdPrereqProblem != None):

            fb += len(self.__stdPrereqProblem) + " STUDENTS COULD NOT REGISTER FOR " + self.__courseCode + " DUE TO THE PREREQ. PROBLEMS"
            fb += "("
            for s in self.__stdPrereqProblem:
                fb += s.getId() + " "
            fb +=")"
            self.feedback.add(fb)
        fb=""
        if(self.__stdQuotaProblem != 0):
            fb += len(self.__stdQuotaProblem) + " STUDENTS COULD NOT REGISTER FOR " + self.__courseCode + " DUE TO THE QUOTA PROBLEMS"
            fb += "( "
            for st in self.__stdQuotaProblem:
                fb += st.getId() + " "
            fb += ")"
            self.__feedback.add(fb)
        return self.__feedback

    def enrollStudent(self, student: Student):
        self.__enrolledList.append(student)

    def addQuotaProblem(self, std: Student):
        self.__stdQuotaProblem.append(std)

    def addPrereqProblem(self, std: Student):
        self.__stdPrereqProblem.append(std)

    def addFeedback(self, feedback: str):
        self.__feedback.append(feedback)

    def getEnrolledSize(self) -> int:
        return len(self.__enrolledList)

    def getQuotaProblemSize(self) -> int:
        return len(self.__stdQuotaProblem)
    
    def getType(self) -> str:
        return self.__courseType

    def getCourseName(self) -> str:
        return self.__courseName

    def getCourseCode(self) -> str:
        return self.__courseCode

    def getSemester(self) -> int:
        return self.__semester

    def getPrerequisites(self) -> List[object]:
        return self.__prerequisites

    def getStdPrereqProblem(self) -> List[Student]:
        return self.__stdPrereqProblem

    def __str__(self):
        return f"Course(course_code={self.__courseCode}, course_name={self.__courseName}, credit={self.__credit}, semester={self.__semester}, course_type={self.__courseType}"

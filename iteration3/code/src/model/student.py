from typing import List
from src.model.course import Course
from src.model.transcript import Transcript
from src.utils.utils import gpa_letter_to_grade

class Student(object):

    def __init__(self):
        pass

    def __init__(self, _id: int, name: str, surname: str, semester: int):
        self.__id: int = _id
        self.__name: str = name
        self.__surname: str = surname
        self.__semester: int = semester
        self.__currentCourses: List[Course] = []
        self.__transcripts: List[Transcript] = []

        self.__passedCourses: List[str] = []
        self.__feedback: List[str] = []
        self.__totalCredit: int = 0
        self.__currentTranscript: Transcript = None

    def calculate(self):
        self.sortTranscript()
        self.calculatePassedCourses()
        self.calculateTotalCredit()
        self.calculateSemester()

    def addCourse(self, course: Course) -> bool:
        if(self.__currentCourses is None):
            self.__currentCourses = []

        if(course in self.__currentCourses):
            return False

        if(not self.canTakeCourse(course)):
            return False

        self.__currentCourses.append(course)
        return True

    """checks whether the student passed prerequisites of the course
     and adds feedback into student's json if necessary"""

    def canTakeCourse(self, course: Course) -> bool:
        if(course.getPrerequisites()):
            nextpr = False
            for pr in List[Course](course.getPrerequisites()):
                for tr in self.__transcripts:
                    for c in tr.getCourses():
                        # equals() -> == , compareTo -> >= , are we check is letter grade bigger or smaller than DD
                        if((c.getCourseCode() == pr.getCourseCode and c.letterGrade() >= 'DD') <= 0):
                            nextpr = True
                            break
                    if nextpr:
                        break
                if nextpr:
                    continue
                
                # if the prereq. isn't passed adds a related feedback and returns false
                course.addPrereqProblem(self)
                self.addFeedbackPrereq(course, pr)

                return False  # if there is no prereq. problem returns true
            return True  # if the course has no prereq. returns true

    def calculateSemester(self):
        cumulative: float = 0.0
        cumulativeCourseCredit: int = 0

        for t in self.__transcripts:
            cumulative += t.getSemesterGPA() * t.getTotalCredit()
            cumulativeCourseCredit += t.getTotalCredit()

            if(cumulative / cumulativeCourseCredit < 1.8):
                break
            self.__semester = t.getSemester()

    def calculateTotalCredit(self):
        sumOfCredits: int = 0

        for t in self.__transcripts:
            for c in t.getCourses():
                if(gpa_letter_to_grade.get(c.getLetterGrade(), 0.0) > 0.1):
                    sumOfCredits += c.getCredit()

        self.__totalCredit = sumOfCredits

    def calculatePassedCourses(self):
        if(self.__passedCourses is None):
            self.__passedCourses = []
        for t in self.__transcripts:
            for c in t.getCourses():
                if(gpa_letter_to_grade.get(c.getLetterGrade(), 0.0) - 0.01 > gpa_letter_to_grade.get("FF", 0.0) and c.getCourseCode() not in self.__passedCourses):
                    self.__passedCourses.append(c.getCourseCode())

    def isStudentEnrolled(self, c: Course) -> bool:
        if(self.__currentCourses is None):
            return False
        for currentCourse in self.__currentCourses:
            if(c.getCourseCode() == currentCourse.getCourseCode()):
                return True
        return False

    def addFeedback(self, feedback: str):
        if self.__feedback is None:
            self.__feedback = []
        self.__feedback.append(feedback)

    def addFeedbackQuota(self, course: Course) -> None:
        self.addFeedback("The student could not register for " + course.getCourseCode() + " because of a quota problem")

    def addFeedbackPrereq(self, course: Course, prereq: Course):
        self.addFeedback("The system did not allow " + course.getCourseCode() + " because student failed prereq. " + prereq.getCourseCode())

    def setCurrentTranscript(self, currentTranscript: Transcript):
        self.__currentTranscript = currentTranscript

    def setId(self, _id: int):
        self.__id = _id

    def getTotalCredit(self) -> int:
        return self.__totalCredit

    def sortTranscripts(self) -> bool:
        sorted(self.__transcripts)

    def getTranscripts(self) -> List[Transcript]:
        return self.__transcripts

    def getGPA(self) -> float:
        return self.__gpa

    def getCurrentCourses(self) -> List[Course]:
        return self.__currentCourses

    def currentTranscript(self) -> Transcript:
        return self.__currentTranscript

    def getId(self) -> int:
        return self.__id

    def save():

        # TODO read Json file

        pass

    def addTranscript(self, tr: Transcript) -> None:
        if(self.__transcripts is None):
            self.__transcripts = []
        self.__transcripts.append(tr)

    def __str__(self):
        return f"Student({self.stdid}, {self.__name}, {self.__surname}, {self.__semester})"

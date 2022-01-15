from typing import List
from model.course import Course

# serializations?


class Student(object):

    id: int
    name: str
    surname: str
    transcripts: List

    def __init__(self):
        pass

    def __init__(self, id, name, surname, semester):

        self.id = id
        self.name = name
        self.surname = surname
        self.semester = semester
        self.currentCourses = []
        self.transcripts = []

    def calculate() -> None:

        sortTranscript()
        calculatePassedCourses()
        calculateTotalCredit()
        calculateSemester()

    def addCourse(self, course: Course) -> bool:  # Course not implemented
        # constructorda boş array olarak başlatıyoruz bu check e gerek var mı?
        if(self.currentCourses is None):

            self.currentCourses = []

        if(course in self.currentCourses):
            return False

        if(not canTakeCourse(course)):
            # TODO not read canTakeCourse fix it!
            return False

        self.currentCourses.append(course)
        return True

    def register(self) -> bool:
        tr = Transcript()  # Trancript not implemented

        for c in self.currentCourses:
            tr.addCourse(TakenCourse(c, None))

        self.currentTranscript = tr     # currentTransript ???
        self.save()

        return True

    """checks whether the student passed prerequisites of the course
     and adds feedback into student's json if necessary"""

    def canTakeCourse(self, course) -> bool:
        if(feedback is None):
            feedback = []

        if(course.getPrerequisites()):

            # nextpr?
            for pr in course.getPrerequisites():
                for tr in transcripts:
                    for c in tr.getCourses():
                        # equals() -> == , compareTo -> >= , are we check is letter grade bigger or smaller than DD
                        if((c.getCourseCode() == pr.getCourseCode and c.letterGrade() >= 'DD') <= 0):
                            continue  # switches other prereq. if there is
                # if the prereq. isn't passed adds a related feedback and returns false
                if(course.prereqProblemStd is None):
                    course.prereqProblemStd = []
                course.prereqProblemStd.append(self)
                feedback.append("The system did not allow " + course.courseCode,
                                " because student failed prereq. " + pr.courseCode)

                return False  # if there is no prereq. problem returns true

            return True  # if the course has no prereq. returns true

    def addFeedbackQuota(course: Course) -> None:
        if(feedback is None):
            feedback = []
        feedback.append("The student could not register for " +
                        course.getCourseCode() + " because of a quota problem")

    def calculateSemester():
        cumulative: float
        cumulativeCourseCredit: int

        for t in transcripts:
            cumulative += t.getSemesterGPA() * t.getTotalCredit()
            cumulativeCourseCredit += t.getTotalCredit()

            if(cumulative / cumulativeCourseCredit < 1.8):
                break
            semester = t.getSemester()

    def calculateTotalCredit() -> None:
        sumOfCredits = 0

        for t in transcripts:
            for c in t.getCourses():
                if(Utils.getInstance().getGPAOfLetterGrade(c.getLetterGrade()) > 0.1):
                    sumOfCredits += c.getCredit()

        totalCredit = sumOfCredits

    def calculatePassedCourses() -> None:
        if(passedCourses is None):
            passedCourses = []
        for t in transcripts:
            for c in t.getCourses():
                if(Utils.getInstance().getGPAOfLetterGrade(c.getLetterGrade()) - 0.01 > Utils.getInstance().getGPAOfLetterGrade("FF") and c.courseCode not in passedCourses):
                    passedCourses.append(c.courseCode)

    def isStudentEnrolled(self, c: Course) -> bool:
        if(currentCourses is None):
            return False
        for currentCourse in self.currentCourses:
            if(c.getCourseCode == currentCourse.getCourseCode()):
                return True
        return False

    def readTranscript() -> bool:
        return True

    def getTotalCredit() -> int:
        return totalCredit

    def sortTranscripts() -> bool:
        Collections.sort(transcripts)  # Collections -> ?

    def getTranscripts() -> List:   # should it be list ?
        return transcripts

    def calculateCumulativeGPA(self, semester) -> None:
        self.gpa = 4.0

    def getGPA() -> float:
        return gpa

    def getCurrentCourses() -> List:
        return currentCourses

    def currentTranscript():
        return currentTranscript

    def setCurrentTranscript(self, currentTranscript: Transcript) -> None:
        self.currentTranscript = currentTranscript

    def getId() -> int:
        return id

    def setId(self, id) -> None:
        self.id = id

    def save() -> None:

        # TODO read Json file

        pass

    def addTranscript(self, tr: Transcript) -> None:
        if(self.transcripts is None):
            self.transcripts = []
        self.transcripts.append(tr)

    def __str__(self):
        return f"Student({self.stdid}, {self.name}, {self.surname}, {self.semester})"

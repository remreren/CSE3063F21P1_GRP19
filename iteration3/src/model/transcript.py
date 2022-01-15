from SerializedName import Expose
from com.google.gson.annotations import SerializedName

from Transcript.utils import Utils


class Comparable(object):
    def __init__(self, semester: int, semesterCourses: List[object], semesterGPA: str, totalCredit: str):

        self.semester = semester
        self.semesterCourses: List[Transcript] = semesterCourses
        self.semesterGPA = semesterGPA
        self.getGradeLetter: List[courseLetterGrade] = getGradeLetter
        self.totalCredit = totalCredit

class Transcript(Comparable):

    def _init_(self):
        self.__semester = 0
        self.__semesterCourses = None
        self.__semesterGPA = 0
        self.__totalCredit = 0
        self.__id = 0
        self.__semesterCourses = []

    def calculate(self):
        self.calculateTotalCredit()
        self.calculateSemesterGPA()

    def calculateSemesterGPA(self):
        creditCount = 0
        utils = Transcript.utils.Utils.getInstance()
        sum = 0.0
        for c in self.__semesterCourses:
            creditCount += c.getCredit()
            sum += c.getCredit() * utils.getGPAOfLetterGrade(c.getLetterGrade())

        self.__semesterGPA = sum/creditCount

    def calculateTotalCredit(self):
        sum = 0
        for course in self.__semesterCourses:
            sum += course.getCredit()
        self.__totalCredit = sum

    def getCourses(self):
        return self.__semesterCourses

    def getSemester(self):
        return self.__semester

    def setSemester(self, semester):
        self.__semester = semester

    def getSemesterGPA(self):
        return self.__semesterGPA

    def getTotalCredit(self):
        return self.__totalCredit

    def getSemesterCourses(self):
        return self.__semesterCourses

    def compareTo(self, o):
        if o._semester == self._semester:
            return 0
        elif o._semester > self._semester:
            return -1
        else:
            return o.__semester

    def addCourse(self, tc):
        if self.__semesterCourses is None:
            self.__semesterCourses = []
        self.__semesterCourses.append(tc)

    def addCourse(self, tcs):
        if self.__semesterCourses is None:
            self.__semesterCourses = []
        self.__semesterCourses.extend(tcs)
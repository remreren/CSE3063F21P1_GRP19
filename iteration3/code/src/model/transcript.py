from typing import List

from model.course import Course
from model.takencourse import TakenCourse

from utils.utils import gpa_letter_to_grade


class Transcript(object):

    def _init_(self):
        self.__semester: int = 0
        self.__semesterGPA: float = 0
        self.__totalCredit: float = 0
        self.__id: int = 0
        self.__semesterCourses: List[TakenCourse] = []

    def calculate(self):
        self.calculateTotalCredit()
        self.calculateSemesterGPA()

    def calculateSemesterGPA(self):
        creditCount = 0
        sum = 0.0
        for c in self.__semesterCourses:
            creditCount += c.__credit
            sum += c.__credit * gpa_letter_to_grade.get(str(c.__letterGrade), 0.0)

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

    def setSemester(self, semester: int):
        self.__semester = semester

    def getSemesterGPA(self):
        return self.__semesterGPA

    def getTotalCredit(self):
        return self.__totalCredit

    def getSemesterCourses(self):
        return self.__semesterCourses

    def addCourse(self, tcs: List [Course]):
        if self.__semesterCourses is None:
            self.__semesterCourses = []
        self.__semesterCourses.append(tcs)

    def add_Course(self, tcs: List [Course]):
        if self.__semesterCourses is None:
            self.__semesterCourses = []
        self.__semesterCourses.extend(tcs)

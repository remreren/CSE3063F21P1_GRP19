from typing import List
from model.course import Course


class Student(object):

    id: int
    name: str
    surname: str
    transcripts:

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

        # sortTranscript()
        # calculatePassedCourses()
        # calculateTotalCredit()
        # calculateSemester()
        pass

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

    def canTakeCourse(course) -> bool:
        if(feedback is None):
            feedback = []

        if(course.getPre)

    def save():
        pass

    def __str__(self):
        return f"Student({self.stdid}, {self.name}, {self.surname}, {self.semester})"

from typing import List
from model.course import Course
from model.student import Student
from model.takencourse import TakenCourse
from model.transcript import Transcript


class Advisor(object):
    def __init__(self):
        self.__name = 'Advisor'
        self.__surname = 'Test'

    def canTakeCourse(self, course: Course, student: Student):
        if course.getPrerequisites() or len(course.getPrerequisites()) == 0:
            for pr in List[Course](course.getPrerequisites()):
                for tr in student.__transcripts:
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
                course.addPrereqProblem(student)
                student.addFeedbackPrereq(course, pr)

                return False  # if there is no prereq. problem returns true
            return True  # if the course has no prereq. returns true

    def register(self, student: Student) -> bool:
        tr = Transcript()

        for c in student.getCurrentCourses():
            tr.addCourse(TakenCourse(c, None))

        student.setCurrentTranscript(tr)
        student.save()

        return True